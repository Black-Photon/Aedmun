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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.Picture;
import com.blackphoton.planetclicker.objectType.Planet;
import com.blackphoton.planetclicker.objectType.table.TableInfo;

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

	//Options
	// - General
	private Texture tableBackground_tex = new Texture("table.png");
	private Texture create_tex;
	private Texture create_locked;
	private Texture upgrade_tex;
	private Texture upgrade_locked;

	// - Building
	private Table buildingTable;
	private Texture house_tex;
	private Image house;
	private Texture village_tex;
	private Image village;
	private Texture town_tex;
	private Image town;

	// - Food
	private Table foodTable;

	// - Resources
	private Table resourcesTable;

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
	private final int margin = 3;
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
	}

	public void updateResources(){
		buildings_background.setTexture(unclicked);
		food_background.setTexture(unclicked);
		resources_background.setTexture(unclicked);
		special_background.setTexture(unclicked);
		switch (Data.getResourceType()) {
			case BUILDINGS:
				buildings_background.setTexture(clicked);
				break;
			case FOOD:
				food_background.setTexture(clicked);
				break;
			case RESOURCES:
				resources_background.setTexture(clicked);
				break;
			case SPECIAL:
				special_background.setTexture(clicked);
				break;
		}
	}

	public void updateEra(Era thisEra){
		era.setTexture(new Texture(thisEra.getImagePath()));
	}

	public void resize(int width, int height) {
		refreshBuildingTable();

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
		stage.addActor(buildingTable);
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

		era.setX(Gdx.graphics.getWidth()/2-era.getWidth()/2);
		era.setY(Gdx.graphics.getHeight()-pop_bar.getHeight()*heightScale-era.getHeight()-10);

		planet.setX(Gdx.graphics.getWidth()/2-planet.getWidth()/2);
		if(Data.main.isBuildingTableVisible()){
			planetY = (era.getY()+buildings_background.getHeight()+buildingTable.getHeight())/2-planet.getHeight()/2;
		}else{
			planetY = (Gdx.graphics.getHeight()/2-planet.getHeight()/2);
		}
		planet.setY(planetY);
		planet.setBounds(planet.getX(), planet.getY(), planet.getWidth(), planet.getHeight());

		buildingTable.setX(0);
		buildingTable.setY(buildings_background.getHeight());
	}

	private float scroll = 0;
	private float scrollSpeed = 0.2f;

	final int rows = 4;
	final int padding = 5;
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
		TableInfo buildingInfo = Data.getBuildingTable();

		if(buildingTable!=null) buildingTable.remove();
		buildingTable = new Table();


		if(Data.main.isBuildingTableVisible()){
			buildingTable.setVisible(true);
		} else {
			buildingTable.setVisible(false);
		}

		float smallUnit = Gdx.graphics.getWidth() * 1/7;
		float largeUnit = Gdx.graphics.getWidth() * 1.25f/7;
		rowHeight = (Gdx.graphics.getHeight()-2*padding)/rows/4; //4 because I want it to cover 1/4 of the screen

		house.setScaling(Scaling.fit);
		village.setScaling(Scaling.fit);
		town.setScaling(Scaling.fit);

		buildingInfo.updateButtons();

		buildingTable.setSkin(skin);
		buildingTable.add().width(smallUnit).center();          buildingTable.add("Name").width(largeUnit).center().fill();                                          buildingTable.add("Holds").width(largeUnit).center().fill();                                                         buildingTable.add("No.").width(smallUnit).center().fill();                                                               buildingTable.add().width(largeUnit).center();                                               buildingTable.add().width(largeUnit).center();                                              buildingTable.row().height(rowHeight);
		buildingTable.add(house).width(smallUnit).center();     buildingTable.add(buildingInfo.getEntries().get(2).getName()).width(largeUnit).center().fill();      buildingTable.add(Integer.toString(buildingInfo.getEntries().get(2).getValue())).width(largeUnit).center().fill();   buildingTable.add(Integer.toString(buildingInfo.getEntries().get(2).getNumberOf())).width(smallUnit).center().fill();    buildingTable.add(buildingInfo.getEntries().get(2).getCreate()).width(largeUnit).center();   buildingTable.add(buildingInfo.getEntries().get(2).getUpgrade()).width(largeUnit).center(); buildingTable.row().height(rowHeight);
		buildingTable.add(village).width(smallUnit).center();   buildingTable.add(buildingInfo.getEntries().get(1).getName()).width(largeUnit).center().fill();      buildingTable.add(Integer.toString(buildingInfo.getEntries().get(1).getValue())).width(largeUnit).center().fill();   buildingTable.add(Integer.toString(buildingInfo.getEntries().get(1).getNumberOf())).width(smallUnit).center().fill();    buildingTable.add(buildingInfo.getEntries().get(1).getCreate()).width(largeUnit).center();   buildingTable.add(buildingInfo.getEntries().get(1).getUpgrade()).width(largeUnit).center(); buildingTable.row().height(rowHeight);
		buildingTable.add(town).width(smallUnit).center();      buildingTable.add(buildingInfo.getEntries().get(0).getName()).width(largeUnit).center().fill();      buildingTable.add(Integer.toString(buildingInfo.getEntries().get(0).getValue())).width(largeUnit).center().fill();   buildingTable.add(Integer.toString(buildingInfo.getEntries().get(0).getNumberOf())).width(smallUnit).center().fill();    buildingTable.add(buildingInfo.getEntries().get(0).getCreate()).width(largeUnit).center();   buildingTable.add(buildingInfo.getEntries().get(0).getUpgrade()).width(largeUnit).center(); //buildingTable.row();

		buildingTable.pad(padding);
		buildingTable.bottom().left();
		buildingTable.setBackground(tableBackground);
		buildingTable.setHeight(rowHeight*4);

		Stage stage = Data.main.getStage();
		stage.addActor(buildingTable);

		buildingTable.setX(0);
		buildingTable.setY(buildings_background.getHeight());
	}

	public void refreshFoodTable(){ }

	public void refreshResourcesTable(){ }

	public void refreshSpecialTable(){ }

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
}
