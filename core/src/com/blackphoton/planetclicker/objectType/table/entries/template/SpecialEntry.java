package com.blackphoton.planetclicker.objectType.table.entries.template;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Table Entry for Special buildings
 */
public class SpecialEntry extends TableEntry {

	/**
	 * Can you build the structure. This is true when you have spend the initial resources.
	 */
	private boolean canBuild = false;
	/**
	 * Has the special been completed
	 */
	private boolean complete;

	/**
	 * @param name Name of resource
	 * @param value Value of 1 resource
	 * @param location Image location
	 * @param requiredEra Era needed to purchase
	 * @param upgradeTo Entry to turn into when upgraded
	 * @param resourcesNeeded Resources needed to buy
	 * @param resources Resource Bundle containing the population requirement
	 */
	public SpecialEntry(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ResourceBundle resources) {
		super(ResourceType.SPECIAL, name, value, location, requiredEra, upgradeTo,resourcesNeeded, null);

		int pop_req = (Integer) resources.getObject(resources.getKeys().nextElement());

		this.resourcesNeeded.add(new RequiredResource(Data.main.POPULATION, pop_req));
		setNumberLabelText();
	}

	/**
	 * Adds to entry, and depending on state of the entry, removes resources, moves towards 100% or set's complete, setting the era.
	 */
	@Override
	public void onClick(){
		if (isCanBuild()){
			//Do nothing at 100%
			if(getPercent()==100) return;

			addToEntry();
			setNumberLabelText();

			if(getPercent()==100){
				if(!isComplete()){
					//Set complete and update era

					setComplete(true);
					boolean found = false;
					for(Era era: Data.getEraList()){
						if(found){
							Data.setCurrentEra(era);
							Data.setCurrentEra(Data.getCurrentEra());
							Data.ui.updateEra();
							break;
						}
						if(era.equals(Data.getCurrentEra())) found = true;
					}
				}
				return;
			}

			return;
		}else{
			//Build, take resources, make it free and reload sidebar.
			setCanBuild(true);
			Data.mechanics.subtractResources(getResourcesNeeded());
			setResourcesNeeded(null);
			Data.ui.loadSideBar(Data.getSelectedEntry(), true);

			addToEntry(); //Creates an initial one to make saving the game a LOT easier
		}
	}

	/**
	 * Get's the percentage complete
	 * @return Percentage complete
	 */
	private int getPercent(){
		return Math.round(((float) numberOf)/ ((float) value)*100);
	}

	@Override
	public void updateButtons(){
		if(name==null) return;

		if(createClicked){
			create.setDrawable(new SpriteDrawable(new Sprite(createClicked_tex)));
		}else
		if(Data.getCurrentEra().equals(requiredEra)){
			create.setDrawable(new SpriteDrawable(new Sprite(create_tex)));
			canCreate = true;
		}else{
			create.setDrawable(new SpriteDrawable(new Sprite(Data.ui.getRequired_tex())));
			upgrade.setDrawable(new SpriteDrawable(new Sprite(requiredEra.getTexture())));
			canCreate = false;
			canUpgrade = false;
			return;
		}

		upgrade.setDrawable(new SpriteDrawable(new Sprite(upgradeLocked_tex)));
		canUpgrade = false;
	}

	//Setters and Getters
	@Override public Label getNumberLabel() {
		return numberLabel;
	}
	/**
	 * Updates the Number Label text automagically, so it shows the current %
	 */ @Override public void setNumberLabelText() {
		this.numberLabel.setText(Integer.toString(getPercent()) + "%");
		if (getPercent() == 100) numberLabel.setColor(Color.GREEN);
	}
	@Override public void setNumberLabelText(String text) {
		this.numberLabel.setText(text);
	}
	public boolean isCanBuild() {
		return canBuild;
	}
	public void setCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
