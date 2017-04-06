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
import main.Layers;
import model.Layer;

public class LayerView extends AnchorPane implements Initializable {

	@FXML
	AnchorPane layerListHolder;
	@FXML
	ListView<LayerRow> layerList;
	
	List<Layer> listOfLayers = new ArrayList<Layer>();
	
	public LayerView() {

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/LayerView.fxml"));
		System.out.println("layerview");
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
		System.out.println("init layers");
		
		listOfLayers.addAll(Layers.getLayerStack());
		
		for(Layer layer : listOfLayers){
			//String[] strings=name.split("\t");
			layerList.getItems().add(new LayerRow(layer));
		}

		layerList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() {
					public void run() {
						layerList.getSelectionModel().select(-1);

					}
				});
			}
		});
	}
	
	
	
}
