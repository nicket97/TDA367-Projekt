package controllers;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.transformations.Crop;
import model.transformations.core.Layer;
import model.transformations.core.Layers;

/**
 * Handles all input related to the crop function
 */
public class CropView extends AnchorPane implements Initializable {

	@FXML
	AnchorPane canvasPane;

	Canvas imagePane;
	CropView canvasView = this;

	double zoomFactor = 1;

	private Point pressedPoint;
	private Point releasedPoint;
	private int width;
	private int height;

	private Stage primaryStage;

	/**
	 * Constructor for CropView
	 */
	public CropView() {
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
	 * Saves points, draws black rectangle and asks for confirmation
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		primaryStage = MainView.getPrimaryStage();
		this.setPrefWidth(MainView.getCanvas().imagePane.getWidth());
		this.setPrefHeight(MainView.getCanvas().imagePane.getHeight());
		this.setTranslateX(MainView.getCanvas().imagePane.getTranslateX());
		this.setOnMousePressed(e -> {
			this.pressedPoint = new Point((int) e.getX(), (int) e.getY());

		});
		this.setOnMouseReleased(e -> {
			this.releasedPoint = new Point((int) e.getX(), (int) e.getY());
			drawImage(pressedPoint, releasedPoint);

			Alert cropAlert = new Alert(Alert.AlertType.CONFIRMATION);
			cropAlert.initStyle(StageStyle.TRANSPARENT);
			cropAlert.setTitle("Beskärning");
			cropAlert.setHeaderText("Vill du endast behålla denna del av bilden?");

			ButtonType buttonTypeYes = new ButtonType("Ja");
			ButtonType buttonTypeNo = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);

			cropAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
			
			DialogPane dialogPane = cropAlert.getDialogPane();
			dialogPane.getStylesheets().add(
			getClass().getResource("../resources/css/style.css").toExternalForm());

			Optional<ButtonType> result = cropAlert.showAndWait();

			if (result.get() == buttonTypeYes) {
				Layers.addLayer(new Layer(new Crop(getStartPoint(), getEndPoint(), width, height)));
				MainView.canvasView.repaint();
				this.getChildren().clear();

			}
			if (result.get() == buttonTypeNo) {
				cropAlert.close();
				this.getChildren().clear();
			} else {
				cropAlert.close();
				this.getChildren().clear();
			}
			MainView.canvasUpdate();
			this.setVisible(false);
		});
		

	}

	/**
	 * Draws black rectangle over photo 
	 * @param topLeft value of the pressed top left point
	 * @param bottomRight value of the released bottom right point
	 */
	public void drawImage(Point topLeft, Point bottomRight) {
		if (pressedPoint == null) {

		} else {
			this.width = (int) (distanceDragged(pressedPoint, releasedPoint).getX());
			this.height = (int) (distanceDragged(pressedPoint, releasedPoint).getY());
			int posX = (int) pressedPoint.getX();
			int posY = (int) pressedPoint.getY();
			Rectangle r = new Rectangle(posX, posY, width, height);
			posX = (int) (posX * MainView.getCanvas().getZoomFactor());
			posY = (int) (posY * MainView.getCanvas().getZoomFactor());
			this.pressedPoint = new Point(posX, posY);
			this.width = (int) (this.width * MainView.getCanvas().getZoomFactor());
			this.height = (int) (this.height * MainView.getCanvas().getZoomFactor());
			r.setStyle(
					"-fx-opacity: 0.5;");
			this.getChildren().add(r);
		}

	}

	/**
	 * Repaints the picture
	 */
	public void repaint() {

	}

	/**
	 * Calculates the distance between mouse is pressed and released
	 * @param pStart point pressed
	 * @param pStop point released
	 * @return the distance between the two points
	 */
	public Point distanceDragged(Point pStart, Point pStop) {
		Point distanceDiffernce = new Point();
		distanceDiffernce.x = (int) (pStop.getX() - pStart.getX());
		distanceDiffernce.y = (int) (pStop.getY() - pStart.getY());

		return distanceDiffernce;
	}

	/**
	 * Getter for startPoint variable
	 * @return the value of startPoint
	 */
	public Point getStartPoint() {
		return pressedPoint;
	}

	/**
	 * Getter for endPoint variable
	 * @return the value of endPoint
	 */
	public Point getEndPoint() {
		return releasedPoint;
	}
}
