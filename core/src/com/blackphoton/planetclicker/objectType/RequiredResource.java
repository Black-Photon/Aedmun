package com.blackphoton.planetclicker.objectType;

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
		resourceNumber = new Label(Integer.toString(numberRequired), Data.ui.getSkin());
		Texture texture = null;
		switch(material){
			case WOOD:
				texture = new Texture("wood.png");
				break;
			case STONE:
				texture = new Texture("stone.png");
				break;
			case BRONZE:
				texture = new Texture("bronze_bar.png");
				break;
			case IRON:
				texture = new Texture("iron_bar.png");
				break;
		}
		if(texture!=null){
			resource = new Image(texture);
			resource.setWidth(64);
			resource.setHeight(64);
		}

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
		resourceNumber.setText(Integer.toString(numberRequired));
	}

	public void setResourceNumberText(String text) {
		resourceNumber.setText(text);
	}
}
