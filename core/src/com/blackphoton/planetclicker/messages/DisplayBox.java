package com.blackphoton.planetclicker.messages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class DisplayBox {
	protected Texture setting_background;
	protected Texture setting_side;
	protected Texture setting_top;
	protected Texture setting_lb;
	protected Texture setting_lt;
	protected Texture setting_rb;
	protected Texture setting_rt;

	public DisplayBox() {
		setting_background = new Texture("setting_background.png");
		setting_side = new Texture("setting_side.png");
		setting_top = new Texture("setting_top.png");
		setting_lb = new Texture("setting_lb.png");
		setting_lt = new Texture("setting_lt.png");
		setting_rb = new Texture("setting_rb.png");
		setting_rt = new Texture("setting_rt.png");
	}

	protected Drawable displayBackground = new Drawable() {
		@Override
		public void draw(Batch batch, float x, float y, float width, float height) {
			batch.draw(setting_background, x+5, y+5, width-10, height-10);

			batch.draw(setting_lb, x, y);
			batch.draw(setting_lt, x, y+height-10);
			batch.draw(setting_rb, x+width-10, y);
			batch.draw(setting_rt, x+width-10, y+height-10);

			batch.draw(setting_side, x, y+5, 5, height-10);
			batch.draw(setting_side, x+width-5, y+5, 5, height-10);
			batch.draw(setting_top, x+5, y, width-10, 5);
			batch.draw(setting_top, x+5, y+height-5, width-10, 5);

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

	protected void resize(int width, int height, float heightScale, Stage stage, Table table){
		table.setTransform(true);
		table.setScale(heightScale);

		table.setX(width/2-table.getWidth()*heightScale/2);
		table.setY(height/2-table.getHeight()*heightScale/2);

		stage.addActor(table);
	}
}
