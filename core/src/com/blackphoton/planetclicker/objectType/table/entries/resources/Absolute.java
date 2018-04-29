package com.blackphoton.planetclicker.objectType.table.entries.resources;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.objectType.table.entries.template.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Absolute extends ResourcesEntry{
	public Absolute(String name, int value, Resource resource, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(name, value, "empty.png", requiredEra, resourcesNeeded, upgradeTo, resources);
		absolute.start();
		setTexture(resource.getTexture());
		setImage(new Image(texture));
		image.setScaling(Scaling.fit);
		material = resource;
	}

	public Thread absolute = new Thread() {
		@Override
		public synchronized void run() {
			while (!Thread.currentThread().isInterrupted()) {
				if(multiplierEntry!=null){
					setValue(initialValue*(multiplierEntry.getNumberOf()+1));
				}

				try {
					if(material.getCount() != numberOf)
						numberOf = material.getCount();
					sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	};

	@Override
	public void onClick() {
		super.onClick();
		Data.mechanics.addResource(getMaterial(), getValue());
	}

	@Override
	public void setValueLabelText() {
		this.valueLabel.setText(Long.toString(value));
	}
}
