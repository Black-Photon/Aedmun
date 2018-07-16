package com.blackphoton.planetclicker.file;

import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Absolute;
import com.blackphoton.planetclicker.objectType.table.entries.template.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;
import com.blackphoton.planetclicker.resources.FileDoesNotExistException;

import java.nio.file.NoSuchFileException;

/**
 * Manages the save file
 */
public class SavegameFile extends File {
	public SavegameFile() {
		super("save.txt");
	}

	/**
	 * String of everything read
	 */
	private String everything;

	/**
	 * Saves the game to the save-file
	 */
	public void saveGame(){
		handle.writeString("Population" + ":" + Data.main.POPULATION.getCount() + "\n", false);
		handle.writeString(";\n", true);

		handle.writeString("Era" + ":" + Data.getCurrentEra().getName() + "\n", true);
		handle.writeString(";\n", true);

		for(TableEntry entry: Data.getBuildingTable().getEntries()) {
			handle.writeString(entry.getName() + ":" + entry.getNumberOf() + "\n", true);
		}
		handle.writeString(";\n", true);

		for(TableEntry entry: Data.getFoodTable().getEntries()) {
			handle.writeString(entry.getName() + ":" + entry.getNumberOf() + "\n", true);
		}
		handle.writeString(";\n", true);

		for(TableEntry entry: Data.getResourcesTable().getEntries()) {
			handle.writeString(entry.getName() + ":" + entry.getNumberOf() + "\n", true);
		}
		handle.writeString(";\n", true);

		for(TableEntry entry: Data.getSpecialTable().getEntries()) {
			handle.writeString(entry.getName() + ":" + entry.getNumberOf() + "\n", true);
		}
		handle.writeString(";\n", true);
	}
	/**
	 * Resets data to 0 in save-file and reads the new empty data
	 */
	public void deleteGame(){
		if(!exists()) throw new FileDoesNotExistException();

		handle.delete();

		Data.main.POPULATION.setCount(2);
		for(Era era: Data.getEraList()){
			Data.setCurrentEra(Data.getEraList().get(0));
		}
		for(TableEntry entry: Data.getBuildingTable().getEntries()) {
			resetEntry(entry);
		}
		for(TableEntry entry: Data.getFoodTable().getEntries()) {
			resetEntry(entry);
		}
		for(TableEntry entry: Data.getResourcesTable().getEntries()) {
			resetEntry(entry);
		}
		for(TableEntry entry: Data.getSpecialTable().getEntries()) {
			resetEntry(entry);
		}

		saveGame();
		readGame();
	}
	/**
	 * Reads and interprets the save-file
	 */
	public void readGame(){
		if(!exists()) throw new FileDoesNotExistException();

		everything = handle.readString();

		String saveEntry = readWord();
		if(!verifyEntry(stringEntry(saveEntry), "Population")) return;
		long numberOf = numberEntry(saveEntry);
		Data.main.POPULATION.setCount(numberOf);

		readWord(); //Dismiss ;

		saveEntry = readWord();
		if(!verifyEntry(stringEntry(saveEntry), "Era")) return;
		String eraString = stringEntryRight(saveEntry);
		for(Era era: Data.getEraList()){
			if(era.getName().equals(eraString)){
				Data.setCurrentEra(era);
				break;
			}
		}

		for(TableEntry entry: Data.getBuildingTable().getEntries()) {
			processEntry(entry);
		}
		for(TableEntry entry: Data.getFoodTable().getEntries()) {
			processEntry(entry);
		}
		for(TableEntry entry: Data.getResourcesTable().getEntries()) {
			processEntry(entry);
		}
		for(TableEntry entry: Data.getSpecialTable().getEntries()) {
			processEntry(entry);
		}
	}
	/**
	 * Checks if the file exists
	 */
	public boolean exists() {
		return handle.exists();
	}

	/**
	 * Reads the word using {@link File#nextEntry(String)}, set's the everything string and returns the entry
	 * @return The next entry from the 'everything' variable
	 */
	private String readWord(){
		String[] array = nextEntry(everything);
		everything = array[1];
		return array[0];
	}
	/**
	 * Returns the right side of a string in the form "a:b" as a long variable<br/>
	 * Eg. "Population:1000" returns 1000L
	 * @param entry Entry to return the part of
	 * @return The number to the right of the entry
	 */
	private long numberEntry(String entry){
		return Long.parseLong(stringEntryRight(entry));
	}
	/**
	 * Ensures the entry name is the same as the name read from the save
	 * @param saveEntry Name read
	 * @param name True name of the entry
	 * @return True if both the same
	 */
	private boolean verifyEntry(String saveEntry, String name){
		if(saveEntry==null) return false;
		if(name==null) return false;
		return (saveEntry.equals(name));
	}
	/**
	 * Return all data on the left side of a string in the form "a:b"<br/>
	 * Eg. "Population:1000" returns "Population"
	 * @param entry Entry to get the data
	 * @return a for a string in the form "a:b"
	 */
	private String stringEntry(String entry){
		StringBuilder sb = new StringBuilder();
		for(char c: entry.toCharArray()) {
			if (c == ':') {
				return sb.toString();
			}else sb.append(c);
		}
		return null;
	}
	/**
	 * Return all data on the right side of a string in the form "a:b"<br/>
	 * Eg. "Population:1000" returns "1000"
	 * @param entry Entry to get the data
	 * @return b for a string in the form "a:b"
	 */
	private String stringEntryRight(String entry){
		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for(char c: entry.toCharArray()) {
			if(found){
				sb.append(c);
			}else
			if (c == ':') {
				found = true;
			}
		}
		return sb.toString();
	}
	/**
	 * Reads the next entry from the save, and saves the data to the entry variable
	 * Ignores ";"'s
	 * @param entry Entry to save data to
	 */
	private void processEntry(TableEntry entry) {
		String saveEntry = readWord();
		if (saveEntry.equals(";")) {
			processEntry(entry);
			return;
		}
		if (!verifyEntry(stringEntry(saveEntry), entry.getName())) return;
		long numberOf = numberEntry(saveEntry);
		if(entry instanceof ResourcesEntry){
			if(entry instanceof Absolute){
				((ResourcesEntry) entry).getMaterial().setCount(numberOf);
			}else entry.setNumberOf((int) numberOf);
		}else
			entry.setNumberOf((int) numberOf);
		entry.setNumberLabelText();
	}
	/**
	 * Set's the number of an entry to 0 to reset it
	 * @param entry Entry to set to 0
	 */
	private void resetEntry(TableEntry entry){
		if(entry instanceof ResourcesEntry){
			if(entry instanceof Absolute){
				((ResourcesEntry) entry).getMaterial().setCount(0);
			}else entry.setNumberOf(0);
		}else
			entry.setNumberOf(0);
		entry.setNumberLabelText();
	}
}
