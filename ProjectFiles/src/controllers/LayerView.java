package controllers;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.transformations.core.Layer;
import model.transformations.core.Layers;

/**
 * List of layers added
 */

public class LayerView extends AnchorPane implements Initializable {

	@FXML
	AnchorPane layerListHolder;
	@FXML
	ListView<LayerRow> layerList;

	private List<Layer> listOfLayers = new ArrayList<Layer>();

	/**
	 * Constructor of the LayerView class
	 */
	public LayerView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/LayerView.fxml"));
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
	}

	/**
	 * Updates list  of layers
	 */
	public void update() {
		listOfLayers.clear();
		layerList.getItems().clear();
		for (Layer l : Layers.getLayerStack()) {
			listOfLayers.add(l);
		}

		for (Layer layer : listOfLayers) {
			layerList.getItems().add(new LayerRow(layer));
		}

		layerList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						layerList.getSelectionModel().select(-1);

					}
				});
			}
		});

	}
}