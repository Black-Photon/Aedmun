package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.messages.Settings;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

import java.util.ArrayList;

public class UI {

	//Bottom Bar
	private Texture buildings_tex;
	private Texture food_tex;
	private Texture resources_tex;
	private Texture special_tex;
	private Texture clicked;
	private Texture unclicked;
	private Image buildings_background;
	private Image food_background;
	private Image resources_background;
	private Image special_background;
	private Image line;
	private Image buildings;
	private Image food;
	private Image resources;
	private Image special;
	private Group resourceGroup;

	//Population
	private Label populationLabel;
	private Group populationGroup;
	private Image pop_bar_left;
	private Image pop_bar;
	private Image pop_bar_right;

	//Side Bar
	private Group reqResGroup;
	private Image reqRes_top;
	private Image reqRes;
	private Image reqRes_bottom;

	//Options
	// - General
	private Texture tableBackground_tex = new Texture("table.png");
	private Texture tableTopBackground_tex = new Texture("table_top.png");
	private Texture tableBottomBackground_tex = new Texture("table_bottom.png");
	private Texture create_tex;
	private Texture create_locked;
	private Texture upgrade_tex;
	private Texture upgrade_locked;

	// - Building
	private ScrollPane buildingScroll;
	private ScrollPane buildingTable;

	// - Food
	private ScrollPane foodScroll;
	private ScrollPane foodTable;

	// - Resources
	private ScrollPane resourceScroll;
	private ScrollPane resourcesTable;


	// - Special
	private ScrollPane specialTable;
	private ScrollPane specialScroll;

	//Other
	private Image settingsImage;
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	private Image era;
	private Texture space;
	private Image sun;
	private Label countLabel;
	private final Image insufficientResources = new Image(new Texture("insufficient_resources.png"));
	private GlyphLayout glyphLayout;
	private BitmapFont bitmapFont;
	private float heightScale;
	private InputMultiplexer multiplexer;
	public float planetY;
	protected Texture required_tex = new Texture("requires.png");

	private Settings settings;

