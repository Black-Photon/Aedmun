package com.blackphoton.planetclicker.objectType.table.entries.resources;

import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Absolute extends ResourcesEntry{
	public Absolute(String name, int value, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(name, value, requiredEra, resourcesNeeded, upgradeTo, resources);
		absolute.start();
	}

	public Thread absolute = new Thread() {
		@Override
		public synchronized void run() {
			while (!Thread.currentThread().isInterrupted()) {
				if(multiplierEntry!=null){
					setValue(initialValue*(multiplierEntry.getNumberOf()+1));
				}

				try {
					switch (material) {
						case WOOD:
							if (Data.main.getWoodCount() != numberOf)
								numberOf = Data.main.getWoodCount();
							break;
						case STONE:
							if (Data.main.getStoneCount() != numberOf)
								numberOf = Data.main.getStoneCount();
							break;
						case IRON:
							if (Data.main.getIronCount() != numberOf)
								numberOf = Data.main.getIronCount();
							break;
						case BRONZE:
							if (Data.main.getBronzeCount() != numberOf)
								numberOf = Data.main.getBronzeCount();
							break;
					}
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
		this.valueLabel.setText(Integer.toString(value));
	}
}
