package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.file.SavegameFile;
import com.blackphoton.planetclicker.messages.ConfirmBox;
import com.blackphoton.planetclicker.messages.Info;
import com.blackphoton.planetclicker.messages.TutorialCollection;
import com.blackphoton.planetclicker.messages.TutorialInfo;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.building.Cave;
import com.blackphoton.planetclicker.objectType.table.entries.food.Hunt;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Absolute;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Gems;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Multiplier;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Resource_ResourceBundle;
import com.blackphoton.planetclicker.objectType.table.entries.special.Special_ResourceBundle;
import com.blackphoton.planetclicker.objectType.table.entries.template.*;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for game mechanics
 */
public class Mechanics {
	private Random random;
	/**
	 * Percentage of original size to shrink to when clicked
	 */
	private final float clickMultiplier = 0.95f;
	/**
	 * The Savegame object. Use to write/read to/from the main savegame
	 */
	private final SavegameFile file = new SavegameFile();

	private ConfirmBox test;
	private TutorialInfo page1;
	private TutorialInfo page2;
	private TutorialInfo page3;
	private TutorialInfo page4;
	private TutorialInfo page5;
	private TutorialInfo page6;
	private TutorialInfo page7;
	private TutorialInfo page8;
	private TutorialInfo page9;
	private TutorialInfo page10;
	private TutorialInfo page11;
	private TutorialInfo page12;
	private TutorialInfo page13;
	private TutorialCollection collection;

	/**
	 * The thread that saves every second
	 */
	private Thread saveThread;

