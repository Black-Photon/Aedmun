package com.blackphoton.planetclicker.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

public abstract class File{
	protected final String fileName;
	protected final FileHandle handle;

	public File(String fileName) {
		this.fileName = fileName;
		handle = Gdx.files.local("data/"+fileName);
	}
	protected String[] nextEntry(String all){
		StringBuilder sb = new StringBuilder();
		For:
		for(char c: all.toCharArray()){
			switch(c) {
				case '\n':
					all=all.substring(sb.length()+1, all.length());
					break For;

				case ';':
					all=all.substring(2, all.length());
					String[] array = {";", all};
					return array;

				default:
					sb.append(c);
			}
		}
		String[] array = {sb.toString(), all};
		return array;
	}


}
