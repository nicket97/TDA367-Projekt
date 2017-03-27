package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class MainView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane bottomPane;
	
	//ToolView toolView;

	public MainView() {

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/MainView.fxml"));

		System.out.println("mainview");
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//bottomPane.getChildren().clear();
		//bottomPane.getChildren().addAll(new ToolView());
	}
	
}
