package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.Layers;
import model.Layer;

/**
 * Custom cell for LayerView
 */

public class LayerRow extends AnchorPane implements Initializable {

	@FXML
	AnchorPane layerList;
	@FXML
	Label layerLabel, settingsIcon, trashCan;
	@FXML
	CheckBox visibleBox;
	@FXML
	AnchorPane layerRowPane;
	
	private String name;
	private Layer layer;
	
	public LayerRow(Layer layer){
		
		this.layer = layer;
		this.name = layer.getName();

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/LayerRow.fxml"));
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
		layerLabel.setText(name);
		//Visiblity
		visibleBox.setSelected(layer.getVisible());
		visibleBox.setOnMouseClicked(e -> { layer.changeVisible();
			MainView.canvasUpdate();});
		//Settings
		settingsIcon.setOnMouseClicked(e -> {
			System.out.println("settings pressed");
			MainView.mainView.updateLayerSettings(layer);
			});
		//Trashcan
		trashCan.setOnMouseClicked(e -> { Layers.remove(layer);
		MainView.canvasUpdate();});
	}
}
