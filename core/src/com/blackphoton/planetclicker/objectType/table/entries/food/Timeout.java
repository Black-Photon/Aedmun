package com.blackphoton.planetclicker.objectType.table.entries.food;

import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.entries.template.FoodEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;

/**
 * Decreases the number of by 1 every timelimit amount of time
 */
public class Timeout extends FoodEntry{
	/**
	 * @param name Name of resource
	 * @param value Value of 1 resource
	 * @param location Image location
	 * @param requiredEra Era needed to purchase
	 * @param upgradeTo Entry to turn into when upgraded
	 * @param resourcesNeeded Resources needed to buy
	 * @param resourcesNeededToUpgrade Resources needed to upgrade
	 * @param timeLimit The time waited between removing 1 of the food
	 */
	public Timeout(String name, int value, String location, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade, final int timeLimit) {
		super(name, value, location, requiredEra, upgradeTo, resourcesNeeded, resourcesNeededToUpgrade);
		new Thread(){
			@Override
			public void run(){
				while (!Thread.currentThread().isInterrupted()) {
					try {
						sleep(timeLimit);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(numberOf>0){
						subFromEntry();
					}
				}
			}
		}.start();
	}
}