	public void createUI(){
		//General Declarations
		glyphLayout = new GlyphLayout();
		bitmapFont = new BitmapFont();

		//Bottom Bar
		buildings_tex = new Texture("building.png");
		food_tex = new Texture("food.png");
		resources_tex = new Texture("resources.png");
		special_tex = new Texture("special.png");
		clicked = new Texture("clicked.png");
		unclicked = new Texture("unclicked.png");

		buildings = new Image(buildings_tex);
		food = new Image(food_tex);
		resources = new Image(resources_tex);
		special = new Image(special_tex );
		line = new Image(new Texture("horizontal_line.png"));

		buildings_background = new Image(unclicked);
		food_background = new Image(unclicked);
		resources_background = new Image(unclicked);
		special_background = new Image(unclicked);

		resourceGroup = new Group();
		resourceGroup.addActor(line);
		resourceGroup.addActor(buildings_background);
		resourceGroup.addActor(food_background);
		resourceGroup.addActor(resources_background);
		resourceGroup.addActor(special_background);
		resourceGroup.addActor(buildings);
		resourceGroup.addActor(food);
		resourceGroup.addActor(resources);
		resourceGroup.addActor(special);
		resourceGroup.setX(0);
		resourceGroup.setY(0);

		buildings_background.addListener(new Mechanics.buildingListener());
		food_background.addListener(new Mechanics.foodListener());
		resources_background.addListener(new Mechanics.resourcesListener());
		special_background.addListener(new Mechanics.specialListener());
		buildings.setTouchable(Touchable.disabled);
		food.setTouchable(Touchable.disabled);
		resources.setTouchable(Touchable.disabled);
		special.setTouchable(Touchable.disabled);

		//Side Bar
		reqRes_bottom = new Image(new Texture("side_bar_bottom.png"));
		reqRes_bottom.setPosition(0,0);
		reqRes = new Image(new Texture("side_bar.png"));
		reqRes.setPosition(0,reqRes_bottom.getHeight());
		reqRes.setScaling(Scaling.stretchY);
		reqRes_top = new Image(new Texture("side_bar_top.png"));
		reqRes_top.setPosition(0,reqRes_bottom.getHeight()+reqRes.getHeight());

		//Population
		populationLabel = new Label("Population: "+ Data.main.getPopulationCount(), skin);
		pop_bar_left = new Image(new Texture("pop_bar_left.png"));
		pop_bar = new Image(new Texture("pop_bar.png"));
		pop_bar_right = new Image(new Texture("pop_bar_right.png"));

		populationGroup = new Group();
		populationGroup.addActor(pop_bar_left);
		populationGroup.addActor(pop_bar);
		populationGroup.addActor(pop_bar_right);
		populationGroup.addActor(populationLabel);

		pop_bar_left.setPosition(0,0);
		pop_bar.setPosition(pop_bar_left.getWidth(), 0);
		pop_bar_right.setPosition(pop_bar_left.getWidth()+pop_bar.getWidth(),0);
		glyphLayout.setText(bitmapFont, populationLabel.getText());
		populationLabel.setPosition(pop_bar_left.getWidth()+pop_bar.getWidth()/2-glyphLayout.width/2,pop_bar.getHeight()/2-glyphLayout.height/2);

		//Options
		resourceScroll = new ScrollPane(null);

		//Other
		settings = new Settings();
		settingsImage = new Image(new Texture("settings.png"));
		settingsImage.addListener(new Mechanics.settingsListener());

		space = new Texture("space.png");
		space.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		sun = new Image(new Texture("sun.png"));

		create_tex = new Texture("create.png");
		create_locked = new Texture("create_gray.png");
		upgrade_tex = new Texture("upgrade.png");
		upgrade_locked = new Texture("upgrade_gray.png");

		era = new Image(new Texture("cavemen.png"));

		updateEra();

		Planet planet = Data.main.getCurrentPlanet();
		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		planet.addListener(new Mechanics.planetListener());
		planet.setTouchable(Touchable.enabled);
		countLabel = new Label("", skin);

		Data.main.setPlanet(planet);

		multiplexer = new InputMultiplexer(Data.main.getStage(), new InputDetector());
		Gdx.input.setInputProcessor(multiplexer);

		insufficientResources.setColor(1f,1f,1f,0f);
	}
	public void updateUI(){
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		populationLabel.setText("Population: "+Data.main.getPopulationCount());

		if(Data.getSelectedEntry()!=null) {
			if(Data.getSelectedEntry().getResourcesNeeded()!=null)
				for (RequiredResource resource : Data.getSelectedEntry().getResourcesNeeded())
					resource.setResourceNumberText();
			if(Data.getSelectedEntry().getResourcesNeededToUpgrade()!=null)
				for (RequiredResource resource : Data.getSelectedEntry().getResourcesNeededToUpgrade())
					resource.setResourceNumberText();

		}
		if(Data.getCurrentTable()!=null)
			for(TableEntry entry: Data.getCurrentTable().getEntries()) {
				entry.setNumberLabelText();
				entry.setValueLabelText();
			}

		Data.main.getStage().getBatch().setColor(1,1,1,1);
	}

	public void updateResources(){
		buildings_background.setDrawable(new SpriteDrawable(new Sprite(unclicked)));
		food_background.setDrawable(new SpriteDrawable(new Sprite(unclicked)));
		resources_background.setDrawable(new SpriteDrawable(new Sprite(unclicked)));
		special_background.setDrawable(new SpriteDrawable(new Sprite(unclicked)));
		switch (Data.getResourceType()) {
			case BUILDINGS:
				buildings_background.setDrawable(new SpriteDrawable(new Sprite(clicked)));
				Data.setCurrentTable(Data.getBuildingTable());
				break;
			case FOOD:
				food_background.setDrawable(new SpriteDrawable(new Sprite(clicked)));
				Data.setCurrentTable(Data.getFoodTable());
				break;
			case RESOURCES:
				resources_background.setDrawable(new SpriteDrawable(new Sprite(clicked)));
				Data.setCurrentTable(Data.getResourcesTable());
				break;
			case SPECIAL:
				special_background.setDrawable(new SpriteDrawable(new Sprite(clicked)));
				Data.setCurrentTable(Data.getSpecialTable());
				break;
		}
	}

	public void updateEra(){
		era.setDrawable(new SpriteDrawable(new Sprite(Data.getCurrentEra().getTexture())));
	}

