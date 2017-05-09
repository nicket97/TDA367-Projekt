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

public class CanvasView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane canvasPane;
	
	Canvas imagePane;
	
	double zoomFactor = 1;


	
	
	
	public CanvasView() {

		
		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/CanvasView.fxml"));
		System.out.println("canvasview");
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
		System.out.println("init canvas");
		
	}
	public void drawImage(LoadedImage img, double zoomFactor){
		
		LoadedImage newImage = new LoadedImage(img);
		
		for(Layer layer : Layers.getLayerStack()){
			if (layer.getVisible()){
				newImage = layer.getAction().transform(newImage);	
			}
		}
		
		imagePane = new Canvas(newImage.width, newImage.heigth);
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		
		if (zoomFactor >= 1) {
		for(int i = 0; i < newImage.pxImage.length/zoomFactor; i++){
			for(int j = 0; j < newImage.pxImage[i].length/zoomFactor; j++){
				gc.setColor(i, j, newImage.pxImage[(int) (zoomFactor*i)][(int) (zoomFactor*j)]);
			}
		}
		}
		else if (zoomFactor < 1) {
			
			double zoom = 1-zoomFactor;
			double y = 1;
			//System.out.println("testa x =" + x + "Y = " + y);
			for(int i = 0; i < newImage.pxImage.length; i++){
				double x = 1;
				y += zoom;
				for(int j = 0; j < newImage.pxImage[i].length; j++){
					gc.setColor(i, j, newImage.pxImage[(int)(y)][(int) (x += zoom)]);
					//System.out.println((int)(x + zoom) + " and  " + (int) (y));
				}
		}
		}
		
				canvasPane.getChildren().clear();
		canvasPane.getChildren().add(imagePane);
		
		System.out.println(canvasPane.getChildren().toString());}
	
	public void repaint(){
		this.drawImage(MainView.getBackgroundImage(), zoomFactor);
	}
	
	public void setZoomFactor (double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}
		
	public double getZoomFactor () {
		return zoomFactor;
	}
	
	
}
