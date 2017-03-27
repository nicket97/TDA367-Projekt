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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class ToolView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane bottomPane, bottomContainer;
	@FXML
	StackPane toolContainer;
	@FXML
	HBox topLevel, adjustLevel, effectLevel, colorLevel, filterLevel;
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
		System.out.println("init");
		
		adjustIcon.setOnMouseClicked(e -> {
			topLevel.setVisible(false);
			adjustLevel.setVisible(true);
		});
		colorIcon.setOnMouseClicked(e -> {
			topLevel.setVisible(false);
			colorLevel.setVisible(true);
		});
		effectIcon.setOnMouseClicked(e -> {
			topLevel.setVisible(false);
			effectLevel.setVisible(true);
		});
		filterIcon.setOnMouseClicked(e -> {
			topLevel.setVisible(false);
			filterLevel.setVisible(true);
		});
	}
	
	
}
