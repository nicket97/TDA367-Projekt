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
import model.transformations.core.Layer;
import model.transformations.core.Layers;

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

	/**
	 * Constructor for LayerRow class
	 * @param layer the layer in question
	 */
	public LayerRow(Layer layer) {

		this.layer = layer;
		this.name = layer.getName();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/LayerRow.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Handles the different options in the layer row
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		layerLabel.setText(name);
		// Visiblity
		visibleBox.setSelected(layer.getVisible());
		visibleBox.setOnMouseClicked(e -> {
			layer.changeVisible();
			MainView.canvasUpdate();
		});
		// Settings
		if (!layer.hasSettings()){
			settingsIcon.setId("disabledIcon");
			settingsIcon.setDisable(true);
		}
		settingsIcon.setOnMouseClicked(e -> {
			MainView.mainView.updateLayerSettings(layer);
		});
		// Trashcan
		trashCan.setOnMouseClicked(e -> {
			Layers.remove(layer);
			MainView.canvasUpdate();
			if (Layers.getLayerStack().isEmpty()){
				MainView.mainView.topToFront();
			}
		});
	}
}
