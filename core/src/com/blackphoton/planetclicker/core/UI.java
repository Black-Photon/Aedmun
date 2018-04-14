package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.Picture;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.RequiredResource;
import com.blackphoton.planetclicker.objectType.table.Row;
import com.blackphoton.planetclicker.objectType.table.TableInfo;
import com.blackphoton.planetclicker.objectType.table.entries.TableEntry;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;

public class UI {

	//Bottom Bar
	private Texture buildings_tex;
	private Texture food_tex;
	private Texture resources_tex;
	private Texture special_tex;
	private Texture clicked;
	private Texture unclicked;
	private Picture buildings_background;
	private Picture food_background;
	private Picture resources_background;
	private Picture special_background;
	private Picture line;
	private Picture buildings;
	private Picture food;
	private Picture resources;
	private Picture special;
	private Group resourceGroup;

	//Population
	private Label populationLabel;
	private Group populationGroup;
	private Picture pop_bar_left;
	private Picture pop_bar;
	private Picture pop_bar_right;

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
	private Table buildingTable;
	private Texture house_tex;
	private Image house;
	private Texture village_tex;
	private Image village;
	private Texture town_tex;
	private Image town;

	// - Food
	private ScrollPane foodScroll;
	private Table foodTable;
	private Texture hunt_tex;
	private Image hunt;
	private Texture small_farm_tex;
	private Image small_farm;
	private Texture farm_tex;
	private Image farm;

	// - Resources
	private ScrollPane resourceScroll;
	private Table resourcesTable;
	private Texture wood_tex;
	private Image wood;
	private Texture woodmill_tex;
	private Image woodmill;
	private Texture stone_tex;
	private Image stone;
	private Texture bronze_tex;
	private Image bronze;
	private Texture iron_tex;
	private Image iron;

	// - Special
	private Table specialTable;

	//Other
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	private Picture era;
	private Picture settings;
	private Texture space;
	private Picture sun;
	private Label countLabel;
	private final Label insufficientResources = new Label("You don't have enough resources to build this", skin);
	private GlyphLayout glyphLayout;
	private BitmapFont bitmapFont;
	private float heightScale;
	private InputMultiplexer multiplexer;
	public float planetY;

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

		buildings = new Picture(buildings_tex, 0, 0);
		food = new Picture(food_tex, 0, 0);
		resources = new Picture(resources_tex, 0, 0);
		special = new Picture(special_tex , 0, 0);
		line = new Picture(new Texture("horizontal_line.png"), 0, 0);

		buildings_background = new Picture(unclicked, 0, 0);
		food_background = new Picture(unclicked, 0, 0);
		resources_background = new Picture(unclicked, 0, 0);
		special_background = new Picture(unclicked, 0, 0);

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
		pop_bar_left = new Picture(new Texture("pop_bar_left.png"),0,0);
		pop_bar = new Picture(new Texture("pop_bar.png"),0,0);
		pop_bar_right = new Picture(new Texture("pop_bar_right.png"),0,0);

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
		house_tex = new Texture("house.png");
		house = new Image(house_tex);
		village_tex = new Texture("village.png");
		village = new Image(village_tex);
		town_tex = new Texture("town.png");
		town = new Image(town_tex);

		hunt_tex = new Texture("hunt.png");
		hunt = new Image(hunt_tex);
		small_farm_tex = new Texture("small_farm.png");
		small_farm = new Image(small_farm_tex);
		farm_tex = new Texture("farm.png");
		farm = new Image(farm_tex);

		wood_tex = new Texture("wood.png");
		wood = new Image(wood_tex);
		woodmill_tex = new Texture("woodmill.png");
		woodmill = new Image(woodmill_tex);
		stone_tex = new Texture("stone.png");
		stone = new Image(stone_tex);
		bronze_tex = new Texture("bronze_bar.png");
		bronze = new Image(bronze_tex);
		iron_tex = new Texture("iron_bar.png");
		iron = new Image(iron_tex);
		resourceScroll = new ScrollPane(null);

