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

public class LayerRow extends AnchorPane implements Initializable {

	//ListCell<Layer> 
	@FXML
	AnchorPane layerList;
	@FXML
	Label layerLabel, trashCan;
	@FXML
	AnchorPane layerRowPane;
	
	private String name;
	
	public LayerRow(Layer layerName){
		//this.name = "hej";
		this.name = layerName.getName();
		System.out.println(name);
		//layerLabel.setText("hej");

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
		System.out.println("init layerrow");
		
	}
}
