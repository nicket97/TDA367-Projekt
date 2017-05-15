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
	CanvasView canvasView = this;
	
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
		this.setPrefSize(2000,2000);
		
		
	}
	public void drawImage(LoadedImage img, double zoomFactor){
		long time = System.nanoTime();
		LoadedImage newImage = new LoadedImage(img);
		
		for(Layer layer : Layers.getLayerStack()){
			if (layer.getVisible()){
				newImage = layer.getAction().transform(newImage);	
			}
		}
		LoadedImage newsImage = newImage;
		imagePane = new Canvas(newImage.getWidth(), newImage.getHeigth());
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		//Zoom Out
		/*Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run(){
            	if (zoomFactor >= 1) {
        			int screenX = 0;
        		for(int i = topX+1; i < canvasView.getWidth() ; i+=2){
        			int screenY = 0;
        			for(int j = topY; j < canvasView.getHeight(); j++){
        				if((zoomFactor*i) >= newsImage.getpxImage().length || (int) (zoomFactor*j) >= newsImage.getpxImage()[1].length)continue;
        				gc.setColor(screenX, screenY, newsImage.getpxImage()[(int) (zoomFactor*i)][(int) (zoomFactor*j)]);
        				screenY++;
        			}
        			screenX++;
        		}
        		}
        		//zoom in
        		else if (zoomFactor < 1) {
        			int screenX = 0;
        			double zoom = zoomFactor;
        			System.out.println(zoom);
        			double y = topX;
        			//System.out.println("testa x =" + x + "Y = " + y);
        			for(int i = topX+1; i < canvasView.getWidth()-1; i+=2){
        				double x = topY;
        				int screenY = 0;
        				y += zoom;
        				for(int j = topY; j < canvasView.getHeight(); j++){
        					if(((int) Math.floor(y) >= newsImage.getpxImage().length) || ((int)Math.floor( (x + zoom)) >= newsImage.getpxImage()[1].length))continue;
        					gc.setColor(screenX, screenY, newsImage.getpxImage()[(int) Math.floor(y)][(int)Math.floor( (x += zoom))]);
        					//System.out.println((x) + " and  " + (y));
        					screenY++;
        				}
        				screenX++;
        		}
            }
            }
        });
		Thread thread2 = new Thread(new Runnable() {
			
            @Override
            public void run(){
            	if (zoomFactor >= 1) {
        			int screenX = 0;
        		for(int i = topX; i < canvasView.getWidth() ; i+=2){
        			int screenY = 0;
        			for(int j = topY; j < canvasView.getHeight(); j++){
        				if((zoomFactor*i) >= newsImage.getpxImage().length || (int) (zoomFactor*j) >= newsImage.getpxImage()[1].length)continue;
        				gc.setColor(screenX, screenY, newsImage.getpxImage()[(int) (zoomFactor*i)][(int) (zoomFactor*j)]);
        				screenY++;
        			}
        			screenX++;
        		}
        		}
        		//zoom in
        		else if (zoomFactor < 1) {
        			int screenX = 0;
        			double zoom = zoomFactor;
        			System.out.println(zoom);
        			double y = topX;
        			//System.out.println("testa x =" + x + "Y = " + y);
        			for(int i = topX; i < canvasView.getWidth(); i+=2){
        				double x = topY;
        				int screenY = 0;
        				y += zoom;
        				for(int j = topY; j < canvasView.getHeight(); j++){
        					if(((int) Math.floor(y) >= newsImage.getpxImage().length) || ((int)Math.floor( (x + zoom)) >= newsImage.getpxImage()[1].length))continue;
        					gc.setColor(screenX, screenY, newsImage.getpxImage()[(int) Math.floor(y)][(int)Math.floor( (x += zoom))]);
        					//System.out.println((x) + " and  " + (y));
        					screenY++;
        				}
        				screenX++;
        		}
            }
            }
		});
		thread1.start();
		thread2.start();
		*/
		
		if (zoomFactor >= 1) {
			int screenX = 0;
		for(int i = topX; i < this.getWidth() ; i++){
			int screenY = 0;
			for(int j = topY; j < this.getHeight(); j++){
				if((zoomFactor*i) >= newImage.getpxImage().length || (int) (zoomFactor*j) >= newImage.getpxImage()[1].length)break;
				gc.setColor(screenX, screenY, newImage.getpxImage()[(int) (zoomFactor*i)][(int) (zoomFactor*j)]);
				screenY++;
			}
			screenX++;
			//System.out.println("screeny" + screenY);
		}
		}
		//zoom in
		else if (zoomFactor < 1) {
			int screenX = 0;
			double zoom = zoomFactor;
			System.out.println(zoom);
			double y = topX;
			System.out.println("testa x =" + this.getHeight() + "Y = " + this.getWidth());
			for(int i = topX; i < this.getWidth(); i++){
				double x = topY;
				int screenY = 0;
				y += zoom;
				screenX++;
				for(int j = topY; j < this.getHeight(); j++){
					 x += zoom;
					 screenY++;
					if(((int) y >= newImage.getpxImage().length ) || ((int)(x) >= newImage.getpxImage()[1].length)){
						//System.out.println(((int) Math.floor(y) >= newImage.getpxImage().length) + " " + ((int)Math.floor( (x)) >= newImage.getpxImage()[1].length));
						
					}
					else{
					gc.setColor(screenX, screenY, newImage.getpxImage()[(int) Math.floor(y)][(int)Math.floor( (x))]);
					
					}
					//System.out.println(screenX + "hej " + screenY);
					//System.out.println((x) + " and  " + (y));
					
				}
				
				
		}
		}
		System.out.println("canvasView" +  (double)(System.nanoTime() - time)/1000000000);
		
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
		distanceDiffernce.x = (int) (releasedX - pressedX)*(-1);
		distanceDiffernce.y = (int) (releasedY - pressedY)*(-1);
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
		
			
		if (topX < 0) {
			this.topX = 0;
		}
		if (topY < 0){
			this.topY = 0;
		}
		repaint();
		
	}
	

	
	
	
}
