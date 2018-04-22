package com.blackphoton.planetclicker.messages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.core.Mechanics;

public class Settings extends DisplayBox{
	private Table settingsGroup;
	private Label title;
	private TextButton about;
	private TextButton reset;
	private TextButton quit;
	Info aboutInfo;

	public Settings() {
		super();
		Skin skin = Data.ui.getSkin();

		final float padding = 5.0f;
		final float outpadding = 25.0f;
		final int width = 100;
		final int rowHeight = 30;

		settingsGroup = new Table();
		title = new Label("Settings", skin);
		about = new TextButton("About", skin);
		reset = new TextButton("Reset", skin);
		quit = new TextButton("Quit", skin);

		about.addListener(new aboutListener());
		reset.addListener(new resetListener());
		quit.addListener(new quitListener());

		settingsGroup.add(title).pad(outpadding, outpadding, padding, outpadding);
		settingsGroup.row().width(width).height(rowHeight);
		settingsGroup.add(about).pad(padding, outpadding, padding, outpadding);
		settingsGroup.row().width(width).height(rowHeight);
		settingsGroup.add(reset).pad(padding, outpadding, padding, outpadding);
		settingsGroup.row().width(width).height(rowHeight);
		settingsGroup.add(quit).pad(padding, outpadding, outpadding, outpadding);
		settingsGroup.row().width(width).height(rowHeight);

		settingsGroup.pack();

		settingsGroup.setBackground(displayBackground);

		settingsGroup.setVisible(false);

		aboutInfo = new Info("About", "Aemun Version 0.2.0\nCreated by Black-Photon\nSoftware and Art Copyrighted to Joseph Keane April 2018", settingsGroup);
	}

	public void resize(int width, int height, float heightScale, Stage stage){
		super.resize(width, height, heightScale, stage, settingsGroup);
	}

	public class aboutListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			aboutInfo.show();
			return true;
		}
	}
	public class resetListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

			return true;
		}
	}
	public class quitListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

			return true;
		}
	}


	public Table getSettingsGroup() {
		return settingsGroup;
	}

	public void setSettingsGroup(Table settingsGroup) {
		this.settingsGroup = settingsGroup;
	}

	public Info getAboutInfo() {
		return aboutInfo;
	}
}