	public void resize(int width, int height) {
		refreshBuildingTable();
		refreshFoodTable();
		refreshResourcesTable();
		refreshSpecialTable();

		//Bottom Bar
		line.setWidth(width);
		buildings_background.setWidth(width/4);
		food_background.setWidth(width/4);
		resources_background.setWidth(width/4);
		special_background.setWidth(width/4);

		line.setPosition(0, buildings_background.getHeight()+1);
		buildings_background.setPosition(0,0);
		food_background.setPosition(buildings_background.getWidth(), 0);
		resources_background.setPosition(buildings_background.getWidth()+food_background.getWidth(),0);
		special_background.setPosition(buildings_background.getWidth()+food_background.getWidth()+resources_background.getWidth(), 0);

		buildings.setPosition(buildings_background.getWidth()/2-buildings.getWidth()/2, buildings_background.getHeight()/2-buildings.getHeight()/2);
		food.setPosition(buildings_background.getWidth()+food_background.getWidth()/2-food.getWidth()/2, food_background.getHeight()/2-food.getHeight()/2);
		resources.setPosition(buildings_background.getWidth()+food_background.getWidth()+resources_background.getWidth()/2-resources.getWidth()/2, resources_background.getHeight()/2-resources.getHeight()/2);
		special.setPosition(buildings_background.getWidth()+food_background.getWidth()+resources_background.getWidth()+special_background.getWidth()/2-special.getWidth()/2, special_background.getHeight()/2-special.getHeight()/2);

		Planet planet = Data.main.getPlanet();

		planet.setMultiplier(width / planet.getInitial_width() * 0.325f);
		heightScale = ((float) height/480f); //480 = default height
		heightScale = (float) Math.pow(1.4, heightScale-1); //Magic. Oooooohh

		sun.setScale(heightScale);
		settingsImage.setScale(heightScale);
		populationGroup.setScale(heightScale);
		era.setScale(heightScale);

		Data.main.getStage().dispose();
		Stage stage = new Stage();
		stage.addActor(resourceGroup);
		stage.addActor(sun);
		stage.addActor(settingsImage);
		stage.addActor(populationGroup);
		stage.addActor(era);
		stage.addActor(planet);
		stage.addActor(insufficientResources);
		Data.main.setStage(stage);

		multiplexer = new InputMultiplexer(stage, new InputDetector());
		Gdx.input.setInputProcessor(multiplexer);

		sun.setX(0);
		sun.setY(height-sun.getHeight()*heightScale);

		settingsImage.setX(width-settingsImage.getWidth()*heightScale);
		settingsImage.setY(height-settingsImage.getHeight()*heightScale);

		glyphLayout.setText(bitmapFont, populationLabel.getText());
		pop_bar.setWidth(glyphLayout.width+100);

		pop_bar_left.setPosition(0,0);
		pop_bar.setPosition(pop_bar_left.getWidth(), 0);
		pop_bar_right.setPosition(pop_bar_left.getWidth()+pop_bar.getWidth(),0);
		populationLabel.setPosition(pop_bar_left.getWidth()+pop_bar.getWidth()/2-glyphLayout.width/2,pop_bar.getHeight()/2-glyphLayout.height);
		populationGroup.setX(width/2-(pop_bar_left.getWidth()+pop_bar.getWidth()+pop_bar_right.getWidth())*heightScale/2);
		populationGroup.setY(height-pop_bar.getHeight()*heightScale);

		era.setX(width/2-era.getWidth()*heightScale/2);
		era.setY(height-pop_bar.getHeight()*heightScale-era.getHeight()*heightScale-10);

		planet.setX(width/2-planet.getWidth()/2);
		if(Data.main.isBuildingTableVisible()||Data.main.isFoodTableVisible()||Data.main.isResourcesTableVisible()||Data.main.isSpecialTableVisible()){
			planetY = (era.getY()+buildings_background.getHeight()+(Gdx.graphics.getHeight()-2*padding)/rows)/2-planet.getHeight()/2;
		}else{
			planetY = (height/2-planet.getHeight()/2);
		}
		planet.setY(planetY);
		planet.setBounds(planet.getX(), planet.getY(), planet.getWidth(), planet.getHeight());

		insufficientResources.setScaling(Scaling.fit);

		insufficientResources.setScale(width/320f);
		insufficientResources.setX(planet.getX()+planet.getWidth()/2-insufficientResources.getWidth()*insufficientResources.getScaleX()/2);
		insufficientResources.setY(planet.getY()+planet.getHeight()/2-insufficientResources.getHeight()*insufficientResources.getScaleY()/2);
		insufficientResources.setTouchable(Touchable.disabled);

		refreshBuildingTable();
		refreshFoodTable();
		refreshResourcesTable();
		refreshSpecialTable();

		settings.resize(width, height, heightScale, stage);
		settings.getAboutInfo().resize(width, height, heightScale, stage);
		settings.getResetConfirm().resize(width, height, heightScale, stage);
		settings.getQuitConfirm().resize(width, height, heightScale, stage);

		if(Data.getSelectedEntry()!=null) loadSideBar(Data.getSelectedEntry(), Data.getSelectedEntry().isCreateClicked());
	}

