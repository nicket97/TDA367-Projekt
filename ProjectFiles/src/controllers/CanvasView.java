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
		BlackAndWhite w = new BlackAndWhite();
		System.out.println(img.toString());
		img = w.transform(img);
		System.out.println(img.toString());
		imagePane = new Canvas(img.width, img.heigth);
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		
		for(int i = 0; i < img.pxImage.length; i++){
			for(int j = 0; j < img.pxImage[i].length; j++){
				//String hexColor = String.format("#%06X", (0xFFFFFF & img.pxImage[i][j]));
				//System.out.println(img.pxImage[i][j]);
				/*int argb = img.pxImage[i][j];
				int r = (argb>>16)&0xFF;
				int g = (argb>>8)&0xFF;
				int b = (argb>>0)&0xFF;
				gc.setColor(i, j, Color.rgb(r, g, b));*/
				gc.setColor(i, j, img.pxImage[i][j]);
			}
			//System.out.println(img.pxImage.length-i);
		}
		
				canvasPane.getChildren().clear();
		canvasPane.getChildren().add(imagePane);
		
		System.out.println(canvasPane.getChildren().toString());
	}
	
	
}
