package com.blackphoton.planetclicker.objectType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.core.Data;

/**
 * Holds info about a resource that may be needed to purchase something, as well as basic UI elements pertaining to it
 */
public class RequiredResource {
	private Resource material;
	private long numberRequired;
	private Image resource;
	private Label resourceNumber;

	/**
	 * @param material The type of resource being requested
	 * @param numberRequired The number of above resource being requested
	 */
	public RequiredResource(Resource material, long numberRequired) {
		this.material = material;
		this.numberRequired = numberRequired;
		Texture texture = material.getTexture();
		long numberOf = material.getCount();
		if(texture!=null){
			resource = new Image(texture);
			resource.setScaling(Scaling.fit);
			resource.setWidth(64);
			resource.setHeight(64);
		}
		resourceNumber = new Label(Long.toString(numberOf)+"/"+Long.toString(numberRequired), Data.ui.getSkin());
	}

	/**
	 * Converts a long into a simple string form:<br/>
	 * Eg. 10->"10", 1000->"1.0k", 100000->"100.0k", 1000000000->"1.0b"
	 * @param i Long to simplify
	 * @return the string under the above algorithm
	 */
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

	//Getters and Setters
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
	/**
	 * Set's the the text to the number label to values in the object, eg. numberOf = 10, numberRequired = 100 -> "10/100". Also set's color.
	 */ public void setResourceNumberText() {
		long numberOf = material.getCount();

		resourceNumber.setText(simplify(numberOf)+"/"+simplify(numberRequired));
		if(numberOf<numberRequired) resourceNumber.setColor(Color.RED);
		else resourceNumber.setColor(Color.GREEN);
	}
	public void setResourceNumberText(String text) {
		resourceNumber.setText(text);
	}
}
