package controllers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Layers;
import main.Main;
import model.*;

public class MainView extends AnchorPane implements Initializable {

	public static LayerView layerView;
	static CanvasView canvasView;

	static MiniCanvasView miniCanvasView;
	private Point topLeft = new Point(0, 0);
	private Point bottomRight = new Point(0, 0);
	private static Stage primaryStage;

	@FXML
	TilePane bottomContainer;
	@FXML
	AnchorPane bottomPane, canvasPane, miniCanvas, layerPane;
	@FXML
	AnchorPane menuBar;
	@FXML
	MenuItem openImage, menuClose, menuExport, menuSaveProject, menuOpenProject;
	@FXML
	MenuItem menuGrayScale, menuColorFilter, menuBlackWhite, menuWhitebalance, menuLevels;
	@FXML
	MenuItem menuCrop, menuExposure, menuContrast, menuHReflect, menuVReflect, menuRotateL, menuRotateR;
	@FXML
	MenuItem menuBlur, menuGaussianBlur, menuSharpen, menuTextFilter, menuEdge, menuGrain, menuNewFilter;
	@FXML
	MenuItem menuZoomIn, menuZoomOut, menuUndo, menuResetWindow;
	@FXML
	Button closeButton, miniButton, maxiButton;
	@FXML
	Button exposureUpdate, contrastUpdate, levelsUpdate, grainUpdate, blurUpdate, gBlurUpdate, sharpenUpdate, cfUpdate,
			grayUpdate, bwUpdate, wbUpdate;
	@FXML
	RadioButton yellowButton, orangeButton, blueButton, redButton, pinkButton, purpleButton, turquoiseButton,
			greenButton;
	@FXML
	ToggleGroup colorGroup;
	@FXML
	ColorPicker customColor;
	@FXML
	ChoiceBox filterBox;
	@FXML
	Slider exposureIntensity, contrastThreshold, contrastIntensity, grainDeviation, levelsMin, levelsMax, blurRadius,
			gBlurRadius, sharpenIntensity, sharpenThreshold;
	@FXML
	Slider colorIntensity, bwThreshold, bwIntensity, wbWarm;
	@FXML
	StackPane toolContainer;
	@FXML
	HBox topLevel, adjustLevel, effectLevel, colorLevel, filterLevel;
	@FXML
	HBox exposureLevel, contrastLevel, levelsLevel, grainLevel;
	@FXML
	HBox blurLevel, gBlurLevel, sharpenLevel;
	@FXML
	HBox colorFilterLevel, grayLevel, bwLevel, wbLevel;
	@FXML
	Label adjustIcon, colorIcon, effectIcon, filterIcon;
	@FXML
	Label exposureIcon, exposureBackIcon, contrastIcon, contrastBackIcon, levelsIcon, levelsBackIcon, grainIcon,
			grainBackIcon;
	@FXML
	Label blurIcon, blurBackIcon, gBlurIcon, gBlurBackIcon, sharpenIcon, sharpenBackIcon;
	@FXML
	Label colorFilterIcon, cfBackIcon, grayIcon, grayBackIcon, whiteBalanceIcon, wbBackIcon, bwIcon, bwBackIcon;
	@FXML
	Label aBackIcon, cBackIcon, eBackIcon, fBackIcon;
	@FXML
	Slider slideZoom;
	Layers layerstack = new Layers();

	private static LoadedImage backgroundImage;

	public MainView(Stage pstage) {
		primaryStage = pstage;
		NewFilterHandeler.loadFilters();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/MainView.fxml"));

		System.out.println("mainview");
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		/***
		 * Interface buttons.
		 */
		closeButton.setOnAction(e -> {
			if (NewFilterHandeler.getFilters().size() > 0) {
				NewFilterHandeler.saveFilters();
			}
			if (MainView.getBackgroundImage() != null) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Varning");
				alert.setHeaderText("Vill du spara projektet innan du avslutar?");

				ButtonType buttonTypeYes = new ButtonType("Spara");
				ButtonType buttonTypeNo = new ButtonType("Avsluta", ButtonBar.ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeYes) {
					SaveProject.saveProject();
				}
				if (result.get() == buttonTypeNo) {
					Platform.exit();
				}
			} else {
				Platform.exit();
			}
		});
		miniButton.setOnAction(e -> {
			Main.getPrimaryStage().setIconified(true);
		});
		maxiButton.setOnAction(e -> {
			if (Main.getPrimaryStage().isMaximized()) {
				Main.getPrimaryStage().setMaximized(false);
			} else {
				Main.getPrimaryStage().setMaximized(true);
			}
		});

