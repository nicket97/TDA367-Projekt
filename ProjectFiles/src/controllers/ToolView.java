package controllers;

import javafx.fxml.FXMLLoader;

public class ToolView {
	
	public ToolView() {

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/ToolView.fxml"));

		System.out.println("toolview");
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

	}
}
