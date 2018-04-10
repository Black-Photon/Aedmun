package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.BuildingEntry;
import com.blackphoton.planetclicker.objectType.table.entries.FoodEntry;
import com.blackphoton.planetclicker.objectType.table.entries.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.TableEntry;
import com.blackphoton.planetclicker.resources.ResourceType;
import com.blackphoton.planetclicker.resources.ResourceMaterial;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.ResourceBundle;

public class Mechanics {

	private Era thisEra;
	private final int peoplePerBuilding = 4;
	private final int resourcesPerBuilding = 1;
	private Random random;
	private final float clickMultiplier = 0.95f;
	private int numberOfIRThreads = 0;

	public void create(){
		thisEra = Data.getEraList().get(0);
		random = new Random();
	}
	public void update(){
		int populationCount = Data.main.getPopulationCount();
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

		boolean found = false;
		for(Era era: Data.getEraList()){
			if(found){
				if(populationCount>=era.getPop_req().toInt()){
					thisEra = era;
					Data.ui.updateEra(thisEra);
				}
			}
			if(era.equals(thisEra)){
				found = true;
			}
		}

		if (buildingCount > populationCount && foodCount > populationCount)
			for(int i=1;i<populationCount;i++) {
				int randomInt = random.nextInt(1000);
				if (randomInt == 42) {
					Data.main.setPopulationCount(populationCount+1);
				}
			}
		if (buildingCount < populationCount || foodCount < populationCount)
			for(int i=1;i<populationCount;i++) {
				int randomInt = random.nextInt(1000);
				if (randomInt == 888) {
					Data.main.setPopulationCount(populationCount-1);
				}
			}
		if(populationCount<2) populationCount=2;

		System.out.printf("Buildings: %s, Food: %s\n", buildingCount, foodCount);


		for(TableEntry entry: Data.getResourcesTable().getEntries()){
			entry.setNumberLabelText();
		}
	}

	private ArrayList keyList;
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
		townArray.add(new RequiredResource(ResourceMaterial.BRONZE, 5));
		townArray.add(new RequiredResource(ResourceMaterial.IRON, 1));
		BuildingEntry town = (BuildingEntry) buildingInfo.addEntry("Town", 20000, Data.getEraList().get(2), null, townArray, null, null);

		ArrayList villageArray = new ArrayList();
		villageArray.add(new RequiredResource(ResourceMaterial.WOOD, 50));
		villageArray.add(new RequiredResource(ResourceMaterial.BRONZE, 1));
		ArrayList toTownArray = new ArrayList();
		toTownArray.add(new RequiredResource(ResourceMaterial.WOOD, 25));
		toTownArray.add(new RequiredResource(ResourceMaterial.BRONZE, 3));
		toTownArray.add(new RequiredResource(ResourceMaterial.IRON, 1));
		BuildingEntry village = (BuildingEntry) buildingInfo.addEntry("Village", 100, Data.getEraList().get(1), town, villageArray, toTownArray, null);

		ArrayList houseArray = new ArrayList();
		ArrayList toVillageArray = new ArrayList();
		toVillageArray.add(new RequiredResource(ResourceMaterial.WOOD, 25));
		toVillageArray.add(new RequiredResource(ResourceMaterial.BRONZE, 1));
		houseArray.add(new RequiredResource(ResourceMaterial.WOOD, 5));
		buildingInfo.addEntry("House", 4, Data.getEraList().get(0), village, houseArray, toVillageArray, null);

