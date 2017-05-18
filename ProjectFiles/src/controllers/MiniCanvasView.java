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

/**
 * Takes care of everything related to the small overview of the canvas on the right side
 */

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
		
		double zoomFactor = 1;
		if(img.getWidth()/180 >= img.getHeigth()/135){
			zoomFactor = img.getWidth()/180;
		}
		else{
			zoomFactor = img.getHeigth()/135;
		}
		imagePane = new Canvas(img.getWidth() / zoomFactor, 135);
		imagePane.setTranslateX((180 - img.getWidth()/zoomFactor) /2 );
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		
		int screenX = 0;
		for(int i = 0; i < 180 ; i++){
			int screenY = 0;
			for(int j = 0; j < 135; j++){
				if((zoomFactor*i) >= newImage.getpxImage().length || (int) (zoomFactor*j) >= newImage.getpxImage()[1].length ||
						(zoomFactor*i) <= 0 || (int) (zoomFactor*j) <= 0);
				else{
				gc.setColor(screenX, screenY, newImage.getpxImage()[(int) (zoomFactor*i)][(int) (zoomFactor*j)]);
				}
				screenY++;
			}
			screenX++;
			//System.out.println("screeny" + screenY);
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
