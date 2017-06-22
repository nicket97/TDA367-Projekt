package model.core;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FindMenuHandler {

	ArrayList<String> allMenuItems = new ArrayList<String>();
	//ObservableList<String> allMenuItems = FXCollections.observableArrayList();
	
	public FindMenuHandler() {
		init();
	}
	
	private void init() {
		allMenuItems.add("Beskära");
		allMenuItems.add("Kontrast");
		allMenuItems.add("Färgfilter");
		allMenuItems.add("Gråskala");
	}
	
	public ArrayList<String> getSuggestions(String txt) {
		
		ArrayList<String> suggestions = new ArrayList<String>();
		
		for(int i = 0; i < allMenuItems.size(); i++) {
			String item = allMenuItems.get(i);
			if (item.startsWith(txt)) {
				suggestions.add(item);
			}
		}
		for(int i = 0; i < allMenuItems.size(); i++) {
			String item = allMenuItems.get(i);
			if (item.contains(txt)) {
				if(!suggestions.contains(item)) {
					suggestions.add(item);
				}
			}
		}
		
		return suggestions;
	}
	
}
