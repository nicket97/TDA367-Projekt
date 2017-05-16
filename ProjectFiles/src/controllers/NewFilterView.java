package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class NewFilterView extends AnchorPane implements Initializable {
	
	
	public NewFilterView() {
		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/NewFilterView.fxml"));
		System.out.println("new filter view");
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
		System.out.println("init newFilter");

		
	}

}
