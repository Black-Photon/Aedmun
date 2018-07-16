package com.blackphoton.planetclicker.messages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackphoton.planetclicker.core.Data;

public class TutorialInfo extends Info {
	TutorialInfo nextBox;
	int tutorial;

	public TutorialInfo(String title, String info, Actor parent, int tutorial) {
		super(title, info, parent);
		back.setVisible(false);
		back.removeListener(back.getClickListener());
		back.addListener(new TutorialInfo.backListener());
		this.tutorial = tutorial;
	}

	public TutorialInfo(String title, String info, String button, Actor parent, int tutorial) {
		super(title, info, button, parent);
		back.removeListener(back.getClickListener());
		back.addListener(new TutorialInfo.backListener());
		this.tutorial = tutorial;
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
}