	public void loadSideBar(TableEntry entry, boolean create){
		float multiplier = Gdx.graphics.getWidth()/640f;

		if(entry==null || (create && (entry.getResourcesNeeded()==null || entry.getResourcesNeeded().size()==0)) || (!create && (entry.getResourcesNeededToUpgrade()==null || entry.getResourcesNeededToUpgrade().size()==0))){
			if(reqResGroup!=null) reqResGroup.remove();
			return;
		}
		if(reqResGroup!=null) reqResGroup.remove();
		reqResGroup = new Group();
		reqResGroup.addActor(reqRes_top);
		reqResGroup.addActor(reqRes);
		reqResGroup.addActor(reqRes_bottom);
		float totalHeight = 0;
		ArrayList<RequiredResource> resources;

		if(create)  resources = entry.getResourcesNeeded();
		else        resources = entry.getResourcesNeededToUpgrade();

		for(RequiredResource resource: resources){
			Image image = resource.getResource();
			if(resource.getNumberRequired()!=0) {
				Label numberNeeded = resource.getResourceNumber();
				glyphLayout = new GlyphLayout(bitmapFont, numberNeeded.getText());

				image.setPosition(reqRes.getWidth() / 2 - image.getWidth() / 2, totalHeight + reqRes_bottom.getHeight() * 3 / 4);
				numberNeeded.setPosition(reqRes.getWidth() / 2 - glyphLayout.width / 2, totalHeight + reqRes_bottom.getHeight() * 3 / 4 - glyphLayout.height);

				totalHeight += image.getHeight();
				totalHeight += glyphLayout.height;

				reqResGroup.addActor(image);
				reqResGroup.addActor(numberNeeded);
			}
		}
		reqRes.setHeight(totalHeight-reqRes_bottom.getHeight());
		reqRes_top.setPosition(0,reqRes_bottom.getHeight()+reqRes.getHeight());
		Data.main.getStage().addActor(reqResGroup);

		reqResGroup.scaleBy(multiplier-1);

		reqResGroup.setX(Gdx.graphics.getWidth() - reqRes.getWidth()*multiplier);
		reqResGroup.setY(Gdx.graphics.getHeight() / 2 - (reqRes_bottom.getHeight()+reqRes.getHeight()+reqRes_top.getHeight())*multiplier/2);
	}

	private float scroll = 0;
	private float scrollSpeed = 0.2f;

	int rows = 4;
	float padding = 3;
	float rowHeight = (Gdx.graphics.getHeight()-2*padding)/rows/4; //4 because I want it to cover 1/4 of the screen

	Drawable tableBottomBackground = new Drawable() {
		@Override
		public void draw(Batch batch, float x, float y, float width, float height) {
			padding = Gdx.graphics.getHeight()/100;
			rowHeight = (Gdx.graphics.getHeight()-2*padding)/rows/4; //4 because I want it to cover 1/4 of the screen
			batch.draw(tableBottomBackground_tex, 0, 0, Gdx.graphics.getWidth(), rowHeight*(rows)+padding);
		}

		@Override
		public float getLeftWidth() {
			return 0;
		}

		@Override
		public void setLeftWidth(float leftWidth) {

		}

		@Override
		public float getRightWidth() {
			return 0;
		}

		@Override
		public void setRightWidth(float rightWidth) {

		}

		@Override
		public float getTopHeight() {
			return 0;
		}

		@Override
		public void setTopHeight(float topHeight) {

		}

		@Override
		public float getBottomHeight() {
			return 0;
		}

		@Override
		public void setBottomHeight(float bottomHeight) {

		}

		@Override
		public float getMinWidth() {
			return 0;
		}

		@Override
		public void setMinWidth(float minWidth) {

		}

		@Override
		public float getMinHeight() {
			return 0;
		}

		@Override
		public void setMinHeight(float minHeight) {

		}
	};

