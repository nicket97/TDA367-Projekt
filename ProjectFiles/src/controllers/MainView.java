package controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.Layers;
import model.LoadedImage;

public class MainView extends AnchorPane implements Initializable {
	
	@FXML
	TilePane bottomContainer;
	@FXML
	AnchorPane bottomPane, canvasPane;
	@FXML
	ToolView toolView;
	CanvasView canvasView;
	@FXML
	MenuItem openImage;
	@FXML
	MenuItem menuClose;
	
	Layers layerstack = new Layers();

	public MainView() {

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/MainView.fxml"));

		System.out.println("mainview");
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		openImage.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setSelectedExtensionFilter(new ExtensionFilter("jpg", "png" , "jpeg"));
			fileChooser.setTitle("Open a Image");
			File f = fileChooser.showOpenDialog(new Stage());
			BufferedImage in;
			try {
				in = ImageIO.read(f);
				BufferedImage newImage = new BufferedImage(
					    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

					Graphics2D g = newImage.createGraphics();
					g.drawImage(in, 0, 0, null);
					g.dispose();
					
					canvasView.drawImage(new LoadedImage (newImage));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			
		});
		menuClose.setOnAction(e ->{
			System.exit(0);
		});
		
}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		toolView = new ToolView();
		bottomContainer.getChildren().add(toolView);
		canvasView = new CanvasView();
		canvasPane.getChildren().add(canvasView);
	
	}
	
}
