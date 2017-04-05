package controllers;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class LayerView extends AnchorPane implements Initializable {

	@FXML
	AnchorPane layerListHolder;
	@FXML
	
	List<String> listOfLayers = new ArrayList<String>();
	
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
		
		listOfLayers.add("Layer 1");
		listOfLayers.add("Layer 2");
		
		for(String name : listOfLayers){
			//String[] strings=name.split("\t");
			//layerList.getItems().add(new LayerRows(name));
		}

		
	}
	
	
}
