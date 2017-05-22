package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.core.CreatedFilter;
import model.core.NewFilterHandeler;
import model.transformations.NewKernel;
import model.transformations.core.Layer;
import model.transformations.core.Layers;

public class AboutView extends AnchorPane implements Initializable {
	
	@FXML
	Button aboutClose;
	
	private Stage stage;

	public AboutView(Stage window) {
		this.stage = window;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/AboutView.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		aboutClose.setOnAction(e -> {
			stage.hide();
		});		
	
	}

}
