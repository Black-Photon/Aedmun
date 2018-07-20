package com.blackphoton.planetclicker.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Main user input detector. Listeners for non-actor-specific events go here
 */
public class InputDetector implements InputProcessor {

	@Override
	public boolean keyDown(int keycode) {
		//If back on Android clicked
		if(keycode == Input.Keys.BACK){
			//Dismisses current settings menu if open
			if(Data.ui.getSettings().getQuitConfirm().getInfoTable().isVisible()){
				Data.ui.getSettings().getQuitConfirm().getInfoTable().setVisible(false);
				Data.ui.getSettings().getSettingsGroup().setVisible(true);
			}else if(Data.ui.getSettings().getResetConfirm().getInfoTable().isVisible()){
				Data.ui.getSettings().getResetConfirm().getInfoTable().setVisible(false);
				Data.ui.getSettings().getSettingsGroup().setVisible(true);
			}else if(Data.ui.getSettings().getAboutInfo().getInfoTable().isVisible()) {
				Data.ui.getSettings().getAboutInfo().getInfoTable().setVisible(false);
				Data.ui.getSettings().getSettingsGroup().setVisible(true);
			}else if(Data.ui.getSettings().getSettingsGroup().isVisible()){
				Data.ui.getSettings().getSettingsGroup().setVisible(false);
				Data.ui.setEverythingTouchable(Touchable.enabled);
				return true;
			}else {
				Data.mechanics.getSaveThread().interrupt();
				Data.main.exit();
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

