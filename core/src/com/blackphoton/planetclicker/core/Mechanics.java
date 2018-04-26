package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackphoton.planetclicker.file.SavegameFile;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.food.Timeout;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Absolute;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Gems;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Multiplier;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Resource_ResourceBundle;
import com.blackphoton.planetclicker.objectType.table.entries.special.Special_ResourceBundle;
import com.blackphoton.planetclicker.objectType.table.entries.template.*;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.resources.ResourceMaterial;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class Mechanics {

	private Era thisEra;
	private Random random;
	private final float clickMultiplier = 0.95f;
	private int numberOfIRThreads = 0;
	private final SavegameFile file = new SavegameFile();

	public void create(){
		thisEra = Data.getEraList().get(0);
		random = new Random();


		file.readGame();

		new Thread(){
			@Override
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try{
						file.saveGame();
						sleep(1000);
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}.start();

		for(TableEntry entry: Data.getSpecialTable().getEntries()){
			if(entry.getNumberOf()>0) {
				((SpecialEntry) entry).setCanBuild(true);
				entry.setResourcesNeeded(null);
			}
		}
	}
	public void update(){
		thisEra = Data.getCurrentEra();

		long populationCount = Data.main.getPopulationCount();
		Data.main.setBuildingCount(0);
		for(TableEntry entry:Data.getBuildingTable().getEntries()){
			Data.main.setBuildingCount(Data.main.getBuildingCount()+entry.getNumberOf()*entry.getValue());
		}
		int buildingCount = Data.main.getBuildingCount();
		Data.main.setFoodCount(0);
		for(TableEntry entry:Data.getFoodTable().getEntries()){
			Data.main.setFoodCount(Data.main.getFoodCount()+entry.getNumberOf()*entry.getValue());
		}
		int foodCount = Data.main.getFoodCount();

		Era next = null;

		boolean found = false;
		for(Era era: Data.getEraList()){
			if(found){
				next = era;
				break;
			}
			if(era.equals(thisEra)){
				found = true;
			}
		}

		if(populationCount<2) Data.main.setPopulationCount(2);

		if(populationCount>next.getPop_req()) Data.main.setPopulationCount(next.getPop_req());

		if(populationCount<next.getPop_req()) {
			if (buildingCount > populationCount && foodCount > populationCount)
				if (populationCount > 1000) {
					int randomInt = (int) ((ThreadLocalRandom.current().nextGaussian() / 2 + 0.5) * populationCount / 500);
					Data.main.setPopulationCount(populationCount + randomInt);
				} else {
					for (int i = 1; i < populationCount; i++) {
						int randomInt = random.nextInt(1000);
						if (randomInt == 42) {
							Data.main.setPopulationCount(populationCount + 1);
						}
					}
				}
		}
		if ((buildingCount < populationCount || foodCount < populationCount) && populationCount > 2)
			if (populationCount > 1000) {
				int randomInt = (int) ((ThreadLocalRandom.current().nextGaussian() / 2 + 0.5) * populationCount / 500);
				Data.main.setPopulationCount(populationCount - randomInt);
			} else {
				for (int i = 1; i < populationCount; i++) {
					int randomInt = random.nextInt(1000);
					if (randomInt == 888) {
						Data.main.setPopulationCount(populationCount - 1);
					}
				}
			}
	}
	
	public void createTables(){
		Data.setTableInfo(
			createBuildingsTable(),
			createFoodTable(),
			createResourcesTable(),
			createSpecialTable()
		);
	}
	private TableInfo createBuildingsTable(){
		TableInfo buildingInfo = new TableInfo(ResourceType.BUILDINGS);

		ArrayList capitalArray = new ArrayList();
		bundleResources(capitalArray, 10000, 20000, 5000, 100000, 100, 25000, 25000, 30000, 50, 0);
		BuildingEntry capital = (BuildingEntry) buildingInfo.addEntry("Capital City", 10000000, Data.getEraList().get(7), null, capitalArray, null, null);

		ArrayList cityArray = new ArrayList();
		bundleResources(cityArray, 5000, 10000, 1000, 1200, 50, 10000, 10000, 10000, 0, 0);
		BuildingEntry city = (BuildingEntry) buildingInfo.addEntry("City", 5000000, Data.getEraList().get(6), null, cityArray, null, null);

		ArrayList townArray = new ArrayList();
		bundleResources(townArray, 4000, 4000, 100, 100, 100, 1000, 1000, 0, 0, 0);
		BuildingEntry town = (BuildingEntry) buildingInfo.addEntry("Town", 250000, Data.getEraList().get(5), null, townArray, null, null);

		ArrayList villageArray = new ArrayList();
		bundleResources(villageArray, 1000, 1000, 50, 40, 500, 500, 0, 0, 0, 0);
		ArrayList toTownArray = new ArrayList();
		bundleResources(toTownArray, 3000, 3000, 50, 60, 0, 500, 1000, 0, 0, 0);
		BuildingEntry village = (BuildingEntry) buildingInfo.addEntry("Village", 20000, Data.getEraList().get(4), town, villageArray, toTownArray, null);

		ArrayList hamletArray = new ArrayList();
		bundleResources(hamletArray, 500, 250, 25, 20, 50, 0, 0, 0, 0, 0);
		BuildingEntry hamlet = (BuildingEntry) buildingInfo.addEntry("Hamlet", 200, Data.getEraList().get(3), null, hamletArray, null, null);

		ArrayList houseArray = new ArrayList();
		bundleResources(houseArray, 100, 60, 25, 15, 0, 0, 0, 0, 0, 0);
		ArrayList toVillageArray = new ArrayList();
		bundleResources(toVillageArray, 400, 180, 0, 5, 50, 0, 0, 0, 0, 0);
		BuildingEntry house = (BuildingEntry) buildingInfo.addEntry("House", 50, Data.getEraList().get(2), village, houseArray, toVillageArray, null);

		ArrayList shackArray = new ArrayList();
		bundleResources(shackArray, 50, 25, 5, 0, 0, 0, 0, 0, 0, 0);
		BuildingEntry shack = (BuildingEntry) buildingInfo.addEntry("Shack", 25, Data.getEraList().get(1), null, shackArray, null, null);

		ArrayList caveArray = new ArrayList();
		bundleResources(caveArray, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		buildingInfo.addEntry("Cave", 4, Data.getEraList().get(0), null, caveArray, null, null);

		return buildingInfo;
	}
	private TableInfo createFoodTable(){
		TableInfo foodInfo = new TableInfo(ResourceType.FOOD);

		ArrayList hydroArray = new ArrayList();
		bundleResources(hydroArray, 0, 0, 0, 0, 0, 1000, 1000, 10000, 100, 0);
		FoodEntry hydro = (FoodEntry) foodInfo.addEntry("Hydroponics", 100000000, Data.getEraList().get(7), null, hydroArray, null, null);

		ArrayList iFarmArray = new ArrayList();
		bundleResources(iFarmArray, 2500, 1000, 100, 100, 500, 75, 40, 0, 0, 0);
		FoodEntry iFarm = (FoodEntry) foodInfo.addEntry("Industrial Farm", 25000000, Data.getEraList().get(6), null, iFarmArray, null, null);

		ArrayList lFarmArray = new ArrayList();
		bundleResources(lFarmArray, 1000, 500, 25, 40, 250, 15, 0, 0, 0, 0);
		FoodEntry lFarm = (FoodEntry) foodInfo.addEntry("Large Farm", 1000000, Data.getEraList().get(5), null, lFarmArray, null, null);

		ArrayList barnArray = new ArrayList();
		bundleResources(barnArray, 250, 50, 10, 0, 25, 0, 0, 0, 0, 0);
		FoodEntry barn = (FoodEntry) foodInfo.addEntry("Barn", 10000, Data.getEraList().get(4), null, barnArray, null, null);

		ArrayList fishingArray = new ArrayList();
		bundleResources(fishingArray, 100, 0, 0, 10, 0, 0, 0, 0, 0, 0);
		FoodEntry fishing = (FoodEntry) foodInfo.addEntry("Fishing", 250, Data.getEraList().get(3), null, fishingArray, null, null);

		ArrayList fieldArray = new ArrayList();
		bundleResources(fieldArray, 50, 5, 0, 0, 0, 0, 0, 0, 0, 0);
		ArrayList toFieldArray = new ArrayList();
		bundleResources(toFieldArray, 35, 3, 0, 0, 0, 0, 0, 0, 0, 0);
		FoodEntry field = (FoodEntry) foodInfo.addEntry("Farm", 100, Data.getEraList().get(2), null, fieldArray, null, null);

		ArrayList sfieldArray = new ArrayList();
		bundleResources(sfieldArray, 15, 1, 0, 0, 0, 0, 0, 0, 0, 0);
		FoodEntry smallfield = (FoodEntry) foodInfo.addEntry("Small Farm", 20, Data.getEraList().get(1), field, sfieldArray, toFieldArray, null);


		foodInfo.addEntry(new Timeout("Hunt Food", 10, Data.getEraList().get(0), smallfield, null, null, 3000)).setUpgradable(false);

		return foodInfo;
	}
	private TableInfo createResourcesTable(){
		TableInfo resourcesInfo = new TableInfo(ResourceType.RESOURCES);

		final TableEntry empty = new ResourcesEntry(null, 0,null,null,null, null);

		//Don't need to be further down as don't rely on TableEntry's
		ResourceBundle woodmill_r = new Resource_ResourceBundle(ResourceMaterial.WOOD, empty);
		ResourceBundle quarry_r = new Resource_ResourceBundle(ResourceMaterial.STONE, empty);
		ResourceBundle cast_r = new Resource_ResourceBundle(ResourceMaterial.BRONZE, empty);
		ResourceBundle forge_r = new Resource_ResourceBundle(ResourceMaterial.IRON, empty);
		ResourceBundle mould_r = new Resource_ResourceBundle(ResourceMaterial.CLAY, empty);
		ResourceBundle oven_r = new Resource_ResourceBundle(ResourceMaterial.BRICK, empty);
		ResourceBundle kiln_r = new Resource_ResourceBundle(ResourceMaterial.CONCRETE, empty);
		ResourceBundle refine_r = new Resource_ResourceBundle(ResourceMaterial.STEEL, empty);
		ResourceBundle compress_r = new Resource_ResourceBundle(ResourceMaterial.GEMS, empty);

		ArrayList woodmill_l = new ArrayList();
		woodmill_l.add(new RequiredResource(ResourceMaterial.WOOD, 100));
		woodmill_l.add(new RequiredResource(ResourceMaterial.STONE, 10));

		ArrayList quarry_l = new ArrayList();
		quarry_l.add(new RequiredResource(ResourceMaterial.WOOD, 40));
		quarry_l.add(new RequiredResource(ResourceMaterial.STONE, 100));

		ArrayList cast_l = new ArrayList();
		cast_l.add(new RequiredResource(ResourceMaterial.WOOD, 10));
		cast_l.add(new RequiredResource(ResourceMaterial.STONE, 100));
		cast_l.add(new RequiredResource(ResourceMaterial.BRONZE, 25));
		cast_l.add(new RequiredResource(ResourceMaterial.IRON, 5));

		ArrayList forge_l = new ArrayList();
		forge_l.add(new RequiredResource(ResourceMaterial.WOOD, 25));
		forge_l.add(new RequiredResource(ResourceMaterial.STONE, 150));
		forge_l.add(new RequiredResource(ResourceMaterial.BRONZE, 75));
		forge_l.add(new RequiredResource(ResourceMaterial.IRON, 100));

		ArrayList mould_l = new ArrayList();
		mould_l.add(new RequiredResource(ResourceMaterial.WOOD, 5));
		mould_l.add(new RequiredResource(ResourceMaterial.STONE, 200));
		mould_l.add(new RequiredResource(ResourceMaterial.BRONZE, 100));
		mould_l.add(new RequiredResource(ResourceMaterial.IRON, 100));
		mould_l.add(new RequiredResource(ResourceMaterial.CLAY, 100));
		mould_l.add(new RequiredResource(ResourceMaterial.BRICK, 25));

		ArrayList oven_l = new ArrayList();
		oven_l.add(new RequiredResource(ResourceMaterial.STONE, 500));
		oven_l.add(new RequiredResource(ResourceMaterial.BRONZE, 50));
		oven_l.add(new RequiredResource(ResourceMaterial.IRON, 150));
		oven_l.add(new RequiredResource(ResourceMaterial.CLAY, 50));
		oven_l.add(new RequiredResource(ResourceMaterial.BRICK, 125));
		oven_l.add(new RequiredResource(ResourceMaterial.CONCRETE, 5));

		ArrayList kiln_l = new ArrayList();
		kiln_l.add(new RequiredResource(ResourceMaterial.WOOD, 25));
		kiln_l.add(new RequiredResource(ResourceMaterial.STONE, 1000));
		kiln_l.add(new RequiredResource(ResourceMaterial.BRONZE, 250));
		kiln_l.add(new RequiredResource(ResourceMaterial.IRON, 300));
		kiln_l.add(new RequiredResource(ResourceMaterial.BRICK, 100));
		kiln_l.add(new RequiredResource(ResourceMaterial.CONCRETE, 50));
		kiln_l.add(new RequiredResource(ResourceMaterial.STEEL, 5));

		ArrayList refine_l = new ArrayList();
		refine_l.add(new RequiredResource(ResourceMaterial.WOOD, 250));
		refine_l.add(new RequiredResource(ResourceMaterial.STONE, 400));
		refine_l.add(new RequiredResource(ResourceMaterial.BRONZE, 600));
		refine_l.add(new RequiredResource(ResourceMaterial.IRON, 1000));
		refine_l.add(new RequiredResource(ResourceMaterial.BRICK, 200));
		refine_l.add(new RequiredResource(ResourceMaterial.CONCRETE, 150));
		refine_l.add(new RequiredResource(ResourceMaterial.STEEL, 100));
		refine_l.add(new RequiredResource(ResourceMaterial.GEMS, 1));

		ArrayList compress_l = new ArrayList();
		refine_l.add(new RequiredResource(ResourceMaterial.BRONZE, 1500));
		refine_l.add(new RequiredResource(ResourceMaterial.IRON, 500));
		refine_l.add(new RequiredResource(ResourceMaterial.CONCRETE, 250));
		refine_l.add(new RequiredResource(ResourceMaterial.STEEL, 1000));
		refine_l.add(new RequiredResource(ResourceMaterial.GEMS, 10));

		ArrayList clayNeeded = new ArrayList();
		clayNeeded.add(new RequiredResource(ResourceMaterial.CLAY, 1));

		final TableEntry woodmill = resourcesInfo.addEntry(new Multiplier("Woodmill", 1, Data.getEraList().get(1), woodmill_l, null, woodmill_r));
		ResourceBundle wood = new Resource_ResourceBundle(ResourceMaterial.WOOD, woodmill);
		resourcesInfo.addEntry(new Absolute("Gather Wood", 1, Data.getEraList().get(0), null, null, wood));
		
		final TableEntry quarry = resourcesInfo.addEntry(new Multiplier("Quarry Stone", 1, Data.getEraList().get(1), quarry_l, null, quarry_r));
		ResourceBundle stone = new Resource_ResourceBundle(ResourceMaterial.STONE, quarry);
		resourcesInfo.addEntry(new Absolute("Mine Stone", 1, Data.getEraList().get(0), null, null, stone));
		
		final TableEntry cast = resourcesInfo.addEntry(new Multiplier("Cast Bronze", 1, Data.getEraList().get(2), cast_l, null, cast_r));
		ResourceBundle bronze = new Resource_ResourceBundle(ResourceMaterial.BRONZE, cast);
		resourcesInfo.addEntry(new Absolute("Smelt Bronze", 1, Data.getEraList().get(1), null, null, bronze));
		
		final TableEntry forge = resourcesInfo.addEntry(new Multiplier("Forge Iron", 1, Data.getEraList().get(3), forge_l, null, forge_r));
		ResourceBundle iron = new Resource_ResourceBundle(ResourceMaterial.IRON, forge);
		resourcesInfo.addEntry(new Absolute("Mine Iron", 1, Data.getEraList().get(2), null, null, iron));

		final TableEntry mould = resourcesInfo.addEntry(new Multiplier("Mould Clay", 1, Data.getEraList().get(4), mould_l, null, mould_r));
		ResourceBundle clay = new Resource_ResourceBundle(ResourceMaterial.CLAY, mould);
		resourcesInfo.addEntry(new Absolute("Collect Clay", 1, Data.getEraList().get(3), null, null, clay));

		final TableEntry oven = resourcesInfo.addEntry(new Multiplier("Cook Brick", 1, Data.getEraList().get(5), oven_l, null, oven_r));
		ResourceBundle brick = new Resource_ResourceBundle(ResourceMaterial.BRICK, oven);
		resourcesInfo.addEntry(new Absolute("Create Brick", 1, Data.getEraList().get(4), clayNeeded, null, brick));

		final TableEntry kiln = resourcesInfo.addEntry(new Multiplier("Fire Concrete", 1, Data.getEraList().get(6), kiln_l, null, kiln_r));
		ResourceBundle concrete = new Resource_ResourceBundle(ResourceMaterial.CONCRETE, kiln);
		resourcesInfo.addEntry(new Absolute("Mix Concrete", 1, Data.getEraList().get(5), clayNeeded, null, concrete));

		final TableEntry refine = resourcesInfo.addEntry(new Multiplier("Refine Steel", 1, Data.getEraList().get(7), refine_l, null, refine_r));
		ResourceBundle steel = new Resource_ResourceBundle(ResourceMaterial.STEEL, refine);
		resourcesInfo.addEntry(new Absolute("Smelt Steel", 1, Data.getEraList().get(6), null, null, steel));

		final TableEntry compress = resourcesInfo.addEntry(new Multiplier("Compress Gem",1, Data.getEraList().get(8), compress_l, null, compress_r));
		ResourceBundle gems = new Resource_ResourceBundle(ResourceMaterial.GEMS, compress);
		resourcesInfo.addEntry(new Gems("Mine Gems", 1, Data.getEraList().get(7), null, null, gems));

		ResourceBundle carbon = new Resource_ResourceBundle(ResourceMaterial.CARBON, empty);
		resourcesInfo.addEntry(new Absolute("Carbon", 1, Data.getEraList().get(8), null, null, carbon));

		return resourcesInfo;
	}
	private TableInfo createSpecialTable(){
		TableInfo specialInfo = new TableInfo(ResourceType.SPECIAL);

		ArrayList<RequiredResource> stone = new ArrayList<RequiredResource>();
		bundleResources(stone, 0, 500, 0, 0, 0, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Stonehenge", 50, Data.getEraList().get(0), null, stone, null, new Special_ResourceBundle(1));

		ArrayList<RequiredResource> pyram = new ArrayList<RequiredResource>();
		bundleResources(pyram, 0, 1000, 350, 0, 0, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Pyramids", 100, Data.getEraList().get(1), null, pyram, null, new Special_ResourceBundle(2));

		ArrayList<RequiredResource> wall = new ArrayList<RequiredResource>();
		bundleResources(wall, 0, 1500, 50, 1000, 0, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Great Wall", 250, Data.getEraList().get(2), null, wall, null, new Special_ResourceBundle(3));

		ArrayList<RequiredResource> colosseum = new ArrayList<RequiredResource>();
		bundleResources(colosseum, 0, 1500, 0, 500, 1000, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Colosseum", 750, Data.getEraList().get(3), null, colosseum, null, new Special_ResourceBundle(4));

		ArrayList<RequiredResource> pisa = new ArrayList<RequiredResource>();
		bundleResources(pisa, 0, 200, 0, 1000, 0, 1500, 0, 0, 0, 0);
		specialInfo.addEntry("Leaning Tower", 1000, Data.getEraList().get(4), null, pisa, null, new Special_ResourceBundle(5));

		ArrayList<RequiredResource> taj = new ArrayList<RequiredResource>();
		bundleResources(taj, 1000, 500, 500, 0, 0, 0, 1500, 0, 0, 0);
		specialInfo.addEntry("Taj Mahal", 1200, Data.getEraList().get(5), null, taj, null, new Special_ResourceBundle(6));

		ArrayList<RequiredResource> eiffel = new ArrayList<RequiredResource>();
		bundleResources(eiffel, 0, 0, 150, 1000, 0, 0, 0, 2500, 0, 0);
		specialInfo.addEntry("Eiffel Tower", 1500, Data.getEraList().get(6), null, eiffel, null, new Special_ResourceBundle(7));

		ArrayList<RequiredResource> liberty = new ArrayList<RequiredResource>();
		bundleResources(liberty, 0, 2000, 0, 200, 0, 0, 100, 500, 150, 0);
		specialInfo.addEntry("Statue of Liberty", 2000, Data.getEraList().get(7), null, liberty, null, new Special_ResourceBundle(8));

		ArrayList<RequiredResource> space = new ArrayList<RequiredResource>();
		bundleResources(space, 0, 0, 1000, 0, 1000, 1000, 1000, 2500, 500, 5000);
		specialInfo.addEntry("Space Island", 2500, Data.getEraList().get(8), null, space, null, new Special_ResourceBundle(9));

		return specialInfo;
	}

	private void bundleResources(ArrayList array, int wood, int stone, int bronze, int iron, int clay, int brick, int concrete, int steel, int gems, int carbon){
		array.add(new RequiredResource(ResourceMaterial.WOOD, wood));
		array.add(new RequiredResource(ResourceMaterial.STONE, stone));
		array.add(new RequiredResource(ResourceMaterial.BRONZE, bronze));
		array.add(new RequiredResource(ResourceMaterial.IRON, iron));
		array.add(new RequiredResource(ResourceMaterial.CLAY, clay));
		array.add(new RequiredResource(ResourceMaterial.BRICK, brick));
		array.add(new RequiredResource(ResourceMaterial.CONCRETE, concrete));
		array.add(new RequiredResource(ResourceMaterial.STEEL, steel));
		array.add(new RequiredResource(ResourceMaterial.GEMS, gems));
		array.add(new RequiredResource(ResourceMaterial.CARBON, carbon));
	}

	/**
	 * For the planet ui stuff done when the planet is clicked
	 */
	public boolean planetClicked(){
		Planet planet = Data.main.getPlanet();

		planet.setMultiplier(clickMultiplier * planet.getMultiplier());
		planet.setX(Gdx.graphics.getWidth()/2- planet.getWidth()/2);
		planet.setY(Data.ui.planetY);

		planet.setClicked(true);
		planetClickedAction();
		return true;
	}
	/**
	 * For the actions done when the planet is clicked
	 */
	public void planetClickedAction(){
		updateNumberOf();

		TableEntry entry = Data.getSelectedEntry();
		if(entry==null) return; //Do nothing if none selected

		if(entry.isCreateClicked()){
			if(hasResources(entry.getResourcesNeeded())){
				entry.onClick();

				if(Data.getSelectedEntry().getResourcesNeeded()!=null)
					for (RequiredResource resource : Data.getSelectedEntry().getResourcesNeeded())
						resource.setResourceNumberText();
				if(Data.getSelectedEntry().getResourcesNeededToUpgrade()!=null)
					for (RequiredResource resource : Data.getSelectedEntry().getResourcesNeededToUpgrade())
						resource.setResourceNumberText();

				Data.ui.loadSideBar(Data.getSelectedEntry(), true);
			}else{
				printInsufficientResources();
			}
		}
		if(entry.isUpgradeClicked()){
			if(hasResources(entry.getResourcesNeededToUpgrade())) {
				if (entry.getNumberOf() > 0) {
					entry.subFromEntry();
					entry.getUpgradeTo().addToEntry();
					subtractResources(entry.getResourcesNeededToUpgrade());

					if(Data.getSelectedEntry().getResourcesNeeded()!=null)
						for (RequiredResource resource : Data.getSelectedEntry().getResourcesNeeded())
							resource.setResourceNumberText();
					if(Data.getSelectedEntry().getResourcesNeededToUpgrade()!=null)
						for (RequiredResource resource : Data.getSelectedEntry().getResourcesNeededToUpgrade())
							resource.setResourceNumberText();

					Data.ui.loadSideBar(Data.getSelectedEntry(), false);
				}
			}else{
				printInsufficientResources();
			}
		}
	}
	public boolean planetUnclicked(){
		Planet planet = Data.main.getPlanet();
		planet.setMultiplier(planet.getMultiplier() / clickMultiplier);
		planet.setClicked(false);
		Data.ui.updateResources();

		planet.setX(Gdx.graphics.getWidth()/2- planet.getWidth()/2);
		planet.setY(Data.ui.planetY);
		return true;
	}

	public boolean hasResources(ArrayList<RequiredResource> resources){
		if(resources==null) return true;

		for(RequiredResource resource: resources){
			switch(resource.getMaterial()){
				case WOOD:
					if(Data.main.getWoodCount()<resource.getNumberRequired()) return false;
					break;
				case STONE:
					if(Data.main.getStoneCount()<resource.getNumberRequired()) return false;
					break;
				case IRON:
					if(Data.main.getIronCount()<resource.getNumberRequired()) return false;
					break;
				case BRONZE:
					if(Data.main.getBronzeCount()<resource.getNumberRequired()) return false;
					break;
			}
		}
		return true;
	}

	public void subtractResources(ArrayList<RequiredResource> resources){
		if(resources==null) return;

		for(RequiredResource resource: resources){
			switch(resource.getMaterial()){
				case WOOD:
					Data.main.setWoodCount(Data.main.getWoodCount()-resource.getNumberRequired());
					break;
				case STONE:
					Data.main.setStoneCount(Data.main.getStoneCount()-resource.getNumberRequired());
					break;
				case IRON:
					Data.main.setIronCount(Data.main.getIronCount()-resource.getNumberRequired());
					break;
				case BRONZE:
					Data.main.setBronzeCount(Data.main.getBronzeCount()-resource.getNumberRequired());
					break;
			}
		}
	}

	public void printInsufficientResources(){
		final Image insufficientResources = Data.ui.getInsufficientResources();

		insufficientResources.setColor(1f,1f,1f,1f);

		new Thread(){
			@Override
			public void run() {
				numberOfIRThreads++;
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				float alpha = insufficientResources.getColor().a;
				while(alpha!=0){
					if(numberOfIRThreads>1){
						numberOfIRThreads--;
						return;
					}
					alpha = insufficientResources.getColor().a;
					insufficientResources.setColor(1f,1f,1f,alpha-0.001f);
					try {
						sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				numberOfIRThreads--;
				Thread.currentThread().interrupt();
			}
		}.start();

	}

	public void updateNumberOf(){
		ArrayList<TableEntry> buildingEntries = Data.getBuildingTable().getEntries();
		Data.main.setBuildingCount(
				buildingEntries.get(0).getValue()*buildingEntries.get(0).getNumberOf() +
						buildingEntries.get(1).getValue()*buildingEntries.get(1).getNumberOf() +
						buildingEntries.get(2).getValue()*buildingEntries.get(2).getNumberOf()
		);
		ArrayList<TableEntry> foodEntries = Data.getFoodTable().getEntries();
		Data.main.setBuildingCount(
				foodEntries.get(0).getValue()*foodEntries.get(0).getNumberOf() +
						foodEntries.get(1).getValue()*foodEntries.get(1).getNumberOf() +
						foodEntries.get(2).getValue()*foodEntries.get(2).getNumberOf()
		);
	}

	public void addResource(ResourceMaterial material, int amount){
		switch(material){
			case WOOD:
				Data.main.addWoodCount(amount);
				break;
			case STONE:
				Data.main.addStoneCount(amount);
				break;
			case IRON:
				Data.main.addIronCount(amount);
				break;
			case BRONZE:
				Data.main.addBronzeCount(amount);
				break;
		}
	}

	public void removeEntryAndUnclick(){
		if(Data.getSelectedEntry()!=null) {
			Data.getSelectedEntry().setCreateClicked(false);
			Data.getSelectedEntry().setUpgradeClicked(false);
		}
		Data.ui.loadSideBar(null, true);
		Data.setSelectedEntry(null);
	}

	public static class buildingListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.getCurrentTable()!=null) Data.getCurrentTable().unclickAll();

			Data.mechanics.removeEntryAndUnclick();

			Data.ui.setAllTablesInvisible();
			Data.ui.refreshTable();
			if(Data.getResourceType()==ResourceType.BUILDINGS){
				Data.setResourceType(ResourceType.NONE);
				Data.main.setBuildingTableVisible(false);
				Data.ui.refreshBuildingTable();
			} else {
				Data.setResourceType(ResourceType.BUILDINGS);
				Data.main.setBuildingTableVisible(true);
			}
			Data.ui.updateResources();
			Data.ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			return true;
		}
	}
	public static class foodListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.getCurrentTable()!=null) Data.getCurrentTable().unclickAll();

			Data.mechanics.removeEntryAndUnclick();

			Data.ui.setAllTablesInvisible();
			Data.ui.refreshTable();
			if(Data.getResourceType()==ResourceType.FOOD){
				Data.setResourceType(ResourceType.NONE);
				Data.main.setFoodTableVisible(false);
				Data.ui.refreshFoodTable();
			} else {
				Data.setResourceType(ResourceType.FOOD);
				Data.main.setFoodTableVisible(true);
			}
			Data.ui.updateResources();
			Data.ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			return true;
		}
	}
	public static class resourcesListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.getCurrentTable()!=null) Data.getCurrentTable().unclickAll();

			Data.mechanics.removeEntryAndUnclick();

			Data.ui.setAllTablesInvisible();
			Data.ui.refreshTable();
			if(Data.getResourceType()==ResourceType.RESOURCES){
				Data.setResourceType(ResourceType.NONE);
				Data.main.setResourcesTableVisible(false);
				Data.ui.refreshResourcesTable();
			} else {
				Data.setResourceType(ResourceType.RESOURCES);
				Data.main.setResourcesTableVisible(true);
			}
			Data.ui.updateResources();
			Data.ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			return true;
		}
	}
	public static class specialListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.getCurrentTable()!=null) Data.getCurrentTable().unclickAll();

			Data.mechanics.removeEntryAndUnclick();

			Data.ui.setAllTablesInvisible();
			Data.ui.refreshTable();
			if(Data.getResourceType()==ResourceType.SPECIAL){
				Data.setResourceType(ResourceType.NONE);
				Data.main.setSpecialTableVisible(false);
				Data.ui.refreshBuildingTable();
			} else {
				Data.setResourceType(ResourceType.SPECIAL);
				Data.main.setSpecialTableVisible(true);
			}
			Data.ui.updateResources();
			Data.ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			return true;
		}
	}
	public static class planetListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.main.getPlanet().pointInsidePlanet(x+Data.main.getPlanet().getX(),y+Data.main.getPlanet().getY()))
				return Data.mechanics.planetClicked();
			return false;
		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			Data.mechanics.planetUnclicked();
		}
	}

	public static class settingsListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.ui.getSettings().getSettingsGroup().setVisible(!Data.ui.getSettings().getSettingsGroup().isVisible());

			Touchable touchable;
			if(!Data.ui.getSettings().getSettingsGroup().isVisible()) {
				touchable = Touchable.enabled;
			}else {
				touchable = Touchable.disabled;
			}

			Data.ui.setEverythingTouchable(touchable);

			return true;
		}
	}

	public void dispose(){

	}

	public SavegameFile getFile() {
		return file;
	}

	public void setThisEra(Era thisEra) {
		this.thisEra = thisEra;
	}
}
