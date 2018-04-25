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
import com.blackphoton.planetclicker.objectType.table.entries.resources.Multiplier;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Resource_ResourceBundle;
import com.blackphoton.planetclicker.objectType.table.entries.special.Special_ResourceBundle;
import com.blackphoton.planetclicker.objectType.table.entries.template.*;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.resources.ResourceMaterial;

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

		ArrayList townArray = new ArrayList();
		townArray.add(new RequiredResource(ResourceMaterial.WOOD, 100));
		townArray.add(new RequiredResource(ResourceMaterial.STONE, 25));
		townArray.add(new RequiredResource(ResourceMaterial.BRONZE, 5));
		townArray.add(new RequiredResource(ResourceMaterial.IRON, 1));
		BuildingEntry town = (BuildingEntry) buildingInfo.addEntry("Town", 20000, Data.getEraList().get(2), null, townArray, null, null);

		ArrayList villageArray = new ArrayList();
		villageArray.add(new RequiredResource(ResourceMaterial.WOOD, 50));
		villageArray.add(new RequiredResource(ResourceMaterial.BRONZE, 1));
		villageArray.add(new RequiredResource(ResourceMaterial.STONE, 10));
		ArrayList toTownArray = new ArrayList();
		toTownArray.add(new RequiredResource(ResourceMaterial.WOOD, 25));
		toTownArray.add(new RequiredResource(ResourceMaterial.STONE, 10));
		toTownArray.add(new RequiredResource(ResourceMaterial.BRONZE, 3));
		toTownArray.add(new RequiredResource(ResourceMaterial.IRON, 1));
		BuildingEntry village = (BuildingEntry) buildingInfo.addEntry("Village", 100, Data.getEraList().get(1), town, villageArray, toTownArray, null);

		ArrayList houseArray = new ArrayList();
		ArrayList toVillageArray = new ArrayList();
		toVillageArray.add(new RequiredResource(ResourceMaterial.WOOD, 25));
		toVillageArray.add(new RequiredResource(ResourceMaterial.STONE, 5));
		toVillageArray.add(new RequiredResource(ResourceMaterial.BRONZE, 1));
		houseArray.add(new RequiredResource(ResourceMaterial.WOOD, 5));
		buildingInfo.addEntry("House", 4, Data.getEraList().get(0), village, houseArray, toVillageArray, null);

		return buildingInfo;
	}
	private TableInfo createFoodTable(){
		TableInfo foodInfo = new TableInfo(ResourceType.FOOD);

		ArrayList farmArray = new ArrayList();
		farmArray.add(new RequiredResource(ResourceMaterial.WOOD, 40));
		farmArray.add(new RequiredResource(ResourceMaterial.STONE, 3));
		ArrayList toFarmArray = new ArrayList();
		toFarmArray.add(new RequiredResource(ResourceMaterial.WOOD, 15));
		toFarmArray.add(new RequiredResource(ResourceMaterial.STONE, 1));
		FoodEntry farm = (FoodEntry) foodInfo.addEntry("Farm", 5000, Data.getEraList().get(2), null, farmArray, null, null);

		ArrayList sfarmArray = new ArrayList();
		sfarmArray.add(new RequiredResource(ResourceMaterial.WOOD, 15));
		sfarmArray.add(new RequiredResource(ResourceMaterial.STONE, 1));
		FoodEntry smallfarm = (FoodEntry) foodInfo.addEntry("Small Farm", 20, Data.getEraList().get(1), farm, sfarmArray, toFarmArray, null);


		foodInfo.addEntry(new Timeout("Hunt Food", 10, Data.getEraList().get(0), smallfarm, null, null, 3000)).setUpgradable(false);

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
		
		return resourcesInfo;
	}
	private TableInfo createSpecialTable(){
		TableInfo specialInfo = new TableInfo(ResourceType.SPECIAL);

		ArrayList<RequiredResource> stone = new ArrayList<RequiredResource>();
		stone.add(new RequiredResource(ResourceMaterial.STONE, 150));
		specialInfo.addEntry("Stonehenge", 50, Data.getEraList().get(0), null, stone, null, new Special_ResourceBundle(1));

		ArrayList<RequiredResource> pyram = new ArrayList<RequiredResource>();
		pyram.add(new RequiredResource(ResourceMaterial.STONE, 350));
		pyram.add(new RequiredResource(ResourceMaterial.BRONZE, 50));
		specialInfo.addEntry("Pyramids", 100, Data.getEraList().get(1), null, pyram, null, new Special_ResourceBundle(2));

		ArrayList<RequiredResource> wall = new ArrayList<RequiredResource>();
		wall.add(new RequiredResource(ResourceMaterial.STONE, 600));
		wall.add(new RequiredResource(ResourceMaterial.BRONZE, 20));
		wall.add(new RequiredResource(ResourceMaterial.IRON, 200));
		specialInfo.addEntry("Great Wall", 250, Data.getEraList().get(2), null, wall, null, new Special_ResourceBundle(3));

		return specialInfo;
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
				if(entry instanceof SpecialEntry) {
					if (((SpecialEntry) entry).isCanBuild()){
						if(((SpecialEntry) entry).getPercent()==100) return;

						entry.addToEntry();
						entry.setNumberLabelText();

						if(((SpecialEntry) entry).getPercent()==100){
							if(!((SpecialEntry) entry).isComplete()){
								((SpecialEntry) entry).setComplete(true);
								boolean found = false;
								for(Era era: Data.getEraList()){
									if(found){
										Data.setCurrentEra(era);
										thisEra = Data.getCurrentEra();
										Data.ui.updateEra();
										break;
									}
									if(era.equals(thisEra)) found = true;
								}
							}
							return;
						}

						return;
					}else{
						((SpecialEntry) entry).setCanBuild(true);
						subtractResources(entry.getResourcesNeeded());
						entry.setResourcesNeeded(null);
						Data.ui.loadSideBar(Data.getSelectedEntry(), true);

						entry.addToEntry();
						return;
					}
				}

				entry.addToEntry();
				subtractResources(entry.getResourcesNeeded());

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

			if(entry instanceof Absolute) {
				addResource(((ResourcesEntry) entry).getMaterial(), entry.getValue());
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
		//Data.ui.refreshTable();
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
}