	void create(){
		random = new Random();

		//Tutorial pages
		test = new ConfirmBox("Tutorial", "Would you like to view the tutorial?", null, new tutorialStart());
		page1 = new TutorialInfo("Tutorial", "Welcome to Aedmun! The aim of the game is to get as high a population as possible. To do this, you need to give the planet enough buildings and food to live.", "Ok", null,1);
		page2 = new TutorialInfo("Tutorial", "Click on the apple to open the food menu.", null, 2);
		page3 = new TutorialInfo("Tutorial", "Now you can see all the options, click create to  choose 'Hunt Food'", null, 3);
		page4 = new TutorialInfo("Tutorial", "Great! Click the planet to hunt food.", null, 4);
		page5 = new TutorialInfo("Tutorial", "Well done! Some structures such as this work differently. Hunt food, for example, goes down over time as food is eaten. Later you can get farms which are permanent. 50 food should be enough for now. Simply click the planet while create is selected.", null, 5);
		page6 = new TutorialInfo("Tutorial", "Now let's click on the house to see what buildings we can make.", null, 6);
		page7 = new TutorialInfo("Tutorial", "This building needs resources to make. You can see them on the right hand side. Resources have their own tab.", null, 7);
		page8 = new TutorialInfo("Tutorial", "Resources and hunted food are free, but everything else costs resources. Collect enough stone for a house.", null, 8);
		page9 = new TutorialInfo("Tutorial", "Great! Now you can buy a house in the buildings tab.", null, 9);
		page10 = new TutorialInfo("Tutorial", "Well done! If you wait and your food doesn't run out, you should see the population increase to 4. Build more houses and collect more food to let more people live.", "OK", null, 10);
		page11 = new TutorialInfo("Tutorial", "When you reach a population of 400, you'll be able to move onto the next by building a monument in the last tab. These have a high population, resource and click requirement, but will unlock higher populations and the abilities the new era offers.", "OK", null, 11);
		page12 = new TutorialInfo("Tutorial", "When you reach the next era, you'll  be able to increase your resources aquired per click using structures such as quarries.", "OK", null, 12);
		page13 = new TutorialInfo("Tutorial", "Have fun playing!", "OK", null, 13);
		createTutorial();

		if(file.exists()) {
			//Reads the savefile
			file.readGame();
		}else {
			//Start Tutorial
			Data.setTutorial(true);
		}

		//Saves the game every 1 second
		saveThread = (new Thread(){
			@Override
			public void run() {
				while (!this.isInterrupted()) {
					try{
						if (Data.main.POPULATION.getCount()!=0 && !Data.isPaused())
							file.saveGame();
						sleep(1000);
					}catch (InterruptedException e){
						break;
					}
				}
			}
		});
		saveThread.start();

		//Let's you finish any special buildings that have had the initial resource cost covered (from savefiles)
		for(TableEntry entry: Data.getSpecialTable().getEntries()){
			if(entry.getNumberOf()>0) {
				((SpecialEntry) entry).setCanBuild(true);
				entry.setResourcesNeeded(null);
			}
		}
	}
	void update(){
		//Calculated how much population can be supported by food and buildings by multiplying each entry by their value
		long populationCount = Data.main.POPULATION.getCount();
		Data.setBuildingCount(0);
		for(TableEntry entry:Data.getBuildingTable().getEntries()){
			Data.setBuildingCount(Data.getBuildingCount()+entry.getNumberOf()*entry.getValue());
		}
		long buildingCount = Data.getBuildingCount();
		Data.setFoodCount(0);
		for(TableEntry entry:Data.getFoodTable().getEntries()){
			Data.setFoodCount(Data.getFoodCount()+entry.getNumberOf()*entry.getValue());
		}
		long foodCount = Data.getFoodCount();

		if(Data.isTutorialRunning() && foodCount>0){
			Data.mechanics.getCollection().objectiveComplete(4);
		}
		if(Data.isTutorialRunning() && foodCount>=500){
			Data.mechanics.getCollection().objectiveComplete(5);
		}
		if(Data.isTutorialRunning() && Data.main.STONE.getCount()>=5){
			Data.mechanics.getCollection().objectiveComplete(8);
		}
		if(Data.isTutorialRunning() && buildingCount>=4){
			Data.mechanics.getCollection().objectiveComplete(9);
		}

		//Algorithm for getting the next era (the one after the current one)
		Era next = null;
		boolean found = false;
		for(Era era: Data.getEraList()){
			if(found){
				next = era;
				break;
			}
			if(era.equals(Data.getCurrentEra())){
				found = true;
			}
		}
		if(next==null) throw new NullPointerException("Could not find the next era");

		//Population should never go below 2
		if(populationCount<2) Data.main.POPULATION.setCount(2);
		populationCount = Data.main.POPULATION.getCount();

		//Stops you getting too high a population before the era increments
		if(populationCount>next.getPop_req()) Data.main.POPULATION.setCount(next.getPop_req());
		populationCount = Data.main.POPULATION.getCount();

		//Increases the population when you have enough food and buildings for more
		if(populationCount<next.getPop_req())
			if (buildingCount > populationCount && foodCount > populationCount) {
				if (populationCount > 1000) {
					int randomInt = Math.abs((int) ((random.nextGaussian() / 2 + 0.5) * populationCount / 500));
					Data.main.POPULATION.setCount(populationCount + randomInt);
					populationCount = Data.main.POPULATION.getCount();
				} else {
					for (int i = 1; i < populationCount; i++) {
						int randomInt = random.nextInt(1000);
						if (randomInt == 42) {
							Data.main.POPULATION.setCount(populationCount + 1);
							populationCount = Data.main.POPULATION.getCount();
						}
					}
				}
				//If the procedure of increasing the population increases it to a point that it will need to decrease again next, make it the maximum possible.
				//Eg. Population 9980, can support population 9990. Process of increasing population normally makes it become 10056 by chance (see ThreadLocalRandom.current().nextGaussian()).
				//This makes it become 9990 exactly, so it doesn't go too high and instantly need to decrease towards 9990 again.
				if (buildingCount < populationCount || foodCount < populationCount){
					Data.main.POPULATION.setCount(buildingCount<foodCount ? buildingCount:foodCount);
					populationCount = Data.main.POPULATION.getCount();
				}
			}

		//Increases the population when you don't have enough food and buildings for your current population
		if ((buildingCount < populationCount || foodCount < populationCount) && populationCount > 2) {
			if (populationCount > 1000) {
				int randomInt = Math.abs((int) ((random.nextGaussian() / 2 + 0.5) * populationCount / 500));
				Data.main.POPULATION.setCount(populationCount - randomInt);
				populationCount = Data.main.POPULATION.getCount();
			} else {
				for (int i = 1; i < populationCount; i++) {
					int randomInt = random.nextInt(1000);
					if (randomInt == 888) {
						Data.main.POPULATION.setCount(populationCount - 1);
						populationCount = Data.main.POPULATION.getCount();
					}
				}
			}
			//If the procedure of decreasing the population decreases it to a point that it will need to increase again next, make it the maximum possible.
			//Eg. Population 10000, can support population 9990. Process of decreasing population normally makes it become 9886 by chance (see ThreadLocalRandom.current().nextGaussian()).
			//This makes it become 9990 exactly, so it doesn't go too low and instantly need to increase towards 9990 again.
			if (buildingCount > populationCount || foodCount > populationCount) {
				Data.main.POPULATION.setCount(buildingCount<foodCount ? buildingCount:foodCount);
				populationCount = Data.main.POPULATION.getCount();
			}
		}

		// Ensures population never goes over limit
		if(populationCount>next.getPop_req()) populationCount = next.getPop_req();

		Data.main.POPULATION.setCount(populationCount);

		if(Data.isTutorial()) tutorial();
	}

