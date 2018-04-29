package com.blackphoton.planetclicker.file;

import com.blackphoton.planetclicker.core.Data;
import com.blackphoton.planetclicker.objectType.Era;
import com.blackphoton.planetclicker.objectType.table.entries.resources.Absolute;
import com.blackphoton.planetclicker.objectType.table.entries.template.ResourcesEntry;
import com.blackphoton.planetclicker.objectType.table.entries.template.TableEntry;

public class SavegameFile extends File {
	public SavegameFile() {
		super("save.txt");
	}

	private String everything;

	public void saveGame(){
		handle.writeString("Population" + ":" + Data.main.getPopulationCount() + "\n", false);
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
	public void readGame(){
		if(!handle.exists())return;

		everything = handle.readString();

		String saveEntry = readWord();
		if(!verifyEntry(stringEntry(saveEntry), "Population")) return;
		long numberOf = numberEntry(saveEntry);
		Data.main.setPopulationCount(numberOf);

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
	public void deleteGame(){
		handle.delete();

		Data.main.setPopulationCount(2);
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

	private String readWord(){
		String[] array = nextEntry(everything);
		everything = array[1];
		return array[0];
	}
	private long numberEntry(String entry){
		return Long.parseLong(stringEntryRight(entry));
	}
	private boolean verifyEntry(String saveEntry, String name){
		if(saveEntry==null) return false;
		if(name==null) return false;
		return (saveEntry.equals(name));
	}
	private String stringEntry(String entry){
		StringBuilder sb = new StringBuilder();
		for(char c: entry.toCharArray()) {
			if (c == ':') {
				return sb.toString();
			}else sb.append(c);
		}
		return null;
	}
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
