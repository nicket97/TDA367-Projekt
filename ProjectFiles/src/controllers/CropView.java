package controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.glass.ui.Screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Layers;
import main.Main;
import model.Layer;
import model.LoadedImage;

public class CropView extends AnchorPane implements Initializable {
	
	@FXML
	AnchorPane canvasPane;
	
	Canvas imagePane;
	CropView canvasView = this;
	
	double zoomFactor = 1;

	private int topX = 0;
	private int topY = 0;
	private double pressedX;
	private double pressedY;
	private double releasedX;
	private double releasedY;
	
	private Point pressedPoint;
	private Point releasedPoint;
	
	
	private Stage primaryStage;
	
	
	
	public CropView() {
		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/CanvasView.fxml"));
		System.out.println("cropview");
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
		primaryStage = Main.getPrimaryStage();
		canvasPane.setOnMousePressed(e ->{
			this.pressedPoint = new Point((int)e.getX(), (int)e.getY());
			
		});
		canvasPane.setOnMouseReleased(e -> {
			this.releasedPoint = new Point((int) e.getX(), (int) e.getY());
			drawImage(pressedPoint, releasedPoint);
			
		});
		
		
	}
	public void drawImage(Point topLeft, Point bottomRight){
		if (pressedPoint == null){
			int width = 0;
			int heigth = 0;
			int posX = 0;
			int posY = 0;
		}
		else {
			System.out.println("drawImagge else is printed");
			int width = (int) distanceDragged(pressedPoint, releasedPoint).getX();
			int heigth = (int) distanceDragged(pressedPoint, releasedPoint).getY();
			int posX = (int) pressedPoint.getX();
			int posY = (int) pressedPoint.getY();
			System.out.println(posX + " hej " + posY + "       " + width + " X " + heigth);
			Rectangle r = new Rectangle(width, heigth, posX, posY);
			r.setStroke(Color.BLACK);
			r.setStrokeWidth(10);
			this.getChildren().add(r);
		}
		
		
		}
	
	public void repaint(){
		
	}
	
	 public Point distanceDragged(Point pStart, Point pStop) {
		Point distanceDiffernce = new Point();
		distanceDiffernce.x = (int) (pStop.getX() - pStart.getX());
		distanceDiffernce.y = (int) (pStop.getY() - pStart.getY());

		return distanceDiffernce;
	}

	public Point getStartPoint() {
		// TODO Auto-generated method stub
		return pressedPoint;
	}

	public Point getEndPoint() {
		// TODO Auto-generated method stub
		return releasedPoint;
	}

	
	
		
}
	

	
	
	

