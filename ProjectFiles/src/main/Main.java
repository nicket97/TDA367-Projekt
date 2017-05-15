package main;

import controllers.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.SaveProject;

import java.util.Optional;

public class Main extends Application {
	
	public static Stage primaryStage;
	
	public static void main(String[] args) {

		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		Parent root = new MainView(primaryStage);
		
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("TO BE DECIDED");
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        System.out.println();
}
	public static Stage getPrimaryStage(){
		return primaryStage;
	}

	/**
	 * Calls when the the application ends.
	 * @throws Exception
	 */
	@Override
	public void stop() throws Exception {
		if (MainView.getBackgroundImage() != null) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Varning");
			alert.setHeaderText("Vill du spara projektet innan du avslutar?");

			ButtonType buttonTypeYes = new ButtonType("Spara");
			ButtonType buttonTypeNo = new ButtonType("Avsluta", ButtonBar.ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeYes){
				SaveProject.saveProject();
			}

		}
	}
}