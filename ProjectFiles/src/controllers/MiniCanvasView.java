package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import main.Layers;
import model.Layer;
import model.LoadedImage;

public class MiniCanvasView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane canvasPane, imgPane;
	
	Canvas imagePane;
	MiniCanvasView canvasView = this;
	
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
		LoadedImage newImage = new LoadedImage(img);
		
		for(Layer layer : Layers.getLayerStack()){
			if (layer.getVisible()){
				newImage = layer.getAction().transform(newImage);	
			}
		}
		imagePane = new Canvas(180, 135);
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		
		for(int i = 0; i < 180 && i < canvasPane.getWidth(); i++){
			 			for(int j = 0; j < 180 && j < canvasPane.getHeight(); j++){
			  				
			 				gc.setColor(i, j, img.getpxImage()[i][j]);
			 			}
			 		}
			 					 	
		
		System.out.println("minicanvasView");
		
		canvasPane.getChildren().clear();
		canvasPane.getChildren().add(imagePane);
		
		System.out.println(canvasPane.getChildren().toString());
		}
	
	public void repaint(){
		this.drawImage(MainView.getBackgroundImage());
	}
	
}
