package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import main.Layers;
import model.ColorShift;
import model.Layer;
import model.Layerable;
import model.LoadedImage;

public class CanvasView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane canvasPane;
	
	Canvas imagePane;
	
	
	
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
	public void drawImage(LoadedImage img){
		
		/*TestCase f�r red color shift
		 * Layerable k = new ColorShift(100,1,1);
		img = k.transform(img);
		
		*/
		LoadedImage newImage = new LoadedImage(img);
		
		for(Layer layer : Layers.getLayerStack()){
			newImage = layer.getAction().transform(newImage);
		}
		
		imagePane = new Canvas(newImage.width, newImage.heigth);
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		
		for(int i = 0; i < newImage.pxImage.length && i < canvasPane.getWidth(); i++){
			for(int j = 0; j < newImage.pxImage[i].length && j < canvasPane.getHeight(); j++){
				//String hexColor = String.format("#%06X", (0xFFFFFF & img.pxImage[i][j]));
				//System.out.println(img.pxImage[i][j]);
				/*int argb = img.pxImage[i][j];
				int r = (argb>>16)&0xFF;
				int g = (argb>>8)&0xFF;
				int b = (argb>>0)&0xFF;
				gc.setColor(i, j, Color.rgb(r, g, b));*/
			
				gc.setColor(i, j, newImage.pxImage[i][j]);
			}
			//System.out.println(img.pxImage.length-i);
		}
		
				canvasPane.getChildren().clear();
		canvasPane.getChildren().add(imagePane);
		
		System.out.println(canvasPane.getChildren().toString());
	}
	public void repaint(){
		this.drawImage(MainView.getBackgroundImage());
	}
	
	
}
