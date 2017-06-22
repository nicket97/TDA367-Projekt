package model.core;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

public class FindMenuHandler {

	ArrayList<MenuItem> allMenuItems = new ArrayList<MenuItem>();
	//ObservableList<String> allMenuItems = FXCollections.observableArrayList();
	
	public FindMenuHandler(ArrayList<MenuItem> menuItems) {
		allMenuItems = menuItems;
	}
	
	public ArrayList<MenuItem> getSuggestions(String txt) {
		
		ArrayList<MenuItem> suggestions = new ArrayList<MenuItem>();
		
		for(int i = 0; i < allMenuItems.size(); i++) {
			MenuItem item = allMenuItems.get(i);
			if (item.getText().toUpperCase().startsWith(txt.toUpperCase())) {
				System.out.println("ppp111 = "+item.getText());
				suggestions.add(item);
			}
		}
		for(int i = 0; i < allMenuItems.size(); i++) {
			MenuItem item = allMenuItems.get(i);
			if (item.getText().toUpperCase().contains(txt.toUpperCase())) {
				System.out.println("ppp = "+item.getText());
				System.out.println("ppp = "+suggestions.contains(item));
				if(!(suggestions.contains(item))) {
					suggestions.add(item);
				}
			}
		}
		System.out.println("ppp size = "+suggestions.size());
		return suggestions;
	}
	
}