		openImage.setOnAction(e -> {
			Layers.clear();
			canvasView.setZoomFactor(1);
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(" Bildfiler", "*.png", "*.jpg",
					"*.jpeg");
			fileChooser.getExtensionFilters().add(extFilter);
			;
			// fileChooser.setSelectedExtensionFilter(new
			// ExtensionFilter(".jpg", ".png" , ".jpeg"));
			fileChooser.setTitle("Välj en bild");

			File f = fileChooser.showOpenDialog(new Stage());

			BufferedImage in;
			try {
				if (ImageIO.read(f) != null) {
					in = ImageIO.read(f);
					BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(),
							BufferedImage.TYPE_INT_ARGB);

					Graphics2D g = newImage.createGraphics();
					g.drawImage(in, 0, 0, null);
					g.dispose();

					long time = System.nanoTime();
					LoadedImage ll = new LoadedImage(newImage);
					System.out.println("Open Image" + (double) (System.nanoTime() - time) / 1000000000);
					// System.out.println(canvasView.toString());
					setBackgroundImage(ll);
					canvasView.repaint();
					miniCanvasView.repaint();
				}
			} catch (IOException e1) {
				// On canceled fileopening
			}
			setDisableMenuItems(false);

		});
		menuClose.setOnAction(e -> {
			if (NewFilterHandeler.getFilters().size() > 0) {
				NewFilterHandeler.saveFilters();
			}
			if (MainView.getBackgroundImage() != null) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Varning");
				alert.setHeaderText("Vill du spara projektet innan du avslutar?");

				ButtonType buttonTypeYes = new ButtonType("Spara");
				ButtonType buttonTypeNo = new ButtonType("Avsluta", ButtonBar.ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeYes) {
					SaveProject.saveProject();
				}
				if (result.get() == buttonTypeNo) {
					Platform.exit();
				}

			} else {
				Platform.exit();
			}
			// System.exit(0);
		});
		menuExport.setOnAction(e -> {

			try {

				LoadedImage export = MainView.backgroundImage;
				for (Layer layer : Layers.getLayerStack()) {
					export = layer.getAction().transform(export);
				}
				FileChooser fileChooser = new FileChooser();

				File outputfile = fileChooser.showSaveDialog(new Stage());
				BufferedImage bufferedExport = export.getBufferedImg();
				ImageIO.write(bufferedExport, "png", outputfile);

			} catch (IOException e1) {

			}
		});
		menuSaveProject.setOnAction(e -> {
			SaveProject.saveProject();
		});
		menuOpenProject.setOnAction(e -> {
			OpenProject.openFile();
			setDisableMenuItems(false);
		});

		menuZoomOut.setOnAction(e -> {
			canvasView.setZoomFactor((canvasView.getZoomFactor() * 1.5));
			System.out.println("zooooomOUT  " + canvasView.getZoomFactor());
			canvasView.repaint();
		});

		menuZoomIn.setOnAction(e -> {
			canvasView.setZoomFactor((canvasView.getZoomFactor() * 0.75));
			System.out.println("zooooomIN  " + canvasView.getZoomFactor());
			canvasView.repaint();
		});
		menuUndo.setOnAction(e -> {
			Layers.remove(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1));
			canvasView.repaint();
		});
		slideZoom.setValue(100);
		slideZoom.setOnMouseClicked(e -> {
			System.out.println("zooma " + slideZoom.getValue());
			canvasView.setZoomFactor((Math.pow(2, slideZoom.getValue() / 20 - 5)));
			canvasView.repaint();
		});
		slideZoom.setOnMouseDragOver(e -> {
			System.out.println("zooma " + slideZoom.getValue());

		});

		menuCrop.setOnAction(e -> {
			CropView cropView = new CropView();
			canvasPane.getChildren().add(cropView);
			canvasView.repaint();

		});

		menuNewFilter.setOnAction(e -> {
			Stage window = new Stage();
			AnchorPane pane = new AnchorPane();
			window.initModality(Modality.APPLICATION_MODAL);
			window.initOwner(primaryStage);
			pane.getChildren().add(new NewFilterView(window));
			Parent root = pane;

			Scene s = new Scene(root);

			window.setScene(s);
			window.show();
		});
		menuResetWindow.setOnAction(e -> {
			canvasView.setTopX(0);
			canvasView.setTopY(0);
			canvasView.repaint();

		});

		/***
		 * Connecting menu options and filters.
		 */
		menuClicked(menuHReflect, (new HMirroring()));
		menuClicked(menuVReflect, (new VMirroring()));
		menuClicked(menuRotateL, (new RotateL()));
		menuClicked(menuRotateR, (new RotateR()));
		menuClickedOptions(menuBlur, blurLevel, (new Blur(7)));
		menuClickedOptions(menuGaussianBlur, gBlurLevel, (new GaussianBlur(6)));
		menuClickedOptions(menuSharpen, sharpenLevel, (new Sharpen()));
		menuClickedOptions(menuGrayScale, grayLevel, (new GrayScale()));
		menuClickedOptions(menuColorFilter, colorFilterLevel, (new ColorShift(50, 1, 1)));
		menuClickedOptions(menuContrast, contrastLevel, (new Contrast(100, 1.4)));
		menuClickedOptions(menuWhitebalance, wbLevel, (new WhiteBalance(40)));
		menuClickedOptions(menuLevels, levelsLevel, (new Levels(100, 40)));
		menuClickedOptions(menuBlackWhite, bwLevel, (new BlackAndWhite(123)));
		menuClickedOptions(menuGrain, grainLevel, new Grain(20));
		menuClickedOptions(menuExposure, exposureLevel, (new Exposure(40)));
		menuClicked(menuEdge, (new Edge()));
		menuClicked(menuTextFilter, (new TextFilter()));

		final Delta dragDelta = new Delta();

		menuBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = pstage.getX() - mouseEvent.getScreenX();
				dragDelta.y = pstage.getY() - mouseEvent.getScreenY();
			}
		});
		menuBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				pstage.setX(mouseEvent.getScreenX() + dragDelta.x);
				pstage.setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Point setTopLeftCrop() {
		Point topLeft = new Point();
		canvasView.setOnMouseClicked(e -> {
			topLeft.setLocation(e.getX(), e.getY());
		});
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Beskärning");
		alert.setHeaderText("Välj önskat övre vänstra hörn");
		alert.showAndWait();
		// alert.get
		while (topLeft.getX() == 0 && topLeft.getY() == 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(topLeft.toString());
		return topLeft;
	}

	public Point setBottomRightCrop() {
		Point bottomRight = new Point();
		canvasView.setOnMouseClicked(e -> {
			bottomRight.setLocation(e.getX(), e.getY());
		});
		Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
		alert2.setTitle("Beskärning");
		alert2.setHeaderText("Välj önskat nedre högra hörn");
		alert2.showAndWait();
		while (topLeft.getX() == 0 && topLeft.getY() == 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return bottomRight;
	}

	/***
	 * Creates fadetransitions that will be used in the toolbar.
	 */
	private FadeTransition fadeIn = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeAdjust = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeExposure = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeContrast = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeLevels = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeGrain = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeEffect = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeBlur = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeGBlur = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeSharpen = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeColor = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeColorFilter = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeGray = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeBW = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeWB = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeFilter = new FadeTransition(Duration.millis(150));

	/***
	 * Connects the fadetransition and HBox node.
	 * 
	 * @param name
	 * @param node
	 */
	private void fadeSettings(FadeTransition name, Node node) {
		name.setNode(node);
		name.setFromValue(0.0);
		name.setToValue(1.0);
		name.setCycleCount(1);
		name.setAutoReverse(false);
		return;
	}

	/***
	 * Changes visibility of nodes when navigating the toolbar.
	 * 
	 * @param begin
	 * @param end
	 * @param fade
	 */
	private void mouseClicked(Node begin, Node end, FadeTransition fade) {
		begin.setVisible(false);
		end.toFront();
		end.setVisible(true);
		fade.playFromStart();
		return;
	}

	/***
	 * Adds a filter via the menu option (no settings).
	 * 
	 * @param name
	 * @param layerType
	 */
	private void menuClicked(MenuItem name, Layerable layerType) {
		name.setOnAction(e -> {
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(layerType));
				canvasUpdate();
			}
		});
		return;
	}

	/***
	 * Adds a filter via the menu option and also returns the settings view.
	 * 
	 * @param name
	 * @param level
	 * @param layerType
	 */
	private void menuClickedOptions(MenuItem name, HBox level, Layerable layerType) {
		name.setOnAction(e -> {
			toolContainer.getChildren().get(toolContainer.getChildren().size() - 1).setVisible(false);
			level.toFront();
			level.setVisible(true);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(layerType));
				canvasUpdate();
			}
		});
		return;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setDisableMenuItems(true);

		canvasView = new CanvasView(primaryStage);
		miniCanvasView = new MiniCanvasView();
		layerView = new LayerView();

		canvasPane.getChildren().add(canvasView);
		miniCanvas.getChildren().add(miniCanvasView);
		layerPane.getChildren().add(layerView);

		topLevel.toFront();

		/***
		 * Adding values to the color buttons.
		 */
		yellowButton.setUserData("yellow");
		orangeButton.setUserData("orange");
		blueButton.setUserData("blue");
		redButton.setUserData("red");
		pinkButton.setUserData("pink");
		purpleButton.setUserData("purple");
		turquoiseButton.setUserData("turquoise");
		greenButton.setUserData("green");

		/***
		 * Functionality for adding filters via the toolbar.
		 */
		// Adjustments
		exposureUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
					.setFactor(exposureIntensity.valueProperty().intValue());
			canvasUpdate();
		});
		contrastUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setFactorAndThreshold(
					contrastIntensity.valueProperty().intValue(), contrastThreshold.valueProperty().doubleValue());
			canvasUpdate();
		});
		levelsUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
					.setLevels(levelsMin.valueProperty().intValue(), levelsMax.valueProperty().intValue());
			canvasUpdate();
		});
		grainUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
					.setDeviation(grainDeviation.valueProperty().intValue());
			canvasUpdate();
		});

		// Effects
		blurUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
					.setRadius(blurRadius.valueProperty().intValue());
			canvasUpdate();
		});
		gBlurUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
					.setRadius(gBlurRadius.valueProperty().intValue());
			canvasUpdate();
		});
		sharpenUpdate.setOnAction(e -> {
			// Layers.getLayerStack().get(Layers.getLayerStack().size()-1.setRadiusAndThreshold(
			// sharpenRadius.valueProperty().intValue(),
			// sharpenThreshold.valueProperty().intValue()));
			canvasUpdate();
		});
		// Colors
		cfUpdate.setOnAction(e -> {
			if (colorGroup.getSelectedToggle().equals(null)) {
				Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(customColor.getValue().getRed(),
						customColor.getValue().getGreen(), customColor.getValue().getBlue());
			} else {
				Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
						.setColor((String) colorGroup.getSelectedToggle().getUserData());
			}
			canvasUpdate();
		});
		/**
		 * grayUpdate.setOnAction(e -> { layerstack.addLayer(new Layer(new
		 * Blur((int) blurRadius.valueProperty().intValue()))); canvasUpdate();
		 * });
		 */
		bwUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
					.setThreshold(bwThreshold.valueProperty().intValue());
			canvasUpdate();
		});
		wbUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1)
					.setThreshold(wbWarm.valueProperty().intValue());
			canvasUpdate();
		});
		// Custom filters

		/***
		 * Handles fade transitions on mouseclick for the toolbar.
		 */
		fadeSettings(fadeIn, topLevel);
		fadeSettings(fadeAdjust, adjustLevel);
		fadeSettings(fadeExposure, exposureLevel);
		fadeSettings(fadeContrast, contrastLevel);
		fadeSettings(fadeLevels, levelsLevel);
		fadeSettings(fadeGrain, grainLevel);
		fadeSettings(fadeEffect, effectLevel);
		fadeSettings(fadeBlur, blurLevel);
		fadeSettings(fadeGBlur, gBlurLevel);
		fadeSettings(fadeSharpen, sharpenLevel);
		fadeSettings(fadeColor, colorLevel);
		fadeSettings(fadeColorFilter, colorFilterLevel);
		fadeSettings(fadeGray, grayLevel);
		fadeSettings(fadeBW, bwLevel);
		fadeSettings(fadeWB, wbLevel);
		fadeSettings(fadeFilter, filterLevel);

		/***
		 * Settings for all the different toolbar icons.
		 */
		adjustIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, adjustLevel, fadeAdjust);
		});
		aBackIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, topLevel, fadeIn);
		});
		exposureIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, exposureLevel, fadeExposure);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new Exposure(1)));
				canvasUpdate();
			}
		});
		exposureBackIcon.setOnMouseClicked(e -> {
			mouseClicked(exposureLevel, adjustLevel, fadeAdjust);
		});
		contrastIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, contrastLevel, fadeContrast);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new Contrast(1, 1)));
				canvasUpdate();
			}
		});
		contrastBackIcon.setOnMouseClicked(e -> {
			mouseClicked(contrastLevel, adjustLevel, fadeAdjust);
		});
		levelsIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, levelsLevel, fadeLevels);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new Levels(1, 1)));
				canvasUpdate();
			}
		});
		levelsBackIcon.setOnMouseClicked(e -> {
			mouseClicked(levelsLevel, adjustLevel, fadeAdjust);
		});
		grainIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, grainLevel, fadeGrain);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new Grain(10)));
				canvasUpdate();
			}
		});
		grainBackIcon.setOnMouseClicked(e -> {
			mouseClicked(grainLevel, adjustLevel, fadeAdjust);
		});
		effectIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, effectLevel, fadeEffect);
		});
		eBackIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, topLevel, fadeIn);
		});
		blurIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, blurLevel, fadeBlur);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new Blur(2)));
				canvasUpdate();
			}
		});
		blurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(blurLevel, effectLevel, fadeEffect);
		});
		gBlurIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, gBlurLevel, fadeGBlur);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new GaussianBlur(2)));
				canvasUpdate();
			}
		});
		gBlurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(gBlurLevel, effectLevel, fadeEffect);
		});
		sharpenIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, sharpenLevel, fadeSharpen);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new Sharpen()));
				canvasUpdate();
			}
		});
		sharpenBackIcon.setOnMouseClicked(e -> {
			mouseClicked(sharpenLevel, effectLevel, fadeEffect);
		});
		colorIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, colorLevel, fadeColor);
		});
		colorFilterIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, colorFilterLevel, fadeColorFilter);
			if (backgroundImage != null) {
				layerstack.addLayer(
						new Layer(new ColorShift(0.7019608020782471, 0.7019608020782471, 0.7019608020782471)));
				canvasUpdate();
			}
		});
		cfBackIcon.setOnMouseClicked(e -> {
			mouseClicked(colorFilterLevel, colorLevel, fadeColor);
			colorGroup.selectToggle(null);
		});
		grayIcon.setOnMouseClicked(e -> {
			// mouseClicked(colorLevel, grayLevel, fadeGray);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new GrayScale()));
				canvasUpdate();
			}
		});
		grayBackIcon.setOnMouseClicked(e -> {
			mouseClicked(grayLevel, colorLevel, fadeColor);
		});
		bwIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, bwLevel, fadeBW);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new BlackAndWhite(50)));
				canvasUpdate();
			}
		});
		bwBackIcon.setOnMouseClicked(e -> {
			mouseClicked(bwLevel, colorLevel, fadeColor);
		});
		whiteBalanceIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, wbLevel, fadeWB);
			if (backgroundImage != null) {
				layerstack.addLayer(new Layer(new WhiteBalance(50)));
				canvasUpdate();
			}
		});
		wbBackIcon.setOnMouseClicked(e -> {
			mouseClicked(wbLevel, colorLevel, fadeColor);
		});
		cBackIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, topLevel, fadeIn);
		});
		filterIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, filterLevel, fadeFilter);
		});
		fBackIcon.setOnMouseClicked(e -> {
			mouseClicked(filterLevel, topLevel, fadeIn);
		});
		slideZoom.setValue(50);

	}

	static void canvasUpdate() {
		canvasView.repaint();
		miniCanvasView.repaint();
	}

	/***
	 * Method for disabling menu options and update buttons when no image is
	 * open.
	 * 
	 * @param b
	 */
	private void setDisableMenuItems(boolean b) {
		menuExport.setDisable(b);
		menuSaveProject.setDisable(b);
		menuGrayScale.setDisable(b);
		menuColorFilter.setDisable(b);
		menuBlackWhite.setDisable(b);
		menuWhitebalance.setDisable(b);
		menuLevels.setDisable(b);
		menuCrop.setDisable(b);
		menuExposure.setDisable(b);
		menuContrast.setDisable(b);
		menuGrain.setDisable(b);
		menuHReflect.setDisable(b);
		menuVReflect.setDisable(b);
		menuRotateL.setDisable(b);
		menuRotateR.setDisable(b);
		menuBlur.setDisable(b);
		menuGaussianBlur.setDisable(b);
		menuSharpen.setDisable(b);
		menuTextFilter.setDisable(b);
		menuEdge.setDisable(b);
		menuZoomIn.setDisable(b);
		menuZoomOut.setDisable(b);
		menuUndo.setDisable(b);
		disableToolbarButtons(b);
	}

	private void disableToolbarButtons(boolean b) {
		exposureUpdate.setDisable(b);
		contrastUpdate.setDisable(b);
		levelsUpdate.setDisable(b);
		grainUpdate.setDisable(b);
		blurUpdate.setDisable(b);
		gBlurUpdate.setDisable(b);
		sharpenUpdate.setDisable(b);
		cfUpdate.setDisable(b);
		bwUpdate.setDisable(b);
		wbUpdate.setDisable(b);
		slideZoom.setDisable(b);
	}

	public static LoadedImage getBackgroundImage() {
		return backgroundImage;
	}

	public static void setBackgroundImage(LoadedImage backgroundImage) {
		MainView.backgroundImage = backgroundImage;
	}

	public static CanvasView getCanvas() {
		return canvasView;
	}
}

class Delta {
	double x, y;
}