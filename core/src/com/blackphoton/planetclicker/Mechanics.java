package com.blackphoton.planetclicker;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

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
		int populationCount = Data.getPlanetClicker().getPopulationCount();
		int buildingCount = Data.getPlanetClicker().getBuildingCount();
		int foodCount = Data.getPlanetClicker().getFoodCount();
		int resourcesCount = Data.getPlanetClicker().getResourcesCount();

		boolean found = false;
		for(Era era: Data.getEraList()){
			if(found){
				if(populationCount>=era.getPop_req().toInt()){
					thisEra = era;
					Data.getUi().updateEra(thisEra);
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
					Data.getPlanetClicker().setPopulationCount(populationCount+1);
				}
			}
	}

	public boolean planetClicked(){
		Planet planet = Data.getPlanetClicker().getPlanet();
		int resourcesCount = Data.getPlanetClicker().getResourcesCount();
		final Label insufficientResources = Data.getUi().getInsufficientResources();

		planet.setMultiplier(clickMultiplier * planet.getMultiplier());
		planet.setClicked(true);
		Data.getUi().updateResources();
		switch (Data.getResourceType()) {
			case BUILDINGS:
				if(resourcesCount>=resourcesPerBuilding) {
					Data.getPlanetClicker().setResourcesCount(resourcesCount-resourcesPerBuilding);
					Data.getPlanetClicker().setBuildingCount(Data.getPlanetClicker().getBuildingCount()+1);
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
				Data.getPlanetClicker().setFoodCount(Data.getPlanetClicker().getFoodCount()+1);
				break;
			case RESOURCES:
				Data.getPlanetClicker().setResourcesCount(resourcesCount+1);
				break;
		}
		return true;
	}
	public boolean planetUnclicked(){
		Planet planet = Data.getPlanetClicker().getPlanet();
		planet.setMultiplier(planet.getMultiplier() / clickMultiplier);
		planet.setClicked(false);
		Data.getUi().updateResources();
		return true;
	}

	public static class buildingListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.setResourceType(ResourceType.BUILDINGS);
			Data.getUi().updateResources();
			return true;
		}
	}
	public static class foodListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.setResourceType(ResourceType.FOOD);
			Data.getUi().updateResources();
			return true;
		}
	}
	public static class resourcesListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.setResourceType(ResourceType.RESOURCES);
			Data.getUi().updateResources();
			return true;
		}
	}

	public void dispose(){

	}
}
