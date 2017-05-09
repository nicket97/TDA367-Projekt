package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import main.Layers;
import model.Layer;

public class LayerRow extends ListCell<Layer> implements Initializable {

	@FXML
	AnchorPane layerList;
	@FXML
	Label layerLabel, trashCan;
	@FXML
	AnchorPane layerRowPane;
	
	private Layer name;
	
	public LayerRow(Layer name){
		
		this.name = name;

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/LayerRow.fxml"));
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
		System.out.println("init layerrow");
		
	}
	@Override
	 protected void updateItem(Layer item, boolean empty){
        super.updateItem(item, empty);
        layerLabel.setText(item.getName());
        trashCan.setOnMouseClicked(e-> {
        	Layers.remove(item);
        });
        setGraphic(layerRowPane);

	}
}
