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
		int buildingCount = Data.main.getBuildingCount();
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

		if (buildingCount * peoplePerBuilding > populationCount && foodCount > populationCount)
			for(int i=1;i<populationCount;i++) {
				int randomInt = random.nextInt(1000);
				if (randomInt == 42) {
					Data.main.setPopulationCount(populationCount+1);
				}
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
		BuildingEntry town = (BuildingEntry) buildingInfo.addEntry("Town", 20000, Data.getEraList().get(2), null, townArray, null);

		ArrayList villageArray = new ArrayList();
		villageArray.add(new RequiredResource(ResourceMaterial.WOOD, 50));
		villageArray.add(new RequiredResource(ResourceMaterial.BRONZE, 1));
		BuildingEntry village = (BuildingEntry) buildingInfo.addEntry("Village", 100, Data.getEraList().get(1), town, villageArray, null);

		ArrayList houseArray = new ArrayList();
		houseArray.add(new RequiredResource(ResourceMaterial.WOOD, 5));
		buildingInfo.addEntry("House", 4, Data.getEraList().get(0), village, houseArray, null);

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

		FoodEntry farm = (FoodEntry) foodInfo.addEntry("Farm", 5000, Data.getEraList().get(2), null, new ArrayList<RequiredResource>(), noLimit);

		ArrayList sfarmArray = new ArrayList();
		sfarmArray.add(new RequiredResource(ResourceMaterial.WOOD, 15));
		FoodEntry smallfarm = (FoodEntry) foodInfo.addEntry("Small Farm", 20, Data.getEraList().get(1), farm, sfarmArray, noLimit);

		ArrayList farmArray = new ArrayList();
		farmArray.add(new RequiredResource(ResourceMaterial.WOOD, 40));
		foodInfo.addEntry("Hunt Food", 10, Data.getEraList().get(0), smallfarm, farmArray, new ResourceBundle() {
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
		TableInfo resourcesInfo = new TableInfo(ResourceType.RESOURCES);
		ResourceBundle wood = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.WOOD;
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
		ResourceBundle stone = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.STONE;
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
		ResourceBundle bronze = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.BRONZE;
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
		ResourceBundle iron = new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("resource")) return ResourceMaterial.IRON;
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
		ResourcesEntry woodMill = (ResourcesEntry) resourcesInfo.addEntry("Woodmill", 100, Data.getEraList().get(1), null, null, wood);
		resourcesInfo.addEntry("Gather Wood", 1, Data.getEraList().get(0), woodMill, null, wood);
		ResourcesEntry quarryStone = (ResourcesEntry) resourcesInfo.addEntry("Quarry Stone", 50, Data.getEraList().get(1), null, null, stone);
		resourcesInfo.addEntry("Mine Stone", 1, Data.getEraList().get(0), quarryStone, null, stone);
		ResourcesEntry bronzeCast = (ResourcesEntry) resourcesInfo.addEntry("Cast Bronze", 150, Data.getEraList().get(2), null, null, bronze);
		resourcesInfo.addEntry("Smelt Bronze", 5, Data.getEraList().get(1), bronzeCast, null, bronze);
		ResourcesEntry ironForge = (ResourcesEntry) resourcesInfo.addEntry("Forge Iron", 200, Data.getEraList().get(3), null, null, iron);
		resourcesInfo.addEntry("Mine Iron", 3, Data.getEraList().get(2), ironForge, null, iron);
		return resourcesInfo;
	}
	private TableInfo createSpecialTable(){
		TableInfo specialInfo = new TableInfo(ResourceType.SPECIAL);

		ArrayList<RequiredResource> stone = new ArrayList<RequiredResource>();
		stone.add(new RequiredResource(ResourceMaterial.STONE, 150));
		specialInfo.addEntry("Stonehenge", 50, Data.getEraList().get(0), null, stone, null);

		ArrayList<RequiredResource> pyram = new ArrayList<RequiredResource>();
		pyram.add(new RequiredResource(ResourceMaterial.STONE, 350));
		pyram.add(new RequiredResource(ResourceMaterial.BRONZE, 50));
		specialInfo.addEntry("Pyramids", 100, Data.getEraList().get(1), null, pyram, null);

		ArrayList<RequiredResource> wall = new ArrayList<RequiredResource>();
		wall.add(new RequiredResource(ResourceMaterial.STONE, 600));
		wall.add(new RequiredResource(ResourceMaterial.BRONZE, 20));
		wall.add(new RequiredResource(ResourceMaterial.IRON, 200));
		specialInfo.addEntry("Great Wall", 250, Data.getEraList().get(2), null, wall, null);

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
		Data.ui.updateResources();
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

		if(entry.isCreateClicked()) entry.addToEntry();
		if(entry.isUpgradeClicked()){
			if(entry.getNumberOf()>0) {
				entry.subFromEntry();
				entry.getUpgradeTo().addToEntry();
			}
		}
		Data.ui.refreshTable();

		/*int resourcesCount = Data.main.getResourcesCount();
		final Label insufficientResources = Data.ui.getInsufficientResources();

		switch (Data.getResourceType()) {
			case BUILDINGS:
				if(resourcesCount>=resourcesPerBuilding) {
					Data.main.setResourcesCount(resourcesCount-resourcesPerBuilding);
					Data.main.setBuildingCount(Data.main.getBuildingCount()+1);
				}else{
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
						}
					}.start();
				}
				break;
			case FOOD:
				Data.main.setFoodCount(Data.main.getFoodCount()+1);
				break;
			case RESOURCES:
				Data.main.setResourcesCount(resourcesCount+1);
				break;
		}*/
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

		ArrayList<TableEntry> resources = Data.getResourcesTable().getEntries();
		Data.main.setWoodCount(0);
		Data.main.setBronzeCount(0);
		Data.main.setIronCount(0);
		for(TableEntry entry: resources){
			if(((ResourcesEntry)entry).getMaterial()==ResourceMaterial.WOOD && Data.main.getWoodCount()==0){
				Data.main.setWoodCount(Data.main.getWoodCount()+entry.getNumberOf());
			}
			if(((ResourcesEntry)entry).getMaterial()==ResourceMaterial.BRONZE && Data.main.getBronzeCount()==0){
				Data.main.setBronzeCount(Data.main.getBronzeCount()+entry.getNumberOf());
			}
			if(((ResourcesEntry)entry).getMaterial()==ResourceMaterial.IRON && Data.main.getIronCount()==0){
				Data.main.setIronCount(Data.main.getIronCount()+entry.getNumberOf());
			}
		}
	}

	public static class buildingListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.getCurrentTable()!=null) Data.getCurrentTable().unclickAll();
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
