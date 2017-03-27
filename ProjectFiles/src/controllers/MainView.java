package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class MainView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane bottomPane;
	@FXML
	TilePane bottomContainer;
	@FXML
	ToolView toolView;

	public MainView() {

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/MainView.fxml"));

		System.out.println("mainview");
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		toolView = new ToolView();
		bottomContainer.getChildren().add(toolView);
		
	}
	
}
