package controllers;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.core.LoadedImage;
import model.transformations.core.Layer;
import model.transformations.core.Layers;

/**
 * All input related to the canvas
 */
public class CanvasView extends AnchorPane implements Initializable {

	@FXML
	AnchorPane canvasPane;

	public Canvas imagePane;
	CanvasView canvasView = this;

	double zoomFactor = 1;

	private int topX = 0;
	private int topY = 0;
	private double pressedX;
	private double pressedY;
	private double releasedX;
	private double releasedY;
	private Stage primaryStage;

	/**
	 * Constructor of CanvasView class
	 * @param pStage primary stage
	 */
	public CanvasView(Stage pStage) {

		primaryStage = pStage;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/CanvasView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}


	/**
	 * Handles mouse clicks on canvas and sets canvas size
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setMouse();
		this.setPrefSize(2000, 2000);

	}

	/**
	 * Draws the image centered on the canvas, with right zoom
	 * @param img the photo being drawn
	 * @param zoomFactor how much of a zoom that the photo should be drawn with
	 */
	public void drawImage(LoadedImage img, double zoomFactor) {
		LoadedImage newImage = new LoadedImage(img);

		for (Layer layer : Layers.getLayerStack()) {
			if (layer.getVisible()) {
				newImage = layer.getAction().transform(newImage);
			}
		}
		LoadedImage newsImage = newImage;

		imagePane = new Canvas(newImage.getWidth() / zoomFactor, newImage.getHeigth() / zoomFactor);
		imagePane.setTranslateX((primaryStage.getWidth() - 240 - newImage.getWidth() / zoomFactor) / 2);
		PixelWriter gc = imagePane.getGraphicsContext2D().getPixelWriter();
		// Zoom Out

		if (zoomFactor >= 1) {
			int screenX = 0;
			for (int i = topX; i < this.getWidth(); i++) {
				int screenY = 0;
				for (int j = topY; j < this.getHeight(); j++) {
					if ((zoomFactor * i) >= newImage.getpxImage().length
							|| (int) (zoomFactor * j) >= newImage.getpxImage()[1].length || (zoomFactor * i) <= 0
							|| (int) (zoomFactor * j) <= 0)
						;
					else {
						gc.setColor(screenX, screenY,
								newImage.getpxImage()[(int) (zoomFactor * i)][(int) (zoomFactor * j)]);
					}
					screenY++;
				}
				screenX++;
			}
		}
		// zoom in
		else if (zoomFactor < 1) {
			int screenX = 0;
			double zoom = zoomFactor;
			double y = topX;
			for (int i = topX; i < this.getWidth(); i++) {
				double x = topY;
				int screenY = 0;
				y += zoom;
				screenX++;
				for (int j = topY; j < this.getHeight(); j++) {
					x += zoom;
					screenY++;
					if (((int) y >= newImage.getpxImage().length)
							|| ((int) (x) >= newImage.getpxImage()[1].length || (x) <= 0 || (int) (y) <= 0)) {
					} else {
						gc.setColor(screenX, screenY,
								newImage.getpxImage()[(int) Math.floor(y)][(int) Math.floor((x))]);

					}
				}
			}
		}

		canvasPane.getChildren().clear();
		canvasPane.getChildren().add(imagePane);

		// System.out.println(canvasPane.getChildren().toString());
	}

	/**
	 * Repaints picture with different zoom factor
	 */
	public void repaint() {
		this.drawImage(Layers.getBackgroundImage(), zoomFactor);
	}

	/**
	 * Setter for zoomFactor variable
	 * @param zoomFactor value for how much zoom
	 */
	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	/**
	 * Getter for zoomFactor variable
	 * @return the value of zoomFactor
	 */
	public double getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * Method that calculates the distance of mouse dragged
	 * @return distance from where mouse is pressed and released
	 */
	public Point distanceDragged() {
		Point distanceDiffernce = new Point();
		distanceDiffernce.x = (int) (releasedX - pressedX) * (-1);
		distanceDiffernce.y = (int) (releasedY - pressedY) * (-1);
		return distanceDiffernce;
	}

	/**
	 * Getter for topX variable
	 * @return value of topX
	 */
	public int getTopX() {
		return topX;
	}

	/**
	 * Getter for topY variable
	 * @return value of topY
	 */
	public int getTopY() {
		return topY;
	}

	/**
	 * Sets new value for topX and topY variables
	 * @param distanceDifference how much the picture should be moved
	 */
	public void moveCanvas(Point distanceDifference) {
		this.topX = getTopX() + distanceDifference.x;
		this.topY = getTopY() + distanceDifference.y;
		repaint();

	}
	public void setMouse(){
		canvasPane.setOnMousePressed(e -> {
			this.pressedX = e.getX();
			this.pressedY = e.getY();

		});
		canvasPane.setOnMouseReleased(e -> {
			this.releasedX = e.getX();
			this.releasedY = e.getY();
			if(imagePane != null){
				if (imagePane.getWidth() < primaryStage.getWidth() - 240) {
				} 
				else {
					moveCanvas(distanceDragged());
				}
			}

		});
	}
	public void resetMouse(){
		canvasPane.setOnMousePressed(null);
		canvasPane.setOnMouseReleased(null);
	}

	/**
	 * Setter for topX variable
	 * @param i new topX value
	 */
	public void setTopX(int i) {
		topX = i;
	}

	/**
	 * Setter for topY variable
	 * @param i new topY value
	 */
	public void setTopY(int i) {
		topY = i;

	}
}
