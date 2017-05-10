package controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

	int topX = 0;
	int topY = 0;
	double pressedX;
	double pressedY;
	double releasedX;
	double releasedY;
	
	
	
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
		canvasPane.setOnMousePressed(e ->{
			System.out.println("Klicka X = " +  e.getX() + " Y = " + e.getY());
			this.pressedX = e.getX();
			this.pressedY = e.getY();
		
		});
		canvasPane.setOnMouseReleased(e -> {
			System.out.println("Släpp X = " +  e.getX() + " Y = " + e.getY());
			this.releasedX = e.getX();
			this.releasedY = e.getY();
			moveCanvas(distanceDragged());
		});
		
		
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
			int screenX = 0;
		for(int i = topX; i < newImage.pxImage.length/zoomFactor; i++){
			int screenY = 0;
			for(int j = topY; j < newImage.pxImage[i].length/zoomFactor; j++){
				gc.setColor(screenX, screenY, newImage.pxImage[(int) (zoomFactor*i)][(int) (zoomFactor*j)]);
				screenY++;
			}
			screenX++;
		}
		}
		else if (zoomFactor < 1) {
			
			double zoom = zoomFactor;
			System.out.println(zoom);
			double y = 1;
			//System.out.println("testa x =" + x + "Y = " + y);
			for(int i = topX; i < newImage.pxImage.length; i++){
				double x = 1;
				y += zoom;
				for(int j = topY; j < newImage.pxImage[i].length; j++){
					gc.setColor(i, j, newImage.pxImage[(int) Math.floor(y)][(int)Math.floor( (x += zoom))]);
					//System.out.println((x) + " and  " + (y));
				}
		}
		}
		
				canvasPane.getChildren().clear();
		canvasPane.getChildren().add(imagePane);
		
		//System.out.println(canvasPane.getChildren().toString());
		}
	
	public void repaint(){
		this.drawImage(MainView.getBackgroundImage(), zoomFactor);
	}
	
	public void setZoomFactor (double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}
		
	public double getZoomFactor () {
		return zoomFactor;
	}
	
	public Point distanceDragged() {
		Point distanceDiffernce = new Point();
		distanceDiffernce.x = (int) (releasedX - pressedX);
		distanceDiffernce.y = (int) (releasedY - pressedY);
		System.out.println("Distance dragged method run" + distanceDiffernce.x);
		return distanceDiffernce;
	}
	
	public int getTopX(){
		return topX;
	}
	
	public int getTopY(){
		return topY;
	}
	
	public void moveCanvas(Point distanceDifference){
		this.topX = getTopX() + distanceDifference.x;
		this.topY = getTopY() + distanceDifference.y;
		if(topX > 0 && topY > 0) {
			repaint();
		}
		else if (topX < 0) {
			this.topX = 0;
		}
		else if (topY < 0){
			this.topY = 0;
		}
		else if (topX < 0 && topY < 0) {
			this.topX = 0;
			this.topY = 0;
		}
	}
	

	
	
	
}
