package com.blackphoton.planetclicker.messages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackphoton.planetclicker.core.Data;

/**
 * A menu-box for the user to confirm they indeed want to do something
 */
public class ConfirmBox extends DisplayBox {
	private Label title;
	private Label info;
	private TextButton yes;
	private TextButton no;
	private Table infoTable;
	private Actor parent;

	/**
	 * @param title Title of the Confirm Box
	 * @param info Info to display under the title
	 * @param parent Parent actor opened from. When the box is opened the parent becomes invisible, and when closed the parent becomes invisible. Null if no parent.
	 * @param ifYes Event to do when the user confirms. The box closes if not confirmed.
	 */
	public ConfirmBox(String title, String info, Actor parent, ClickListener ifYes) {
		this.title = new Label(title, Data.ui.getSkin());
		this.info = new Label(info, Data.ui.getSkin());
		yes = new TextButton("Yes", Data.ui.getSkin());
		no = new TextButton("No", Data.ui.getSkin());
		infoTable = new Table();
		infoTable.setSkin(Data.ui.getSkin());
		this.parent = parent;

		final float padding = 5.0f;
		final float outpadding = 25.0f;
		final int width = 200;
		final int rowHeight = 30;

		yes.addListener(ifYes);
		no.addListener(new noListener());

		this.info.setWrap(true);
		this.info.pack();

		this.info.setWidth(2*width);
		this.info.pack();
		this.info.setWidth(2*width);

		Table yesno = new Table();
		yesno.add(yes).pad(padding).width(35);
		yesno.add(no).pad(padding).width(35);

		infoTable.row().width(width).height(rowHeight).expand();
		infoTable.add(this.title).pad(outpadding, outpadding, padding, outpadding);
		infoTable.row().width(width).height(this.info.getPrefHeight()).expand();
		infoTable.add(this.info).pad(padding, outpadding, padding, outpadding);
		infoTable.row().width(width).height(rowHeight);
		infoTable.add(yesno).pad(padding, outpadding, outpadding, outpadding).expand();

		infoTable.setWidth(width+2*outpadding);

		infoTable.pack();

		infoTable.setBackground(displayBackground);

		infoTable.setVisible(false);
	}

	/**
	 * Makes the box visible and the parent invisible
	 */
	public void show(){
		if(parent!=null) parent.setVisible(false);
		infoTable.setVisible(true);
	}

	public void resize(int width, int height, float heightScale, Stage stage) {
		super.resize(width, height, heightScale, stage, infoTable);
	}

	public class noListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if(parent!=null) parent.setVisible(true);
			infoTable.setVisible(false);
			return true;
		}
	}

	//Getters and Setters
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
	public Table getInfoTable() {
		return infoTable;
	}
	public void setInfoTable(Table infoTable) {
		this.infoTable = infoTable;
	}
}