		//Other
		space = new Texture("space.png");
		space.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		sun = new Picture(new Texture("sun.png"),0,0);
		settings = new Picture(new Texture("settings.png"),0,0);

		create_tex = new Texture("create.png");
		create_locked = new Texture("create_gray.png");
		upgrade_tex = new Texture("upgrade.png");
		upgrade_locked = new Texture("upgrade_gray.png");

		era = new Picture(new Texture("cavemen.png"),0,0);

		Planet planet = Data.getCurrentPlanet();
		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		planet.addListener(new Mechanics.planetListener());
		planet.setTouchable(Touchable.enabled);
		countLabel = new Label("", skin);

		Data.main.setPlanet(planet);

		insufficientResources.setColor(0.8f,0.8f,0.8f,0);

		multiplexer = new InputMultiplexer(Data.main.getStage(), new InputDetector());
		Gdx.input.setInputProcessor(multiplexer);
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
		buildings_background.setTexture(unclicked);
		food_background.setTexture(unclicked);
		resources_background.setTexture(unclicked);
		special_background.setTexture(unclicked);
		switch (Data.getResourceType()) {
			case BUILDINGS:
				buildings_background.setTexture(clicked);
				Data.setCurrentTable(Data.getBuildingTable());
				break;
			case FOOD:
				food_background.setTexture(clicked);
				Data.setCurrentTable(Data.getFoodTable());
				break;
			case RESOURCES:
				resources_background.setTexture(clicked);
				Data.setCurrentTable(Data.getResourcesTable());
				break;
			case SPECIAL:
				special_background.setTexture(clicked);
				Data.setCurrentTable(Data.getSpecialTable());
				break;
		}
	}

	public void updateEra(Era thisEra){
		era.setTexture(new Texture(thisEra.getImagePath()));
	}

	public void resize(int width, int height) {
		refreshBuildingTable();
		refreshFoodTable();
		refreshResourcesTable();
		refreshSpecialTable();

		//Bottom Bar
		line.setWidth(Gdx.graphics.getWidth());
		buildings_background.setWidth(Gdx.graphics.getWidth()/4);
		food_background.setWidth(Gdx.graphics.getWidth()/4);
		resources_background.setWidth(Gdx.graphics.getWidth()/4);
		special_background.setWidth(Gdx.graphics.getWidth()/4);

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

		planet.setMultiplier(Gdx.graphics.getWidth() / planet.getInitial_width() * 0.325f);
		heightScale = ((float) Gdx.graphics.getHeight())/480f; //480 = default height
		heightScale = (float) Math.pow(1.4, heightScale-1); //Magic. Oooooohh

		sun.setScale(heightScale);
		settings.setScale(heightScale);
		populationGroup.setScale(heightScale);
		era.setScale(heightScale);

		Data.main.getStage().dispose();
		Stage stage = new Stage();
		stage.addActor(resourceGroup);
		stage.addActor(sun);
		stage.addActor(settings);
		stage.addActor(populationGroup);
		stage.addActor(era);
		stage.addActor(planet);
		stage.addActor(insufficientResources);
		Data.main.setStage(stage);

		multiplexer = new InputMultiplexer(stage, new InputDetector());
		Gdx.input.setInputProcessor(multiplexer);

		sun.setX(0);
		sun.setY(Gdx.graphics.getHeight()-sun.getHeight());

		settings.setX(Gdx.graphics.getWidth()-settings.getWidth());
		settings.setY(Gdx.graphics.getHeight()-settings.getHeight());

		glyphLayout.setText(bitmapFont, populationLabel.getText());
		pop_bar.setWidth(glyphLayout.width+100);

		pop_bar_left.setPosition(0,0);
		pop_bar.setPosition(pop_bar_left.getWidth(), 0);
		pop_bar_right.setPosition(pop_bar_left.getWidth()+pop_bar.getWidth(),0);
		populationLabel.setPosition(pop_bar_left.getWidth()+pop_bar.getWidth()/2-glyphLayout.width/2,pop_bar.getHeight()/2-glyphLayout.height);
		populationGroup.setX(Gdx.graphics.getWidth()/2-(pop_bar_left.getWidth()+pop_bar.getWidth()+pop_bar_right.getWidth())*heightScale/2);
		populationGroup.setY(Gdx.graphics.getHeight()-pop_bar.getHeight()*heightScale);

		glyphLayout.setText(bitmapFont, insufficientResources.getText());
		insufficientResources.setX(Gdx.graphics.getWidth()/2-glyphLayout.width/2);
		insufficientResources.setY(Gdx.graphics.getHeight()/2-glyphLayout.height/2);
		insufficientResources.setTouchable(Touchable.disabled);

		era.setX(Gdx.graphics.getWidth()/2-era.getWidth()/2);
		era.setY(Gdx.graphics.getHeight()-pop_bar.getHeight()*heightScale-era.getHeight()-10);

		planet.setX(Gdx.graphics.getWidth()/2-planet.getWidth()/2);
		if(Data.main.isBuildingTableVisible()||Data.main.isFoodTableVisible()||Data.main.isResourcesTableVisible()||Data.main.isSpecialTableVisible()){
			planetY = (era.getY()+buildings_background.getHeight()+(Gdx.graphics.getHeight()-2*padding)/rows)/2-planet.getHeight()/2;
		}else{
			planetY = (Gdx.graphics.getHeight()/2-planet.getHeight()/2);
		}
		planet.setY(planetY);
		planet.setBounds(planet.getX(), planet.getY(), planet.getWidth(), planet.getHeight());

		refreshBuildingTable();
		refreshFoodTable();
		refreshResourcesTable();

		if(Data.getSelectedEntry()!=null) loadSideBar(Data.getSelectedEntry(), Data.getSelectedEntry().isCreateClicked());
	}

	public void loadSideBar(TableEntry entry, boolean create){
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
			int numberRequired = resource.getNumberRequired();
			Label numberNeeded = resource.getResourceNumber();
			glyphLayout = new GlyphLayout(bitmapFont, Integer.toString(numberRequired));

			image.setPosition(reqRes.getWidth()/2-image.getWidth()/2, totalHeight+reqRes_bottom.getHeight()*3/4);
			numberNeeded.setPosition(reqRes.getWidth()/2-glyphLayout.width/2, totalHeight+reqRes_bottom.getHeight()*3/4-glyphLayout.height);

			totalHeight+=image.getHeight();
			totalHeight+=glyphLayout.height;

			reqResGroup.addActor(image);
			reqResGroup.addActor(numberNeeded);
		}
		reqRes.setHeight(totalHeight-reqRes_bottom.getHeight());
		reqRes_top.setPosition(0,reqRes_bottom.getHeight()+reqRes.getHeight());
		Data.main.getStage().addActor(reqResGroup);


		reqResGroup.setX(Gdx.graphics.getWidth() - reqRes.getWidth());
		reqResGroup.setY(Gdx.graphics.getHeight() / 2 - (reqRes_bottom.getHeight()+reqRes.getHeight()+reqRes_top.getHeight())/2);
	}

	private float scroll = 0;
	private float scrollSpeed = 0.2f;

	int rows = 4;
	float padding = 3;
	float rowHeight = (Gdx.graphics.getHeight()-2*padding)/rows/4; //4 because I want it to cover 1/4 of the screen

	Drawable tableBackground = new Drawable() {
		@Override
		public void draw(Batch batch, float x, float y, float width, float height) {
			batch.draw(tableBackground_tex, 0, buildings_background.getHeight(), Gdx.graphics.getWidth(), rowHeight*rows+padding*2);
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
		house.setScaling(Scaling.fit);
		village.setScaling(Scaling.fit);
		town.setScaling(Scaling.fit);

		ArrayList<Row> list = new ArrayList<Row>();
		list.add(new Row(2, house));
		list.add(new Row(1, village));
		list.add(new Row(0, town));

		refreshTableX(Data.getBuildingTable(), buildingTitleTable, buildingScroller, "Holds", Data.main.isBuildingTableVisible(), list);
	}

	public void refreshFoodTable(){
		hunt.setScaling(Scaling.fit);
		small_farm.setScaling(Scaling.fit);
		farm.setScaling(Scaling.fit);

		ArrayList<Row> list = new ArrayList<Row>();
		list.add(new Row(2, hunt));
		list.add(new Row(1, small_farm));
		list.add(new Row(0, farm));

		refreshTableX(Data.getFoodTable(), foodTitleTable, foodScroller, "Feeds", Data.main.isFoodTableVisible(), list);
	}

	private Table buildingTitleTable;
	private Table foodTitleTable;
	private Table resourceTitleTable;

	private ScrollPane buildingScroller;
	private ScrollPane foodScroller;
	private ScrollPane resourceScroller;

	public void refreshResourcesTable(){
		wood.setScaling(Scaling.fit);
		woodmill.setScaling(Scaling.fit);
		stone.setScaling(Scaling.fit);
		bronze.setScaling(Scaling.fit);
		iron.setScaling(Scaling.fit);

		ArrayList<Row> list = new ArrayList<Row>();
		list.add(new Row(1, wood));
		list.add(new Row(0, woodmill));
		list.add(new Row(3, stone));
		list.add(new Row(2, null));
		list.add(new Row(5, bronze));
		list.add(new Row(4, null));
		list.add(new Row(7, iron));
		list.add(new Row(6, null));

		refreshTableX(Data.getResourcesTable(), resourceTitleTable, resourceScroller, "Value", Data.main.isResourcesTableVisible(), list);
	}

	public void refreshSpecialTable(){ }

	public void refreshTableX(TableInfo info, Table titleTable, ScrollPane scroller, String secret, boolean isVisible, ArrayList<Row> rowList){
		rows = 4;
		padding = Gdx.graphics.getHeight()/100;
		rowHeight = (Gdx.graphics.getHeight())/rows/4; //4 because I want it to cover 1/4 of the screen

		if(titleTable!=null) titleTable.remove();
		if(scroller!=null) scroller.remove();

		float smallUnit = Gdx.graphics.getWidth() * 1/7;
		float largeUnit = Gdx.graphics.getWidth() * 1.25f/7;

		Table scrollTable = new Table();

		titleTable = new Table();
		titleTable.setSkin(skin);
		addTitleRow(titleTable, secret, smallUnit, largeUnit);
		titleTable.pad(0, 0,0,0);

		info.updateButtons();

		scrollTable.setSkin(skin);
		scrollTable.row().height(rowHeight);
		for(Row row: rowList){
			if(!row.equals(rowList.get(rowList.size()-1))) addRow(scrollTable, false, smallUnit, largeUnit, info, row.getLine(), row.getImage());
			else addRow(scrollTable, true, smallUnit, largeUnit, info, row.getLine(), row.getImage());
		}


		scrollTable.pad(0, 0,0,0);

		scrollTable.setBackground(tableBottomBackground);
		titleTable.setBackground(tableTopBackground);

		scroller = new ScrollPane(scrollTable);
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

	public void refreshStage(){

	}

	public void drawBackground(Batch batch){
		batch.draw(space, (int)-scroll, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0,0,4,4);
		batch.draw(space, Gdx.graphics.getWidth()-(int)scroll, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0,0,4,4);
		scroll+=scrollSpeed;
		if(scroll>=Gdx.graphics.getWidth()) scroll-=Gdx.graphics.getWidth();
	}

	public void dispose(){

	}

	public Label getInsufficientResources() {
		return insufficientResources;
	}

	public Skin getSkin() {
		return skin;
	}
}
