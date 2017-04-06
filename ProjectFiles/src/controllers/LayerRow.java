package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Layer;

public class LayerRow extends AnchorPane implements Initializable {

	@FXML
	AnchorPane layerList;
	@FXML
	Label layerLabel, trashCan;
	
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
		layerLabel.setText(Layer.class.getName());
		
		trashCan.setOnMouseClicked(e -> {
			LayerView.remove(this);
			//layerList.getParent().remove(this);
		});
	}
}