		return buildingInfo;
	}
	private TableInfo createFoodTable(){
		keyList = new ArrayList();
		keyList.add("time");
		ResourceBundle noLimit = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("time")) return -1;
				return null;
			}

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;
					int count = 0;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};
		TableInfo foodInfo = new TableInfo(ResourceType.FOOD);

		ArrayList farmArray = new ArrayList();
		farmArray.add(new RequiredResource(ResourceMaterial.WOOD, 40));
		ArrayList toFarmArray = new ArrayList();
		toFarmArray.add(new RequiredResource(ResourceMaterial.WOOD, 15));
		FoodEntry farm = (FoodEntry) foodInfo.addEntry("Farm", 5000, Data.getEraList().get(2), null, farmArray, toFarmArray, noLimit);

		ArrayList sfarmArray = new ArrayList();
		sfarmArray.add(new RequiredResource(ResourceMaterial.WOOD, 15));
		ArrayList toSFarmArray = new ArrayList();
		toSFarmArray.add(new RequiredResource(ResourceMaterial.WOOD, 10));
		FoodEntry smallfarm = (FoodEntry) foodInfo.addEntry("Small Farm", 20, Data.getEraList().get(1), farm, sfarmArray, toSFarmArray, noLimit);


		foodInfo.addEntry("Hunt Food", 10, Data.getEraList().get(0), smallfarm, null, null, new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("time")) return 3000;
				return null;
			}

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;
					int count = 0;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		}).setUpgradable(false);

		return foodInfo;
	}
	private TableInfo createResourcesTable(){
		keyList = new ArrayList();
		keyList.add("resource");
		keyList.add("absolute");
		keyList.add("time");
		TableInfo resourcesInfo = new TableInfo(ResourceType.RESOURCES);
		ResourceBundle wood = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.WOOD;
				if(key.equals("absolute")) return true;
				if(key.equals("time")) return 0f;
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;
					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};
		ResourceBundle stone = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.STONE;
				if(key.equals("absolute")) return true;
				if(key.equals("time")) return 0f;
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};
		ResourceBundle bronze = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.BRONZE;
				if(key.equals("absolute")) return true;
				if(key.equals("time")) return 0f;
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};
		ResourceBundle iron = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.IRON;
				if(key.equals("absolute")) return true;
				if(key.equals("time")) return 0f;
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};

		//Not absolute - as in they increase resources over time
		ResourceBundle woodMill_r = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.WOOD;
				if(key.equals("absolute")) return false;
				if(key.equals("time")) return 15f; //In seconds
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};
		ResourceBundle quarryStone_r = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.STONE;
				if(key.equals("absolute")) return false;
				if(key.equals("time")) return 10f; //In seconds
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};
		ResourceBundle bronzeCast_r = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.BRONZE;
				if(key.equals("absolute")) return false;
				if(key.equals("time")) return 40f; //In seconds
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};
		ResourceBundle ironForge_r = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.IRON;
				if(key.equals("absolute")) return false;
				if(key.equals("time")) return 50f; //In seconds
				return null;
			}

			int count = 0;

			@Override
			public Enumeration<String> getKeys() {
				Enumeration<String> e = new Enumeration<String>() {
					ArrayList<String> elements = keyList;

					@Override
					public boolean hasMoreElements() {
						return count<elements.size();
					}

					@Override
					public String nextElement() {
						count++;
						return elements.get(count-1);
					}
				};
				return e;
			}
		};

		ArrayList woodMill_l = new ArrayList();
		woodMill_l.add(new RequiredResource(ResourceMaterial.WOOD, 100));
		woodMill_l.add(new RequiredResource(ResourceMaterial.STONE, 10));


		resourcesInfo.addEntry("Woodmill", 1, Data.getEraList().get(1), null, woodMill_l, null, woodMill_r);

		resourcesInfo.addEntry("Gather Wood", 1, Data.getEraList().get(0), null, null, null, wood);

		resourcesInfo.addEntry("Quarry Stone", 1, Data.getEraList().get(1), null, null, null, quarryStone_r);

		resourcesInfo.addEntry("Mine Stone", 1, Data.getEraList().get(0), null, null, null, stone);

		resourcesInfo.addEntry("Cast Bronze", 1, Data.getEraList().get(2), null, null, null, bronzeCast_r);

		resourcesInfo.addEntry("Smelt Bronze", 5, Data.getEraList().get(1), null, null, null, bronze);

		resourcesInfo.addEntry("Forge Iron", 1, Data.getEraList().get(3), null, null, null, ironForge_r);

		resourcesInfo.addEntry("Mine Iron", 3, Data.getEraList().get(2), null, null, null, iron);
		return resourcesInfo;
	}
	private TableInfo createSpecialTable(){
		TableInfo specialInfo = new TableInfo(ResourceType.SPECIAL);

		ArrayList<RequiredResource> stone = new ArrayList<RequiredResource>();
		stone.add(new RequiredResource(ResourceMaterial.STONE, 150));
		specialInfo.addEntry("Stonehenge", 50, Data.getEraList().get(0), null, stone, null, null);

		ArrayList<RequiredResource> pyram = new ArrayList<RequiredResource>();
		pyram.add(new RequiredResource(ResourceMaterial.STONE, 350));
		pyram.add(new RequiredResource(ResourceMaterial.BRONZE, 50));
		specialInfo.addEntry("Pyramids", 100, Data.getEraList().get(1), null, pyram, null, null);

		ArrayList<RequiredResource> wall = new ArrayList<RequiredResource>();
		wall.add(new RequiredResource(ResourceMaterial.STONE, 600));
		wall.add(new RequiredResource(ResourceMaterial.BRONZE, 20));
		wall.add(new RequiredResource(ResourceMaterial.IRON, 200));
		specialInfo.addEntry("Great Wall", 250, Data.getEraList().get(2), null, wall, null, null);

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
				entry.addToEntry();
				subtractResources(entry.getResourcesNeeded());
			}else{
				printInsufficientResources();
			}

			if(entry instanceof ResourcesEntry) {
				if(((ResourcesEntry) entry).isAbsolute()){
					addResource(((ResourcesEntry) entry).getMaterial(), entry.getValue());
				}
			}
		}
		if(entry.isUpgradeClicked()){
			if(hasResources(entry.getResourcesNeededToUpgrade())) {
				if (entry.getNumberOf() > 0) {
					entry.subFromEntry();
					entry.getUpgradeTo().addToEntry();
					subtractResources(entry.getResourcesNeededToUpgrade());
				}
			}else{
				printInsufficientResources();
			}
		}
		Data.ui.refreshTable();
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
		final Label insufficientResources = Data.ui.getInsufficientResources();
		insufficientResources.setColor(0.8f,0.8f,0.8f,1);

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
					insufficientResources.setColor(0.8f,0.8f,0.8f,alpha-0.001f);
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
			if(Data.getResourceType()==ResourceType.BUILDINGS){
				Data.setResourceType(ResourceType.NONE);
				Data.main.setBuildingTableVisible(false);
				Data.ui.refreshBuildingTable();
			}

			Data.mechanics.removeEntryAndUnclick();

			Data.setResourceType(ResourceType.SPECIAL);
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

	public void dispose(){

	}
}
