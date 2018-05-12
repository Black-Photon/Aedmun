package com.blackphoton.planetclicker.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

/**
 * Class for managing files
 */
public abstract class File{
	protected final String fileName;
	protected final FileHandle handle;

	public File(String fileName) {
		this.fileName = fileName;
		handle = Gdx.files.local("data/"+fileName);
	}

	/**
	 * Returns the next entry in a string, and the remaining string
	 * Takes a new line to represent the end of that entry <br/>
	 * Takes a ';' on it's own to represent something to delete<br/>
	 *
	 * Eg.<br/>
	 * {@code String all = "Hello\nWorld\nBanana\n;\nLife\n";}<br/>
	 * {@code String[] array;}<br/>
	 * {@code array = nextEntry(all); //["Hello","World\nBanana\n;\nLife\n"]}<br/>
	 * {@code all = array[1];}<br/>
	 * {@code array = nextEntry(all); //["World","Banana\n;\nLife\n"]}<br/>
	 * {@code all = array[1];}<br/>
	 * {@code array = nextEntry(all); //["Banana",";\nLife\n"]}<br/>
	 * {@code all = array[1];}<br/>
	 * {@code array = nextEntry(all); //[";","Life\n"]}<br/>
	 * {@code all = array[1];}<br/>
	 * {@code array = nextEntry(all); //["Life",""]}<br/>
	 *
	 * @param all String to find the next entry of
	 * @return A String[] in the format {entry, original_string-entry}
	 */
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