	Drawable tableTopBackground = new Drawable() {
		@Override
		public void draw(Batch batch, float x, float y, float width, float height) {
			padding = Gdx.graphics.getHeight()/100;
			rowHeight = (Gdx.graphics.getHeight()-2*padding)/rows/4; //4 because I want it to cover 1/4 of the screen
			batch.draw(tableTopBackground_tex, 0, buildings_background.getHeight()+rowHeight*(rows-1), Gdx.graphics.getWidth(), rowHeight+padding);
		}

		@Override
		public float getLeftWidth() {
			return 0;
		}

		@Override
		public void setLeftWidth(float leftWidth) {

		}

		@Override
		public float getRightWidth() {
			return 0;
		}

		@Override
		public void setRightWidth(float rightWidth) {

		}

		@Override
		public float getTopHeight() {
			return 0;
		}

		@Override
		public void setTopHeight(float topHeight) {

		}

		@Override
		public float getBottomHeight() {
			return 0;
		}

		@Override
		public void setBottomHeight(float bottomHeight) {

		}

		@Override
		public float getMinWidth() {
			return 0;
		}

		@Override
		public void setMinWidth(float minWidth) {

		}

		@Override
		public float getMinHeight() {
			return 0;
		}

		@Override
		public void setMinHeight(float minHeight) {

		}
	};

	public void setEverythingTouchable(Touchable touchable){
		Data.main.getPlanet().setTouchable(touchable);
		resourceGroup.setTouchable(touchable);

		if(buildingTable.isVisible()) buildingTable.setTouchable(touchable);
		if(foodTable.isVisible()) foodTable.setTouchable(touchable);
		if(resourcesTable.isVisible()) resourcesTable.setTouchable(touchable);
		if(specialTable.isVisible()) specialTable.setTouchable(touchable);

		sun.setTouchable(touchable);
	}

	public void refreshTable(){
		switch (Data.getResourceType()){
			case BUILDINGS:
				refreshBuildingTable();
				break;
			case FOOD:
				refreshFoodTable();
				break;
			case RESOURCES:
				refreshResourcesTable();
				break;
			case SPECIAL:
				refreshSpecialTable();
				break;
		}
	}

	public void refreshBuildingTable(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(7);
		list.add(6);
		list.add(5);
		list.add(4);
		list.add(3);
		list.add(2);
		list.add(1);
		list.add(0);

		buildingTable = refreshTableX(Data.getBuildingTable(),"Holds", Data.main.isBuildingTableVisible(), list);
	}

	public void refreshFoodTable(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(7);
		list.add(6);
		list.add(5);
		list.add(4);
		list.add(3);
		list.add(2);
		list.add(1);
		list.add(0);

		foodTable = refreshTableX(Data.getFoodTable(),"Feeds", Data.main.isFoodTableVisible(), list);
	}

	public void refreshResourcesTable(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(0);
		list.add(3);
		list.add(2);
		list.add(5);
		list.add(4);
		list.add(7);
		list.add(6);
		list.add(9);
		list.add(8);
		list.add(11);
		list.add(10);
		list.add(13);
		list.add(12);
		list.add(15);
		list.add(14);
		list.add(17);
		list.add(16);
		list.add(18);

		resourcesTable = refreshTableX(Data.getResourcesTable(),"Value", Data.main.isResourcesTableVisible(), list);
	}

	public void refreshSpecialTable(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);

