package controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.glass.ui.Screen;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Layers;
import main.Main;
import model.Crop;
import model.Layer;
import model.LoadedImage;
import model.SaveProject;

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
	private int width;
	private int height;
	
	
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
		this.setOnMousePressed(e ->{
			this.pressedPoint = new Point((int)e.getX(), (int)e.getY());
			
		});
		this.setOnMouseReleased(e -> {
			this.releasedPoint = new Point((int) e.getX(), (int) e.getY());
			drawImage(pressedPoint, releasedPoint);
			
			Alert cropAlert = new Alert(Alert.AlertType.CONFIRMATION);
			cropAlert.setTitle("Beskärning");
			cropAlert.setHeaderText("Vill du endast behålla denna del av bilden?");

			ButtonType buttonTypeYes = new ButtonType("Ja");
			ButtonType buttonTypeNo = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);

			cropAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

			Optional<ButtonType> result = cropAlert.showAndWait();
		
			if (result.get() == buttonTypeYes){
				Layers.addLayer(new Layer( new Crop(getStartPoint(), getEndPoint(), width, height)));
				canvasView.repaint();
				this.getChildren().clear();
				
				
			}
			if(result.get() == buttonTypeNo){
				cropAlert.close();
				this.getChildren().clear();
			}
		else{
		cropAlert.close();
		this.getChildren().clear();
		}
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
			this.width = (int) distanceDragged(pressedPoint, releasedPoint).getX();
			this.height = (int) distanceDragged(pressedPoint, releasedPoint).getY();
			int posX = (int) pressedPoint.getX();
			int posY = (int) pressedPoint.getY();
			System.out.println(posX + " hej " + posY + "       " + width + " X " + height);
			Rectangle r = new Rectangle(posX, posY, width, height);
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
	

	
	
	
