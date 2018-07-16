package com.blackphoton.planetclicker.messages;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class TutorialCollection {
	ArrayList<TutorialInfo> messages;
	int messageIndex = 1;

	public TutorialCollection(TutorialInfo... infos) {
		messages = new ArrayList<>();
		for(TutorialInfo info: infos){
			messages.add(info);
		}
	}

	public void next(){
		messageIndex++;
	}

	public void setData(){
		for(TutorialInfo info: messages){
			info.setData();
		}
	}

	public void resize(int width, int height, float heightScale, Stage stage) {
		for(TutorialInfo info: messages){
			info.resize(width, height, heightScale, stage);
		}
	}

	public ArrayList<TutorialInfo> getMessages() {
		return messages;
	}

	public void objectiveComplete(int objective){
		if(objective == messageIndex){
			messages.get(messageIndex-1).complete();
			messageIndex++;
		}
	}
	public TutorialInfo getCurrentMessage(){
		return messages.get(messageIndex-1);
	}
}
