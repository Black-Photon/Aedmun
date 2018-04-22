package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.blackphoton.planetclicker.resources.ResourceType;

public class InputDetector implements InputProcessor {

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.BACK){
			if(Data.ui.getSettings().getAboutInfo().getInfoTable().isVisible()) {
				Data.ui.getSettings().getAboutInfo().getInfoTable().getParent().setVisible(true);
				Data.ui.getSettings().getAboutInfo().getInfoTable().setVisible(false);
			}else if(Data.ui.getSettings().getSettingsGroup().isVisible()){
				Data.ui.setEverythingTouchable(Touchable.enabled);
				return true;
			}else {
				Data.setResourceType(ResourceType.NONE);
				Data.ui.refreshBuildingTable();
				Data.ui.refreshFoodTable();
				Data.ui.refreshResourcesTable();
				Data.ui.refreshSpecialTable();
				Gdx.app.exit();
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

