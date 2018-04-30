package com.blackphoton.planetclicker.objectType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.core.Data;

public class RequiredResource {
	Resource material;
	long numberRequired;
	Image resource;
	Label resourceNumber;

	public RequiredResource(Resource material, long numberRequired) {
		this.material = material;
		this.numberRequired = numberRequired;
		Texture texture = null;
		long numberOf = 0;

		texture = material.getTexture();
		numberOf = material.getCount();
		if(texture!=null){
			resource = new Image(texture);
			resource.setScaling(Scaling.fit);
			resource.setWidth(64);
			resource.setHeight(64);
		}
		resourceNumber = new Label(Long.toString(numberOf)+"/"+Long.toString(numberRequired), Data.ui.getSkin());
	}

	public Resource getMaterial() {
		return material;
	}

	public void setMaterial(Resource material) {
		this.material = material;
	}

	public long getNumberRequired() {
		return numberRequired;
	}

	public void setNumberRequired(long numberRequired) {
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
		numberOf = material.getCount();

		resourceNumber.setText(simplify(numberOf)+"/"+simplify(numberRequired));
		if(numberOf<numberRequired) resourceNumber.setColor(Color.RED);
		else resourceNumber.setColor(Color.GREEN);
	}

	private String simplify(long i){
		StringBuilder numberOfS = new StringBuilder("");
		if(i>=1000000000){
			numberOfS.append((double)Math.round((double) i/100000000d)/10d);
			numberOfS.append("b");
		}else
		if(i>=1000000){
			numberOfS.append((double)Math.round((double) i/100000d)/10d);
			numberOfS.append("m");
		}else
		if(i>=1000){
			numberOfS.append((double)Math.round((double) i/100d)/10d);
			numberOfS.append("k");
		}
		else numberOfS.append(i);
		return numberOfS.toString();
	}

	public void setResourceNumberText(String text) {
		resourceNumber.setText(text);
	}


}
