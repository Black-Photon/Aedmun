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

public class SpecialEntry extends TableEntry {

	private boolean canBuild = false;

	private boolean complete;

	public SpecialEntry(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ResourceBundle resources) {
		super(ResourceType.SPECIAL, name, value, location, requiredEra, upgradeTo,resourcesNeeded, null, resources);

		int pop_req = (Integer) resources.getObject(resources.getKeys().nextElement());

		this.resourcesNeeded.add(new RequiredResource(Data.main.POPULATION, pop_req));
		setNumberLabelText();
	}

	@Override
	public void onClick(){
		if (isCanBuild()){
			if(getPercent()==100) return;

			addToEntry();
			setNumberLabelText();

			if(getPercent()==100){
				if(!isComplete()){
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
			setCanBuild(true);
			Data.mechanics.subtractResources(getResourcesNeeded());
			setResourcesNeeded(null);
			Data.ui.loadSideBar(Data.getSelectedEntry(), true);

			addToEntry();
		}
	}

	public int getPercent(){
		return Math.round(((float) numberOf)/ ((float) value)*100);
	}

	@Override
	public Label getNumberLabel() {
		return numberLabel;
	}

	@Override
	public void setNumberLabelText() {
		this.numberLabel.setText(Integer.toString(getPercent())+"%");
		if(getPercent()==100) numberLabel.setColor(Color.GREEN);
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

	@Override
	public void setNumberLabelText(String text) {
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
