package main;

import controllers.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	public static void main(String[] args) {

		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MainView.fxml"));
	    
		Parent root = new MainView();
		
        Scene scene = new Scene(root, 1280, 720);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("TO BE DECIDED");
        primaryStage.setScene(scene);
        primaryStage.show();

}
}