	/**
	 * Creates data for all the 4 tables
	 */
	void createTables(){
		Data.setTableInfo(
			createBuildingsTable(),
			createFoodTable(),
			createResourcesTable(),
			createSpecialTable()
		);
	}
	private TableInfo createBuildingsTable(){
		TableInfo buildingInfo = new TableInfo(ResourceType.BUILDINGS);

		ArrayList<RequiredResource> capitalArray = new ArrayList<RequiredResource>();
		bundleResources(capitalArray, 0, 0, 0, 0, 0, 250, 250, 30, 3);
		BuildingEntry capital = (BuildingEntry) buildingInfo.addEntry("Capital City", 100000000, "capital.png", Data.getEraList().get(7), null, capitalArray, null, null);

		ArrayList<RequiredResource> cityArray = new ArrayList<RequiredResource>();
		bundleResources(cityArray, 0, 0, 0, 500, 0, 150, 100, 2);
		ArrayList<RequiredResource> toCapitalArray = new ArrayList<RequiredResource>();
		bundleResources(toCapitalArray, 0, 0, 0, 0, 0, 100, 100, 25, 3);
		BuildingEntry city = (BuildingEntry) buildingInfo.addEntry("City", 5000000, "city.png", Data.getEraList().get(6), capital, cityArray, toCapitalArray, null);

		ArrayList<RequiredResource> townArray = new ArrayList<RequiredResource>();
		bundleResources(townArray, 0, 250, 0, 250, 0, 10, 8);
		ArrayList<RequiredResource> toCityArray = new ArrayList<RequiredResource>();
		bundleResources(toCityArray, 0, 0, 0, 250, 0, 100, 100, 2);
		BuildingEntry town = (BuildingEntry) buildingInfo.addEntry("Town", 500000, "town.png", Data.getEraList().get(5), city, townArray, toCityArray, null);

		ArrayList<RequiredResource> villageArray = new ArrayList<RequiredResource>();
		bundleResources(villageArray, 100, 50, 0, 40, 0, 3);
		ArrayList<RequiredResource> toTownArray = new ArrayList<RequiredResource>();
		bundleResources(toTownArray, 0, 200, 0, 200, 0, 7, 8);
		BuildingEntry village = (BuildingEntry) buildingInfo.addEntry("Village", 20000, "village.png", Data.getEraList().get(4), town, villageArray, toTownArray, null);

		ArrayList<RequiredResource> hamletArray = new ArrayList<RequiredResource>();
		bundleResources(hamletArray, 25, 100, 10, 0, 2);
		ArrayList<RequiredResource> toVillageArray = new ArrayList<RequiredResource>();
		bundleResources(toVillageArray, 75, 0, 10, 30, 0, 2);
		BuildingEntry hamlet = (BuildingEntry) buildingInfo.addEntry("Hamlet", 2000, "hamlet.png", Data.getEraList().get(3), village, hamletArray, toVillageArray, null);

		ArrayList<RequiredResource> houseArray = new ArrayList<RequiredResource>();
		bundleResources(houseArray, 5, 15, 3, 1);
		ArrayList<RequiredResource> toHamletArray = new ArrayList<RequiredResource>();
		bundleResources(toHamletArray, 20, 85, 7, 0, 2);
		BuildingEntry house = (BuildingEntry) buildingInfo.addEntry("House", 50, "house.png", Data.getEraList().get(2), hamlet, houseArray, toHamletArray, null);

		ArrayList<RequiredResource> shackArray = new ArrayList<RequiredResource>();
		bundleResources(shackArray, 2, 10, 1);
		ArrayList<RequiredResource> toHouseArray = new ArrayList<RequiredResource>();
		bundleResources(toHouseArray, 3, 5,  2, 1);
		buildingInfo.addEntry("Shack", 25, "shack.png", Data.getEraList().get(1), house, shackArray, toHouseArray, null);

		ArrayList<RequiredResource> caveArray = new ArrayList<RequiredResource>();
		bundleResources(caveArray, 0, 2);
		buildingInfo.addEntry(new Cave("Cave", 4, "cave.png", Data.getEraList().get(0), null, caveArray, null));

		return buildingInfo;
	}
	private TableInfo createFoodTable(){
		TableInfo foodInfo = new TableInfo(ResourceType.FOOD);

		ArrayList<RequiredResource> hydroArray = new ArrayList<RequiredResource>();
		bundleResources(hydroArray, 0, 0, 0, 0, 0, 100, 75, 200, 1);
		foodInfo.addEntry("Hydroponics", 100000000, "hydroponics.png", Data.getEraList().get(7), null, hydroArray, null, null);

		ArrayList<RequiredResource> iFarmArray = new ArrayList<RequiredResource>();
		bundleResources(iFarmArray, 0, 500, 100, 100, 0, 0, 4);
		FoodEntry iFarm = (FoodEntry) foodInfo.addEntry("Industrial Farm", 25000000, "industrial_farm.png", Data.getEraList().get(6), null, iFarmArray, null, null);

		ArrayList<RequiredResource> lFarmArray = new ArrayList<RequiredResource>();
		bundleResources(lFarmArray, 350, 150, 0, 0, 25, 5);
		ArrayList<RequiredResource> toIFarmArray = new ArrayList<RequiredResource>();
		bundleResources(toIFarmArray, 0, 500, 75, 75, 0, 0, 40);
		FoodEntry lFarm = (FoodEntry) foodInfo.addEntry("Large Farm", 1000000, "large_farm.png", Data.getEraList().get(5), iFarm, lFarmArray, toIFarmArray, null);

		ArrayList<RequiredResource> barnArray = new ArrayList<RequiredResource>();
		bundleResources(barnArray, 150, 50, 10, 0, 5);
		foodInfo.addEntry("Barn", 10000, "barn.png", Data.getEraList().get(4), null, barnArray, null, null);

		ArrayList<RequiredResource> fishingArray = new ArrayList<RequiredResource>();
		bundleResources(fishingArray, 100, 0, 0, 10);
		foodInfo.addEntry("Fishing", 2500, "fishing.png", Data.getEraList().get(3), null, fishingArray, null, null);

		ArrayList<RequiredResource> fieldArray = new ArrayList<RequiredResource>();
		bundleResources(fieldArray, 15, 5);
		ArrayList<RequiredResource> toLFarmArray = new ArrayList<RequiredResource>();
		bundleResources(toLFarmArray, 950, 500, 0, 0, 240, 15);
		FoodEntry field = (FoodEntry) foodInfo.addEntry("Farm", 100, "farm.png", Data.getEraList().get(2), lFarm, fieldArray, toLFarmArray, null);

		ArrayList<RequiredResource> sfieldArray = new ArrayList<RequiredResource>();
		bundleResources(sfieldArray, 2, 5);
		ArrayList<RequiredResource> toFieldArray = new ArrayList<RequiredResource>();
		bundleResources(toFieldArray, 35, 3);
		FoodEntry smallfield = (FoodEntry) foodInfo.addEntry("Small Farm", 20, "small_farm.png", Data.getEraList().get(1), field, sfieldArray, toFieldArray, null);


		foodInfo.addEntry(new Hunt("Hunt Food", 10, "hunt.png", Data.getEraList().get(0), smallfield, null, null, 3000)).setUpgradable(false);

		return foodInfo;
	}
	private TableInfo createResourcesTable(){
		TableInfo resourcesInfo = new TableInfo(ResourceType.RESOURCES);

		final TableEntry empty = new ResourcesEntry(null, 0, "empty.png",null,null,null, null);

		//Don't need to be further down as don't rely on TableEntry's
		ResourceBundle woodmill_r = new Resource_ResourceBundle(Data.main.WOOD, empty);
		ResourceBundle quarry_r = new Resource_ResourceBundle(Data.main.STONE, empty);
		ResourceBundle cast_r = new Resource_ResourceBundle(Data.main.BRONZE, empty);
		ResourceBundle forge_r = new Resource_ResourceBundle(Data.main.IRON, empty);
		ResourceBundle mould_r = new Resource_ResourceBundle(Data.main.CLAY, empty);
		ResourceBundle oven_r = new Resource_ResourceBundle(Data.main.BRICK, empty);
		ResourceBundle kiln_r = new Resource_ResourceBundle(Data.main.CONCRETE, empty);
		ResourceBundle refine_r = new Resource_ResourceBundle(Data.main.STEEL, empty);
		ResourceBundle compress_r = new Resource_ResourceBundle(Data.main.GEMS, empty);

		ArrayList<RequiredResource> woodmill_l = new ArrayList<RequiredResource>();
		bundleResources(woodmill_l, 50, 50, 0, 20);

		ArrayList<RequiredResource> quarry_l = new ArrayList<RequiredResource>();
		bundleResources(quarry_l, 20, 50);

		ArrayList<RequiredResource> cast_l = new ArrayList<RequiredResource>();
		bundleResources(cast_l, 10, 100, 100, 5);

		ArrayList<RequiredResource> forge_l = new ArrayList<RequiredResource>();
		bundleResources(forge_l, 25, 150, 75, 100);

		ArrayList<RequiredResource> mould_l = new ArrayList<RequiredResource>();
		bundleResources(mould_l, 0, 200, 100, 0, 75, 25);

		ArrayList<RequiredResource> oven_l = new ArrayList<RequiredResource>();
		bundleResources(oven_l, 0, 500, 0, 150, 0, 125, 50);

		ArrayList<RequiredResource> kiln_l = new ArrayList<RequiredResource>();
		bundleResources(kiln_l, 0, 1000, 250, 0, 0, 100, 0, 50);

		ArrayList<RequiredResource> refine_l = new ArrayList<RequiredResource>();
		bundleResources(refine_l, 0, 0, 600, 1000, 0, 0, 100, 10);

		ArrayList<RequiredResource> compress_l = new ArrayList<RequiredResource>();
		bundleResources(compress_l, 0, 0, 1500, 0, 0, 0,250, 1000, 10);

		ArrayList<RequiredResource> clayNeeded = new ArrayList<RequiredResource>();
		clayNeeded.add(new RequiredResource(Data.main.CLAY, 1));

		ArrayList<RequiredResource> ironNeeded = new ArrayList<RequiredResource>();
		ironNeeded.add(new RequiredResource(Data.main.IRON, 1));

		ArrayList<RequiredResource> gemsNeeded = new ArrayList<RequiredResource>();
		gemsNeeded.add(new RequiredResource(Data.main.GEMS, 5));

		final TableEntry quarry = resourcesInfo.addEntry(new Multiplier("Quarry Stone", 1, "quarry.png", Data.getEraList().get(1), quarry_l, null, quarry_r));
		ResourceBundle stone = new Resource_ResourceBundle(Data.main.STONE, quarry);
		resourcesInfo.addEntry(new Absolute("Mine Stone", 1, Data.main.STONE, Data.getEraList().get(0), null, null, stone));

		final TableEntry woodmill = resourcesInfo.addEntry(new Multiplier("Woodmill", 1, "woodmill.png", Data.getEraList().get(2), woodmill_l, null, woodmill_r));
		ResourceBundle wood = new Resource_ResourceBundle(Data.main.WOOD, woodmill);
		resourcesInfo.addEntry(new Absolute("Gather Wood", 1, Data.main.WOOD, Data.getEraList().get(1), null, null, wood));

		final TableEntry cast = resourcesInfo.addEntry(new Multiplier("Cast Bronze", 1, "cast.png", Data.getEraList().get(2), cast_l, null, cast_r));
		ResourceBundle bronze = new Resource_ResourceBundle(Data.main.BRONZE, cast);
		resourcesInfo.addEntry(new Absolute("Smelt Bronze", 1, Data.main.BRONZE, Data.getEraList().get(1), null, null, bronze));
		
		final TableEntry forge = resourcesInfo.addEntry(new Multiplier("Forge Iron", 1, "forge.png", Data.getEraList().get(3), forge_l, null, forge_r));
		ResourceBundle iron = new Resource_ResourceBundle(Data.main.IRON, forge);
		resourcesInfo.addEntry(new Absolute("Mine Iron", 1, Data.main.IRON, Data.getEraList().get(2), null, null, iron));

		final TableEntry mould = resourcesInfo.addEntry(new Multiplier("Mould Clay", 1, "mould.png", Data.getEraList().get(4), mould_l, null, mould_r));
		ResourceBundle clay = new Resource_ResourceBundle(Data.main.CLAY, mould);
		resourcesInfo.addEntry(new Absolute("Collect Clay", 1, Data.main.CLAY, Data.getEraList().get(3), null, null, clay));

		final TableEntry oven = resourcesInfo.addEntry(new Multiplier("Cook Brick", 1, "oven.png", Data.getEraList().get(5), oven_l, null, oven_r));
		ResourceBundle brick = new Resource_ResourceBundle(Data.main.BRICK, oven);
		resourcesInfo.addEntry(new Absolute("Create Brick", 1, Data.main.BRICK, Data.getEraList().get(4), clayNeeded, null, brick));

		final TableEntry kiln = resourcesInfo.addEntry(new Multiplier("Fire Concrete", 1, "kiln.png", Data.getEraList().get(6), kiln_l, null, kiln_r));
		ResourceBundle concrete = new Resource_ResourceBundle(Data.main.CONCRETE, kiln);
		resourcesInfo.addEntry(new Absolute("Mix Concrete", 1, Data.main.CONCRETE, Data.getEraList().get(5), clayNeeded, null, concrete));

		final TableEntry refine = resourcesInfo.addEntry(new Multiplier("Refine Steel", 1, "refine.png", Data.getEraList().get(7), refine_l, null, refine_r));
		ResourceBundle steel = new Resource_ResourceBundle(Data.main.STEEL, refine);
		resourcesInfo.addEntry(new Absolute("Smelt Steel", 1, Data.main.STEEL, Data.getEraList().get(6), ironNeeded, null, steel));

		final TableEntry compress = resourcesInfo.addEntry(new Multiplier("Compress Gem",1, "compress.png", Data.getEraList().get(8), compress_l, null, compress_r));
		ResourceBundle gems = new Resource_ResourceBundle(Data.main.GEMS, compress);
		resourcesInfo.addEntry(new Gems("Mine Gems", 1, Data.main.GEMS, Data.getEraList().get(7), null, null, gems));

		ResourceBundle carbon = new Resource_ResourceBundle(Data.main.CARBON, empty);
		resourcesInfo.addEntry(new Absolute("Assemble Carbon", 1, Data.main.CARBON, Data.getEraList().get(8), null, null, carbon));
		ResourceBundle aCarbon = new Resource_ResourceBundle(Data.main.CARBON, empty);
		resourcesInfo.addEntry(new Absolute("Extract Diamonds", 25, Data.main.CARBON, Data.getEraList().get(8), gemsNeeded, null, aCarbon));

		new Absolute("Population", 1, Data.main.WOOD, Data.getEraList().get(0), null, null, null);

		return resourcesInfo;
	}
	private TableInfo createSpecialTable(){
		TableInfo specialInfo = new TableInfo(ResourceType.SPECIAL);

		ArrayList<RequiredResource> stone = new ArrayList<RequiredResource>();
		bundleResources(stone, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Stonehenge", 10, "stonehenge.png", Data.getEraList().get(0), null, stone, null, new Special_ResourceBundle(1));

		ArrayList<RequiredResource> pyram = new ArrayList<RequiredResource>();
		bundleResources(pyram, 0, 500, 50, 0, 0, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Pyramids", 25, "pyramid.png", Data.getEraList().get(1), null, pyram, null, new Special_ResourceBundle(2));

		ArrayList<RequiredResource> wall = new ArrayList<RequiredResource>();
		bundleResources(wall, 0, 1500, 100, 50, 0, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Great Wall", 50, "great_wall.png", Data.getEraList().get(2), null, wall, null, new Special_ResourceBundle(3));

		ArrayList<RequiredResource> colosseum = new ArrayList<RequiredResource>();
		bundleResources(colosseum, 0, 1500, 0, 500, 100, 0, 0, 0, 0, 0);
		specialInfo.addEntry("Colosseum", 100, "colosseum.png", Data.getEraList().get(3), null, colosseum, null, new Special_ResourceBundle(4));

		ArrayList<RequiredResource> pisa = new ArrayList<RequiredResource>();
		bundleResources(pisa, 0, 500, 0, 1500, 0, 250, 0, 0, 0, 0);
		specialInfo.addEntry("Leaning Tower", 150, "tower_of_pisa.png", Data.getEraList().get(4), null, pisa, null, new Special_ResourceBundle(5));

		ArrayList<RequiredResource> taj = new ArrayList<RequiredResource>();
		bundleResources(taj, 0, 4000, 4000, 0, 0, 0, 500, 0, 0, 0);
		specialInfo.addEntry("Taj Mahal", 250, "taj_mahal.png", Data.getEraList().get(5), null, taj, null, new Special_ResourceBundle(6));

		ArrayList<RequiredResource> eiffel = new ArrayList<RequiredResource>();
		bundleResources(eiffel, 0, 0, 1500, 7500, 0, 0, 0, 900, 0, 0);
		specialInfo.addEntry("Eiffel Tower", 400, "eiffel_tower.png", Data.getEraList().get(6), null, eiffel, null, new Special_ResourceBundle(7));

		ArrayList<RequiredResource> liberty = new ArrayList<RequiredResource>();
		bundleResources(liberty, 0, 0, 0, 0, 0, 0, 1000, 5000, 150, 0);
		specialInfo.addEntry("Statue of Liberty", 500, "statue_liberty.png", Data.getEraList().get(7), null, liberty, null, new Special_ResourceBundle(8));

		ArrayList<RequiredResource> space = new ArrayList<RequiredResource>();
		bundleResources(space, 0, 0, 0, 0, 0, 0, 0, 2500, 500, 1000);
		specialInfo.addEntry("Space Island", 750, "space_island.png", Data.getEraList().get(8), null, space, null, new Special_ResourceBundle(9));

		return specialInfo;
	}

	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone) {
		bundleResources(array, wood, stone, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze) {
		bundleResources(array, wood, stone, bronze, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze, int iron) {
		bundleResources(array, wood, stone, bronze, iron, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze, int iron, int clay) {
		bundleResources(array, wood, stone, bronze, iron, clay, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze, int iron, int clay, int brick) {
		bundleResources(array, wood, stone, bronze, iron, clay, brick, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze, int iron, int clay, int brick, int concrete) {
		bundleResources(array, wood, stone, bronze, iron, clay, brick, concrete, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze, int iron, int clay, int brick, int concrete, int steel) {
		bundleResources(array, wood, stone, bronze, iron, clay, brick, concrete, steel, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze, int iron, int clay, int brick, int concrete, int steel, int gems){
		bundleResources(array, wood, stone, bronze, iron, clay, brick, concrete, steel, gems, 0);
	}
	/**
	 * Bundles the resources specified into an ArrayList of required resources.
	 */ private void bundleResources(ArrayList<RequiredResource> array, int wood, int stone, int bronze, int iron, int clay, int brick, int concrete, int steel, int gems, int carbon){
		array.add(new RequiredResource(Data.main.WOOD, wood));
		array.add(new RequiredResource(Data.main.STONE, stone));
		array.add(new RequiredResource(Data.main.BRONZE, bronze));
		array.add(new RequiredResource(Data.main.IRON, iron));
		array.add(new RequiredResource(Data.main.CLAY, clay));
		array.add(new RequiredResource(Data.main.BRICK, brick));
		array.add(new RequiredResource(Data.main.CONCRETE, concrete));
		array.add(new RequiredResource(Data.main.STEEL, steel));
		array.add(new RequiredResource(Data.main.GEMS, gems));
		array.add(new RequiredResource(Data.main.CARBON, carbon));
	}

	/**
	 * For the planet UI stuff done when the planet is clicked
	 */
	boolean planetClicked(){
		Planet planet = Data.getCurrentPlanet();

		planet.setMultiplier(clickMultiplier * planet.getMultiplier());
		Data.ui.setPlanetLocation();

		planet.setClicked(true);
		planetClickedAction();
		return true;
	}
	/**
	 * For the actions done when the planet is clicked
	 */
	private void planetClickedAction(){
		TableEntry entry = Data.getSelectedEntry();
		if(entry==null) return; //Do nothing if none selected

		//Adds resources to whatever's clicked
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
				Data.ui.printInsufficientResources();
			}
		}
		//Upgrades the current entry to the next one specified
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
				Data.ui.printInsufficientResources();
			}
		}
	}
	/**
	 * Done when the planet is unclicked, returning it to normal
	 */
	void planetUnclicked(){
		Planet planet = Data.getCurrentPlanet();
		planet.setMultiplier(planet.getMultiplier() / clickMultiplier);
		planet.setClicked(false);
		Data.ui.updateResources();

		Data.ui.setPlanetLocation();
	}

	/**
	 * Checks if the user has enough resources to purchase the building they're trying to
	 * @param resources The list of resources to check if the user has
	 * @return True if sufficient resources
	 */
	private boolean hasResources(ArrayList<RequiredResource> resources){
		if(resources==null) return true;

		for(RequiredResource resource: resources){
			if(resource.getMaterial().getCount()<resource.getNumberRequired()) return false;
		}
		return true;
	}

	/**
	 * Takes away to given resources (as a result of purchasing something with them)
	 * @param resources The resources to remove
	 */
	public void subtractResources(ArrayList<RequiredResource> resources){
		if(resources==null) return;

		for(RequiredResource resource: resources){
			if(resource.getMaterial().equals(Data.main.POPULATION)) return;
			resource.getMaterial().addCount(-resource.getNumberRequired());
		}
	}

	/**
	 * Gives the player an amount of a specified resource
	 * @param material The resource to add to the number of
	 * @param amount The amount of that resource to add
	 */
	public void addResource(Resource material, long amount){
		material.addCount(amount);
	}

	/**
	 * Sets the entry to unclicked, removes the resource bar and set's the current entry to none
	 */
	void removeEntryAndUnclick(){
		if(Data.getSelectedEntry()!=null) {
			Data.getSelectedEntry().setCreateClicked(false);
			Data.getSelectedEntry().setUpgradeClicked(false);
		}
		Data.ui.loadSideBar(null, true);
		Data.setSelectedEntry(null);
	}

	//Listeners for actors being clicked
	public static class buildingListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.getCurrentTable()!=null) Data.getCurrentTable().unclickAll();

			Data.mechanics.removeEntryAndUnclick();

			Data.ui.setAllTablesInvisible();
			Data.ui.refreshTable();
			if(Data.getResourceType()==ResourceType.BUILDINGS){
				Data.setResourceType(ResourceType.NONE);
				Data.setBuildingTableVisible(false);
				Data.ui.refreshBuildingTable();
			} else {
				Data.setResourceType(ResourceType.BUILDINGS);
				Data.setBuildingTableVisible(true);
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

			if(Data.isTutorialRunning()){
				Data.mechanics.getCollection().objectiveComplete(2);
			}

			Data.mechanics.removeEntryAndUnclick();

			Data.ui.setAllTablesInvisible();
			Data.ui.refreshTable();
			if(Data.getResourceType()==ResourceType.FOOD){
				Data.setResourceType(ResourceType.NONE);
				Data.setFoodTableVisible(false);
				Data.ui.refreshFoodTable();
			} else {
				Data.setResourceType(ResourceType.FOOD);
				Data.setFoodTableVisible(true);
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

			if(Data.isTutorialRunning()){
				Data.mechanics.getCollection().objectiveComplete(7);
			}

			Data.ui.setAllTablesInvisible();
			Data.ui.refreshTable();
			if(Data.getResourceType()==ResourceType.RESOURCES){
				Data.setResourceType(ResourceType.NONE);
				Data.setResourcesTableVisible(false);
				Data.ui.refreshResourcesTable();
			} else {
				Data.setResourceType(ResourceType.RESOURCES);
				Data.setResourcesTableVisible(true);
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
				Data.setSpecialTableVisible(false);
				Data.ui.refreshBuildingTable();
			} else {
				Data.setResourceType(ResourceType.SPECIAL);
				Data.setSpecialTableVisible(true);
			}
			Data.ui.updateResources();
			Data.ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			return true;
		}
	}
	public static class planetListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			return Data.getCurrentPlanet().pointInsidePlanet(x + Data.getCurrentPlanet().getX(), y + Data.getCurrentPlanet().getY()) && Data.mechanics.planetClicked();
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
				Data.setPaused(false);
			}else {
				touchable = Touchable.disabled;
				Data.setPaused(true);
			}

			Data.ui.setEverythingTouchable(touchable);

			return true;
		}
	}
	public static class maximizeListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.mechanics.collection.getCurrentMessage().maximize();

			return true;
		}
	}
	public static class sunListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.main.getStage().dispose();
			Stage stage = new Stage();
			stage.addActor(Data.ui.getSpaceTeaserImage());
			Data.main.setStage(stage);
			Data.ui.getSpaceTeaserImage().setScaling(Scaling.fit);

			Gdx.input.setInputProcessor(stage);

			float heightRatio = (float)Data.ui.getSpaceTeaser().getHeight()/Gdx.graphics.getHeight();
			float widthRatio = (float)Data.ui.getSpaceTeaser().getWidth()/Gdx.graphics.getWidth();

			if(heightRatio>widthRatio){
				Data.ui.getSpaceTeaserImage().setHeight(Gdx.graphics.getHeight());
			}else{
				Data.ui.getSpaceTeaserImage().setWidth(Gdx.graphics.getWidth());
			}

			Data.ui.getSpaceTeaserImage().setX(Gdx.graphics.getWidth()/2-Data.ui.getSpaceTeaserImage().getWidth()/2);
			Data.ui.getSpaceTeaserImage().setY(Gdx.graphics.getHeight()/2-Data.ui.getSpaceTeaserImage().getHeight()/2);

			return true;
		}
	}

	void dispose(){

	}

	public SavegameFile getFile() {
		return file;
	}

	void tutorial(){
		Data.setTutorial(false);
		System.out.println("Starting Tutorial");
		test.show();
	}

	public void createTutorial(){
		collection = new TutorialCollection(page1, page2, page3, page4, page5, page6, page7, page8, page9, page10, page11, page12, page13);
		collection.setData();
	}

	public static class tutorialStart extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.setTutorialRunning(true);
			Data.mechanics.test.getInfoTable().setVisible(false);
			Data.mechanics.collection.getMessages().get(0).show();

			return true;
		}
	}

	public ConfirmBox getTest() {
		return test;
	}
	public void setTest(ConfirmBox test) {
		this.test = test;
	}
	public TutorialCollection getCollection() {
		return collection;
	}
	public Thread getSaveThread() {
		return saveThread;
	}
	public void setSaveThread(Thread saveThread) {
		this.saveThread = saveThread;
	}
}