		specialTable = refreshTableX(Data.getSpecialTable(),"Clicks", Data.main.isSpecialTableVisible(), list);
	}

	public ScrollPane refreshTableX(TableInfo info, String secret, boolean isVisible, ArrayList<Integer> rowList){
		rows = 4;
		padding = Gdx.graphics.getHeight()/100;
		rowHeight = (Gdx.graphics.getHeight())/rows/4; //4 because I want it to cover 1/4 of the screen

		float smallUnit = Gdx.graphics.getWidth() * 1/7;
		float largeUnit = Gdx.graphics.getWidth() * 1.25f/7;

		Table scrollTable = new Table();

		Table titleTable = new Table();
		titleTable.setSkin(skin);
		addTitleRow(titleTable, secret, smallUnit, largeUnit);
		titleTable.pad(0, 0,0,0);

		info.updateButtons();

		scrollTable.setSkin(skin);
		scrollTable.row().height(rowHeight);
		for(Integer i: rowList){
			if(!i.equals(rowList.get(rowList.size()-1))) addRow(scrollTable, false, smallUnit, largeUnit, info, i, info.getEntries().get(i).getImage());
			else addRow(scrollTable, true, smallUnit, largeUnit, info, i, info.getEntries().get(i).getImage());
		}


		scrollTable.pad(0, 0,0,0);

		scrollTable.setBackground(tableBottomBackground);
		titleTable.setBackground(tableTopBackground);

		ScrollPane scroller = new ScrollPane(scrollTable);
		scroller.setScrollingDisabled(true,false);
		scroller.setHeight(3*rowHeight);
		scroller.setWidth(Gdx.graphics.getWidth());

		titleTable.setHeight(rowHeight);
		titleTable.setWidth(Gdx.graphics.getWidth());

		titleTable.setX(0);
		titleTable.setY(line.getY()+1+scroller.getHeight());
		scroller.setX(0);
		scroller.setY(line.getY()+1);

		if(isVisible){ //Believe me, I know it's weird, but somehow doesn't work without. Feel free to try, but don't blame me for breaking the project.
			scroller.setVisible(true);
			titleTable.setVisible(true);
		} else {
			scroller.setVisible(false);
			titleTable.setVisible(false);
		}

		Stage stage = Data.main.getStage();
		stage.addActor(titleTable);
		stage.addActor(scroller);
		return scroller;
	}

	public void setAllTablesInvisible(){
		Data.main.setBuildingTableVisible(false);
		Data.main.setFoodTableVisible(false);
		Data.main.setResourcesTableVisible(false);
		Data.main.setSpecialTableVisible(false);
	}

	private void addTitleRow(Table table, String valueName, float smallUnit, float largeUnit){
		table.add().width(smallUnit).center();
		table.add("Name").width(largeUnit).center().fill();
		table.add(valueName).width(largeUnit).center().fill();
		table.add("No.").width(smallUnit).center().fill();
		table.add().width(largeUnit).center();
		table.add().width(largeUnit).center();
		table.row().height(rowHeight);
	}

	private void addRow(Table table, boolean lastRow, float smallUnit, float largeUnit, TableInfo info, int entry, Image picture){
		table.add(picture).width(smallUnit).center();
		table.add(info.getEntries().get(entry).getName()).width(largeUnit).center().fill();
		table.add(info.getEntries().get(entry).getValueLabel()).width(largeUnit).center().fill();
		table.add(info.getEntries().get(entry).getNumberLabel()).width(smallUnit).center().fill();
		table.add(info.getEntries().get(entry).getCreate()).width(largeUnit).center();
		table.add(info.getEntries().get(entry).getUpgrade()).width(largeUnit).center();
		if(!lastRow) table.row().height(rowHeight);
	}

	public void drawBackground(Batch batch){
		batch.draw(space, (int)-scroll, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0,0,4,4);
		batch.draw(space, Gdx.graphics.getWidth()-(int)scroll, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0,0,4,4);
		scroll+=scrollSpeed;
		if(scroll>=Gdx.graphics.getWidth()) scroll-=Gdx.graphics.getWidth();
	}

	public void dispose(){

	}

	//Getters and Setters

	public Image getInsufficientResources() {
		return insufficientResources;
	}

	public Skin getSkin() {
		return skin;
	}

	public Texture getRequired_tex() {
		return required_tex;
	}

	public void setRequired_tex(Texture required_tex) {
		this.required_tex = required_tex;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}

	public void setBitmapFont(BitmapFont bitmapFont) {
		this.bitmapFont = bitmapFont;
	}

	public Label getPopulationLabel() {
		return populationLabel;
	}
}
