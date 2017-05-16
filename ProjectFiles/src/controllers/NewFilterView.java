package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.CreatedFilter;
import model.Layer;
import model.NewFilterHandeler;

public class NewFilterView extends AnchorPane implements Initializable {
	
	@FXML AnchorPane canvasPane;
	@FXML TextField grid00, grid01, grid02, grid10, grid11, grid12, grid20, grid21, grid22, nameInput;
	@FXML Button newFilterCancelButton, newFilterSaveButton;
	
	
	
	
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
		double[][] gridValues = new double[3][3];
		newFilterSaveButton.setOnAction( e->{
			
			String filterName = nameInput.getText();
			gridValues[0][0] = Double.parseDouble(grid00.getText());
			gridValues[0][1] = Double.parseDouble(grid01.getText());
			gridValues[0][2] = Double.parseDouble(grid02.getText());
			gridValues[1][0] = Double.parseDouble(grid10.getText());
			gridValues[1][1] = Double.parseDouble(grid11.getText());
			gridValues[1][2] = Double.parseDouble(grid12.getText());
			gridValues[2][0] = Double.parseDouble(grid20.getText());
			gridValues[2][1] = Double.parseDouble(grid21.getText());
			gridValues[2][2] = Double.parseDouble(grid22.getText());
			
			NewFilterHandeler.addFilter(new CreatedFilter(filterName, gridValues));
			
			System.out.println("DONE!");

		});
		
	}
	

	

}
