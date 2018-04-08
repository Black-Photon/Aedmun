package com.blackphoton.planetclicker.objectType.table;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Row {
//addRow(scrollTable, false, smallUnit, largeUnit, info, 1, wood);
	int line;
	Image image;
	public Row(int line, Image image) {
		this.line = line;
		this.image = image;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
