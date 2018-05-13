package com.blackphoton.planetclicker.objectType.table.entries.resources;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.Resource;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class for Gems - Gems have a 10% chance to be gotten when clicked
 */
public class Gems extends Absolute {
	public Gems(String name, int value, Resource resource, Era requiredEra, ArrayList<RequiredResource> resourcesNeeded, TableEntry upgradeTo, ResourceBundle resources) {
		super(name, value, resource, requiredEra, resourcesNeeded, upgradeTo, resources);
		probability.start();
	}

	boolean canCreate = true;
	/**
	 * Probability of getting a gem = 1/test, so 10 -> p = 1/10 = 10%
	 */
	final int test = 10;

	public Thread probability = new Thread() {
		@Override
		public synchronized void run() {
			Random random = new Random();
			while (!Thread.currentThread().isInterrupted()) {
				try{
					canCreate = random.nextInt(test) == 0;
					sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	};

	//Setters and Getters
	/**
	 * Updates the % of getting as the value
	 */ @Override public void setValueLabelText() {
		valueLabel.setText(Double.toString(100/(double)test)+"%");
	}
	@Override public void onClick() {
		if(canCreate) super.onClick();
	}
}
