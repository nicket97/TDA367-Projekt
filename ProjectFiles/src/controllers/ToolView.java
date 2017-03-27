package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class ToolView extends AnchorPane implements Initializable {
	
	@FXML
	StackPane toolContainer;
	@FXML
	Label adjustIcon, colorIcon, effectIcon, filterIcon;
	
	public ToolView() {

		
		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/ToolView.fxml"));
		System.out.println("toolview");
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
		//toolContainer.getChildren().addAll(adjustIcon, colorIcon, effectIcon, filterIcon);
		System.out.println("init");
		
	}
	
	
}
