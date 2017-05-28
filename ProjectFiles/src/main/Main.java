package main;

import controllers.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

import Project.SaveProject;

public class Main extends Application {



	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = new MainView(primaryStage);

		Scene scene = new Scene(root, 1280, 720);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle("Layers");
		primaryStage.getIcons().add(new Image("resources/icons/favicon.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	/**
	 * Calls when the the application ends.
	 * 
	 * @throws Exception if somethings wrong
	 */
	@Override
	public void stop() throws Exception {

	}
}