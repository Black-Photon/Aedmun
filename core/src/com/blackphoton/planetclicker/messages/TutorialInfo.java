package com.blackphoton.planetclicker.messages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackphoton.planetclicker.core.Data;

public class TutorialInfo extends Info {
	TutorialInfo nextBox;
	int tutorial;
	protected TextButton minimize;

	public TutorialInfo(String title, String info, Actor parent, int tutorial) {
		super(title, info, parent);
		back.setVisible(false);
		constructor(tutorial);
	}

	public TutorialInfo(String title, String info, String button, Actor parent, int tutorial) {
		super(title, info, button, parent);
		constructor(tutorial);
	}

	private void constructor(int tutorial){
		back.removeListener(back.getClickListener());
		back.addListener(new TutorialInfo.backListener());
		this.tutorial = tutorial;
		minimize = new TextButton(" - ", Data.ui.getSkin());
		infoTable.addActorAt(1, minimize);
		minimize.setBounds(infoTable.getWidth()-40, infoTable.getHeight()-40, 20, 20);
		minimize.addListener(new MinimizeListener());
	}

	public void setData(){
		try {
			nextBox = Data.mechanics.getCollection().getMessages().get(tutorial);
		} catch(IndexOutOfBoundsException e){
			System.out.println("Last in list. nextBox will be null");
		}
	}

	public class backListener extends ClickListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.mechanics.getCollection().objectiveComplete(tutorial);
			return true;
		}
	}

	public void complete(){
		infoTable.setVisible(false);
		if(nextBox!=null) {
			nextBox.show();
		}else{
			Data.setTutorialRunning(false);
		}
	}

	public static class MinimizeListener extends ClickListener{
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Data.mechanics.getCollection().getCurrentMessage().minimize();
			return true;
		}
	}

	public void minimize(){
		infoTable.setVisible(false);
		Data.setTutorialMinimized(true);
		Data.ui.setMaximizeVisible(true);
	}
	public void maximize(){
		infoTable.setVisible(true);
		Data.setTutorialMinimized(false);
		Data.ui.setMaximizeVisible(false);
	}
}
