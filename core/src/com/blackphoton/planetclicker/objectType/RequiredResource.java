package com.blackphoton.planetclicker.objectType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.resources.ResourceMaterial;

public class RequiredResource {
	ResourceMaterial material;
	int numberRequired;
	Image resource;
	Label resourceNumber;

	public RequiredResource(ResourceMaterial material, int numberRequired) {
		this.material = material;
		this.numberRequired = numberRequired;
		Texture texture = null;
		long numberOf = 0;
		switch(material){
			case WOOD:
				texture = new Texture("wood.png");
				numberOf = Data.main.getWoodCount();
				break;
			case STONE:
				texture = new Texture("stone.png");
				numberOf = Data.main.getStoneCount();
				break;
			case BRONZE:
				texture = new Texture("bronze_bar.png");
				numberOf = Data.main.getBronzeCount();
				break;
			case IRON:
				texture = new Texture("iron_bar.png");
				numberOf = Data.main.getIronCount();
				break;
			case POPULATION:
				texture = new Texture("population.png");
				numberOf = Data.main.getPopulationCount();
				break;
		}
		if(texture!=null){
			resource = new Image(texture);
			resource.setWidth(64);
			resource.setHeight(64);
		}
		resourceNumber = new Label(Long.toString(numberOf)+"/"+Integer.toString(numberRequired), Data.ui.getSkin());
	}

	public ResourceMaterial getMaterial() {
		return material;
	}

	public void setMaterial(ResourceMaterial material) {
		this.material = material;
	}

	public int getNumberRequired() {
		return numberRequired;
	}

	public void setNumberRequired(int numberRequired) {
		this.numberRequired = numberRequired;
	}

	public Image getResource() {
		return resource;
	}

	public void setResource(Image resource) {
		this.resource = resource;
	}

	public Label getResourceNumber() {
		return resourceNumber;
	}

	public void setResourceNumberText() {
		long numberOf = 0;
		switch(material){
			case WOOD:
				numberOf = Data.main.getWoodCount();
				break;
			case STONE:
				numberOf = Data.main.getStoneCount();
				break;
			case BRONZE:
				numberOf = Data.main.getBronzeCount();
				break;
			case IRON:
				numberOf = Data.main.getIronCount();
				break;
			case POPULATION:
				numberOf = Data.main.getPopulationCount();
				break;
		}

		resourceNumber.setText(simplify(numberOf)+"/"+simplify(numberRequired));
		if(numberOf<numberRequired) resourceNumber.setColor(Color.RED);
		else resourceNumber.setColor(Color.GREEN);
	}

	private String simplify(long i){
		StringBuilder numberOfS = new StringBuilder("");
		if(i>=1000000000){
			numberOfS.append(Math.round((double) i/100000000d)/10);
			numberOfS.append("b");
		}else
		if(i>=1000000){
			numberOfS.append(Math.round((double) i/100000d)/10);
			numberOfS.append("m");
		}else
		if(i>=1000){
			numberOfS.append(Math.round((double) i/100d)/10);
			numberOfS.append("k");
		}
		else numberOfS.append(i);
		return numberOfS.toString();
	}

	public void setResourceNumberText(String text) {
		resourceNumber.setText(text);
	}
}
