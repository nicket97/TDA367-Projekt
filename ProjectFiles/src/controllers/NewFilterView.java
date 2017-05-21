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

/**
 * Handles all input regarding creation of new filters by the user
 */

public class NewFilterView extends AnchorPane implements Initializable {
	double[][] gridValues = new double[3][3];
	String filterName;

	@FXML
	AnchorPane canvasPane;
	@FXML
	TextField grid00, grid01, grid02, grid10, grid11, grid12, grid20, grid21, grid22, nameInput;
	@FXML
	Button newFilterCancelButton, newFilterSaveButton, newFilterApplyButton;
	@FXML 
  ComboBox<String> newFilterDropDown;

	private Stage stage;

	public NewFilterView(Stage window) {
		stage = window;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/NewFilterView.fxml"));
		System.out.println("new filter view");
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("init newFilter");
		newFilterDropDown.setEditable(true);
		ObservableList<String> options = FXCollections.observableArrayList();
		if(Layers.getBackgroundImage() == null){
			newFilterApplyButton.setDisable(true);
		}
		for (CreatedFilter f : NewFilterHandeler.getFilters()) {
			System.out.println(f.getName());
			options.add(f.getName());
		}
		newFilterDropDown.getItems().addAll(options);

		newFilterDropDown.setOnAction(e -> {

			for (CreatedFilter f : NewFilterHandeler.getFilters()) {
				if (f.getName().equalsIgnoreCase(newFilterDropDown.getValue().toString())) {
					gridValues = f.getKernel();
					grid00.setText("" + gridValues[0][0]);
					grid10.setText("" + gridValues[1][0]);
					grid20.setText("" + gridValues[2][0]);
					grid01.setText("" + gridValues[0][1]);
					grid11.setText("" + gridValues[1][1]);
					grid21.setText("" + gridValues[2][1]);
					grid02.setText("" + gridValues[0][2]);
					grid12.setText("" + gridValues[1][2]);
					grid22.setText("" + gridValues[2][2]);

					nameInput.setText(newFilterDropDown.getValue().toString());
					break;

				}

			}
		});

		newFilterSaveButton.setOnAction(e -> {

			filterName = nameInput.getText();
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
			stage.hide();

			if (newFilterDropDown.getValue() == null && nameInput.getText() != null) {
				nameInput.clear();
			}

		});
		newFilterCancelButton.setOnAction(e -> {
			stage.hide();
		});
		newFilterApplyButton.setOnAction(e -> {
			Layers.addLayer(new Layer(new NewKernel(gridValues, filterName)));
			stage.hide();
		});

	}

}
