package com.blackphoton.planetclicker.objectType.table.entries;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.resources.ResourceMaterial;
import com.blackphoton.planetclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.ResourceBundle;

public abstract class TableEntry {
	protected ResourceType type;
	protected String name;
	protected int value;
	protected int numberOf;
	protected ResourceBundle resources;
	protected Era requiredEra;
	final protected Texture create_tex = new Texture("create.png");
	final protected Texture upgrade_tex = new Texture("upgrade.png");
	final protected Texture createLocked_tex = new Texture("create_gray.png");
	final protected Texture upgradeLocked_tex = new Texture("upgrade_gray.png");
	final protected Texture createClicked_tex = new Texture("create_clicked.png");
	final protected Texture upgradeClicked_tex = new Texture("upgrade_clicked.png");
	protected Image create;
	protected Image upgrade;
	protected boolean canCreate;
	protected boolean canUpgrade;
	protected TableEntry upgradeTo;
	protected boolean createClicked = false;
	protected boolean upgradeClicked = false;
	protected boolean upgradable = true;
	protected ArrayList<RequiredResource> resourcesNeeded;
	protected ArrayList<RequiredResource> resourcesNeededToUpgrade;
	protected Label numberLabel;

	public TableEntry(ResourceType type, String name, int value, Era requiredEra, TableEntry upgradeTo, ArrayList<RequiredResource> resourcesNeeded, ArrayList<RequiredResource> resourcesNeededToUpgrade, ResourceBundle resources){
		this.type = type;
		numberOf = 0;
		this.resources = resources;
		this.upgradeTo = upgradeTo;
		this.requiredEra = requiredEra;
		this.name = name;
		this.value = value;
		this.resourcesNeeded = resourcesNeeded;
		this.resourcesNeededToUpgrade = resourcesNeededToUpgrade;

		create = new Image(create_tex);
		create.setScaling(Scaling.fit);
		create.addListener(new createListener());
		upgrade = new Image(upgrade_tex);
		upgrade.setScaling(Scaling.fit);
		upgrade.addListener(new upgradeListener());

		numberLabel = new Label(Integer.toString(numberOf), Data.ui.getSkin());

		updateButtons();
	}

	/**
	 * Adds one of this building - so you now have one more than before
	 */
	public void addToEntry(){
		numberOf++;
	}

	/**
	 * Subtracts one building - so you now have one less than before
	 */
	public void subFromEntry(){
		numberOf--;
	}

	public void updateButtons(){
		if(createClicked){
			create.setDrawable(new SpriteDrawable(new Sprite(createClicked_tex)));
		}else
		if(Data.main.getPopulationCount()>=requiredEra.getPop_req()){
			create.setDrawable(new SpriteDrawable(new Sprite(create_tex)));
			canCreate = true;
		}else{
			create.setDrawable(new SpriteDrawable(new Sprite(createLocked_tex)));
			canCreate = false;
		}

		if(upgradeClicked){
			upgrade.setDrawable(new SpriteDrawable(new Sprite(upgradeClicked_tex)));
		}else
		if(upgradeTo==null || !upgradable){
			upgrade.setDrawable(new SpriteDrawable(new Sprite(upgradeLocked_tex)));
			canUpgrade = false;
		}else
		if(Data.main.getPopulationCount()>=upgradeTo.requiredEra.getPop_req()){
			upgrade.setDrawable(new SpriteDrawable(new Sprite(upgrade_tex)));
			canUpgrade = true;
		}else{
			upgrade.setDrawable(new SpriteDrawable(new Sprite(upgradeLocked_tex)));
			canUpgrade = false;
		}
	}

	public void unclick(){
		createClicked = false;
		upgradeClicked = false;
	}

	public class createListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(!canCreate) return false;
			if(createClicked){
				unclick();
				updateButtons();
				Data.setSelectedEntry(null);
				Data.ui.loadSideBar(null, true);
				return true;
			}
			Data.getCurrentTable().unclickAll();
			createClicked = true;
			updateButtons();
			Data.setSelectedEntry(TableEntry.this);
			Data.ui.loadSideBar(TableEntry.this, true);
			return true;
		}
	}

	public class upgradeListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(!canUpgrade) return false;
			if(upgradeClicked){
				unclick();
				updateButtons();
				Data.setSelectedEntry(null);
				Data.ui.loadSideBar(null, false);
				return true;
			}
			Data.getCurrentTable().unclickAll();
			upgradeClicked = true;
			updateButtons();
			Data.setSelectedEntry(TableEntry.this);
			Data.ui.loadSideBar(TableEntry.this, false);
			return true;
		}
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getNumberOf() {
		return numberOf;
	}

	public Image getCreate() {
		return create;
	}

	public void setCreate(Image create) {
		this.create = create;
	}

	public Image getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(Image upgrade) {
		this.upgrade = upgrade;
	}

	public boolean isCanCreate() {
		return canCreate;
	}

	public void setCanCreate(boolean canCreate) {
		this.canCreate = canCreate;
	}

	public boolean isCanUpgrade() {
		return canUpgrade;
	}

	public void setCanUpgrade(boolean canUpgrade) {
		this.canUpgrade = canUpgrade;
	}

	public TableEntry getUpgradeTo() {
		return upgradeTo;
	}

	public void setUpgradeTo(TableEntry upgradeTo) {
		this.upgradeTo = upgradeTo;
	}

	public boolean isCreateClicked() {
		return createClicked;
	}

	public void setCreateClicked(boolean createClicked) {
		this.createClicked = createClicked;
	}

	public boolean isUpgradeClicked() {
		return upgradeClicked;
	}

	public void setUpgradeClicked(boolean upgradeClicked) {
		this.upgradeClicked = upgradeClicked;
	}

	public boolean isUpgradable() {
		return upgradable;
	}

	public void setUpgradable(boolean upgradable) {
		this.upgradable = upgradable;
	}

	public ArrayList<RequiredResource> getResourcesNeeded() {
		return resourcesNeeded;
	}

	public void setResourcesNeeded(ArrayList<RequiredResource> resourcesNeeded) {
		this.resourcesNeeded = resourcesNeeded;
	}

	public ArrayList<RequiredResource> getResourcesNeededToUpgrade() {
		return resourcesNeededToUpgrade;
	}

	public void setResourcesNeededToUpgrade(ArrayList<RequiredResource> resourcesNeededToUpgrade) {
		this.resourcesNeededToUpgrade = resourcesNeededToUpgrade;
	}

	public Label getNumberLabel() {
		return numberLabel;
	}

	public void setNumberLabelText() {
		this.numberLabel.setText(Integer.toString(numberOf));
	}

	public void setNumberLabelText(String text) {
		this.numberLabel.setText(text);
	}
}