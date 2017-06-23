package model.core;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

/**
 * Handle the search function under 'Hjälp' in menu.
 * @author anton
 *
 */
public class FindMenuHandler {

	ArrayList<MenuItem> allMenuItems = new ArrayList<MenuItem>();
	
	public FindMenuHandler(ArrayList<MenuItem> menuItems) {
		allMenuItems = menuItems;
	}
	
	public ArrayList<MenuItem> getSuggestions(String txt) {
		
		ArrayList<MenuItem> suggestions = new ArrayList<MenuItem>();
		
		for(int i = 0; i < allMenuItems.size(); i++) {
			MenuItem item = allMenuItems.get(i);
			if (item.getText().toUpperCase().startsWith(txt.toUpperCase())) {
				if (!item.isDisable() ) {
					suggestions.add(item);
				}
			}
		}
		for(int i = 0; i < allMenuItems.size(); i++) {
			MenuItem item = allMenuItems.get(i);
			if (item.getText().toUpperCase().contains(txt.toUpperCase())) {
				if(!(suggestions.contains(item))) {
					if (!item.isDisable()) {
						suggestions.add(item);
					}
				}
			}
		}
		return suggestions;
	}
	
}
