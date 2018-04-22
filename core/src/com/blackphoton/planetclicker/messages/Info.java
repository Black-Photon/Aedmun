package com.blackphoton.planetclicker.messages;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.table.Row;

public class Info extends DisplayBox{
	private Label title;
	private Label info;
	private TextButton back;
	private Table infoTable;
	private Actor parent;

	public Info(String title, String info, Actor parent) {
		this.title = new Label(title, Data.ui.getSkin());
		this.info = new Label(info, Data.ui.getSkin());
		back = new TextButton("Back", Data.ui.getSkin());
		infoTable = new Table();
		infoTable.setSkin(Data.ui.getSkin());
		this.parent = parent;

		final float padding = 5.0f;
		final float outpadding = 25.0f;
		final int width = 350;
		final int rowHeight = 30;

		back.addListener(new backListener());

		this.info.setWrap(true);
		this.info.pack();

		this.info.setWidth(width);
		this.info.pack();
		this.info.setWidth(width);

		infoTable.row().width(width).height(rowHeight);
		infoTable.add(this.title).pad(outpadding, outpadding, padding, outpadding);
		infoTable.row().width(width).height(this.info.getPrefHeight());
		infoTable.add(this.info).pad(padding, outpadding, padding, outpadding);
		infoTable.row().width(width).height(rowHeight);
		infoTable.add(back).pad(padding, outpadding, outpadding, outpadding);

		infoTable.setWidth(width+2*outpadding);

		infoTable.pack();

		infoTable.setBackground(displayBackground);

		infoTable.setVisible(false);
	}

	public void show(){
		parent.setVisible(false);
		infoTable.setVisible(true);
	}

	public void resize(int width, int height, float heightScale, Stage stage) {
		super.resize(width, height, heightScale, stage, infoTable);
	}

	public class backListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			parent.setVisible(true);
			infoTable.setVisible(false);
			return true;
		}
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

	public Label getInfo() {
		return info;
	}

	public void setInfo(Label info) {
		this.info = info;
	}

	public TextButton getBack() {
		return back;
	}

	public void setBack(TextButton back) {
		this.back = back;
	}

	public Table getInfoTable() {
		return infoTable;
	}

	public void setInfoTable(Table infoTable) {
		this.infoTable = infoTable;
	}

	public Actor getParent() {
		return parent;
	}

	public void setParent(Actor parent) {
		this.parent = parent;
	}
}
