package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import model.BlackAndWhite;
import model.LoadedImage;

public class MiniCanvasView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane canvasPane;
	Canvas imagePane;
	
	public MiniCanvasView() {

		
		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/MiniCanvasView.fxml"));
		System.out.println("minicanvasview");
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
		System.out.println("init minicanvas");
		
	}
	public void drawImage(LoadedImage img){
		
		imagePane = new Canvas(img.width, img.heigth);
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		
		for(int i = 0; i < img.pxImage.length && i < canvasPane.getWidth(); i++){
			for(int j = 0; j < img.pxImage[i].length && j < canvasPane.getHeight(); j++){
				gc.setColor(i, j, img.pxImage[i][j]);
			}
		}
		
				canvasPane.getChildren().clear();
		canvasPane.getChildren().add(imagePane);
		System.out.println(canvasPane.getChildren().toString());
	}
	
	
}