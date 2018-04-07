package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.Planet;
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
		int resourcesCount = Data.main.getResourcesCount();

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
		BuildingEntry town = (BuildingEntry) buildingInfo.addEntry("Town", 20000, Data.getEraList().get(2), null, null);
		BuildingEntry village = (BuildingEntry) buildingInfo.addEntry("Village", 100, Data.getEraList().get(1), town, null);
		buildingInfo.addEntry("House", 4, Data.getEraList().get(0), village,null);
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
		FoodEntry farm = (FoodEntry) foodInfo.addEntry("Farm", 5000, Data.getEraList().get(2), null, noLimit);
		FoodEntry smallfarm = (FoodEntry) foodInfo.addEntry("Small Farm", 20, Data.getEraList().get(1), farm, noLimit);
		foodInfo.addEntry("Hunt Food", 10, Data.getEraList().get(0), smallfarm, new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				if(key.equals("time")) return 3;
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
		});
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
		ResourcesEntry woodMill = (ResourcesEntry) resourcesInfo.addEntry("Woodmill", 100, Data.getEraList().get(1), null, wood);
		resourcesInfo.addEntry("Gather Wood", 1, Data.getEraList().get(0), woodMill, wood);
		ResourcesEntry bronzeCast = (ResourcesEntry) resourcesInfo.addEntry("Cast Bronze", 150, Data.getEraList().get(2), null, bronze);
		resourcesInfo.addEntry("Smelt Bronze", 5, Data.getEraList().get(1), bronzeCast, bronze);
		ResourcesEntry ironForge = (ResourcesEntry) resourcesInfo.addEntry("Forge Iron", 200, Data.getEraList().get(3), null, iron);
		resourcesInfo.addEntry("Mine Iron", 3, Data.getEraList().get(2), ironForge, iron);
		return resourcesInfo;
	}
	private TableInfo createSpecialTable(){
		TableInfo specialInfo = new TableInfo(ResourceType.SPECIAL);
		specialInfo.addEntry("Stonehenge", 50, Data.getEraList().get(0), null, null);
		specialInfo.addEntry("Pyramids", 100, Data.getEraList().get(1), null, null);
		specialInfo.addEntry("Great Wall", 250, Data.getEraList().get(2), null, null);
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

	public static class buildingListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
			if(Data.getResourceType()==ResourceType.BUILDINGS){
				Data.setResourceType(ResourceType.NONE);
				Data.main.setBuildingTableVisible(false);
				Data.ui.refreshBuildingTable();
			}
			Data.setResourceType(ResourceType.FOOD);
			Data.ui.updateResources();
			Data.ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			return true;
		}
	}
	public static class resourcesListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(Data.getResourceType()==ResourceType.BUILDINGS) {
				Data.setResourceType(ResourceType.NONE);
				Data.main.setBuildingTableVisible(false);
				Data.ui.refreshBuildingTable();
			}
			Data.setResourceType(ResourceType.RESOURCES);
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
