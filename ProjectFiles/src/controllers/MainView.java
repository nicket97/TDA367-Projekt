package controllers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.awt.GraphicsEnvironment;

import javax.imageio.ImageIO;

import Project.OpenProject;
import Project.SaveProject;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Main;
import model.core.CreatedFilter;
import model.core.Layerable;
import model.core.LoadedImage;
import model.core.NewFilterHandeler;
import model.transformations.*;
import model.transformations.core.Layer;
import model.transformations.core.Layers;

/**
 * Main controller, distributes tasks
 */

public class MainView extends AnchorPane implements Initializable {

	static MainView mainView;
	public static LayerView layerView;
	static CanvasView canvasView;
	static MiniCanvasView miniCanvasView;
	private Point topLeft = new Point(0, 0);
	private Point bottomRight = new Point(0, 0);
	private static Stage primaryStage;
	/**
	 * Indicates if the project has changed.
	 */
	private static boolean changed = false;

	@FXML
	TilePane bottomContainer;
	@FXML
	AnchorPane bottomPane, canvasPane, miniCanvas, layerPane;
	@FXML
	AnchorPane menuBar;
	@FXML
	MenuItem openImage, menuClose, menuExport, menuSaveProject, menuOpenProject, menuResetPicture,
	menuGrayScale, menuColorFilter, menuBlackWhite, menuWhitebalance, menuLevels, 
	menuCrop, menuExposure, menuContrast, menuHReflect, menuVReflect, menuRotateL, menuRotateR,
	menuBlur, menuGaussianBlur, menuSharpen, menuTextFilter, menuEdge, menuGrain, menuNewFilter, menuFilter, 
	menuZoomIn, menuZoomOut, menuUndo, menuRedo, menuResetWindow, menuAbout;
	@FXML
	Button closeButton, miniButton, maxiButton;
	@FXML
	Button exposureUpdate, contrastUpdate, levelsUpdate, grainUpdate, blurUpdate, gBlurUpdate, sharpenUpdate,
			textUpdate, cfUpdate, grayUpdate, bwUpdate, wbUpdate, filterUpdate;
	@FXML
	RadioButton yellowButton, orangeButton, blueButton, redButton, pinkButton, purpleButton, turquoiseButton,
			greenButton;
	@FXML
	ToggleGroup colorGroup;
	@FXML
	ColorPicker customColor, textColor;
	@FXML
	ComboBox<String> filterBox;
	@FXML
	Slider contrastThreshold, contrastIntensity, grainDeviation, levelsMin, levelsMax, blurRadius,
			gBlurRadius, sharpenIntensity, sharpenThreshold, textSize, colorIntensity, bwThreshold, bwIntensity, wbWarm, greyIntensity;
	@FXML
	StackPane toolContainer;
	@FXML
	HBox topLevel, adjustLevel, effectLevel, colorLevel, filterLevel, exposureLevel, contrastLevel, levelsLevel, grainLevel,
	blurLevel, gBlurLevel, sharpenLevel, textLevel, colorFilterLevel, grayLevel, bwLevel, wbLevel;
	@FXML
	Label adjustIcon, colorIcon, effectIcon, filterIcon, exposureIcon, exposureBackIcon, contrastIcon, contrastBackIcon, 
	levelsIcon, levelsBackIcon, grainIcon, grainBackIcon, blurIcon, blurBackIcon, gBlurIcon, gBlurBackIcon,
	sharpenIcon, sharpenBackIcon, textIcon, textBackIcon, colorFilterIcon, cfBackIcon, grayIcon, grayBackIcon, whiteBalanceIcon,
	wbBackIcon, bwIcon, bwBackIcon,	aBackIcon, cBackIcon, eBackIcon, fBackIcon, clearColorIcon;
	@FXML
	Slider slideZoom;
	@FXML
	TextField textInput;
	@FXML
	ChoiceBox<String> fontBox, fontPlacement;
	Layers layerstack = new Layers();

	

	public MainView(Stage pstage) {
		mainView = this;
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
		setChoiceBoxes();
				
		/***
		 * Interface buttons.
		 */
		closeButton.setOnAction(e -> {
			if (NewFilterHandeler.getFilters().size() > 0) {
				NewFilterHandeler.saveFilters();
			}
			exit(changed);
		});
		miniButton.setOnAction(e -> {
			getPrimaryStage().setIconified(true);
		});
		maxiButton.setOnAction(e -> {
			if (getPrimaryStage().isMaximized()) {
				getPrimaryStage().setMaximized(false);
			} else {
				getPrimaryStage().setMaximized(true);
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
					Layers.setBackgroundImage(ll);
					canvasUpdate();
					
				}
			} catch (IOException e1) {
				// On canceled fileopening
			}
			setDisableMenuItems(false);
			changed = true;

		});
		menuClose.setOnAction(e -> {
			if (NewFilterHandeler.getFilters().size() > 0) {
				NewFilterHandeler.saveFilters();
			}
			exit(changed);
		});
		menuExport.setOnAction(e -> {

			try {

				LoadedImage export = Layers.getBackgroundImage();
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
			changed = false;
		});
		menuOpenProject.setOnAction(e -> {
			OpenProject.openFile();
			setDisableMenuItems(false);
			canvasUpdate();
			changed = false;
		});

		menuZoomOut.setOnAction(e -> {
			canvasView.setZoomFactor((canvasView.getZoomFactor() * 1.5));
			System.out.println("zooooomOUT  " + canvasView.getZoomFactor());
			slideZoom.setValue((Delta.log(canvasView.getZoomFactor(), 2)+5)*20);
			canvasUpdate();
		});

		menuZoomIn.setOnAction(e -> {
			canvasView.setZoomFactor((canvasView.getZoomFactor() * 0.75));
			System.out.println("zooooomIN  " + canvasView.getZoomFactor());
			slideZoom.setValue((Delta.log(canvasView.getZoomFactor(), 2)+5)*20);
			
			System.out.println("slide" + slideZoom.getValue() + "              " + (Delta.log(canvasView.getZoomFactor(), 2)+5)*20);
			canvasUpdate();
		});
		menuUndo.setOnAction(e -> {
			Layers.remove(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1));
			canvasUpdate();
		});
		slideZoom.setValue(100);
		slideZoom.setOnMouseClicked(e -> {
			System.out.println("zooma " + slideZoom.getValue());
			canvasView.setZoomFactor(((Math.pow(2, (slideZoom.getValue() / 20 - 5)*-1))));
			canvasUpdate();
		});
		slideZoom.setOnMouseDragOver(e -> {
			System.out.println("zooma " + slideZoom.getValue());

		});

		menuCrop.setOnAction(e -> {
			CropView cropView = new CropView();
			canvasPane.getChildren().add(cropView);
			canvasUpdate();

		});

		menuNewFilter.setOnAction(e -> {
			Stage window = new Stage();
			AnchorPane pane = new AnchorPane();
			window.initModality(Modality.APPLICATION_MODAL);
			window.initOwner(primaryStage);
			window.initStyle(StageStyle.TRANSPARENT);
			pane.getChildren().add(new NewFilterView(window));
			Parent root = pane;

			Scene s = new Scene(root);

	        window.setScene(s);
			window.show();
		});
		menuResetWindow.setOnAction(e -> {
			canvasView.setTopX(0);
			canvasView.setTopY(0);
			canvasUpdate();

		});
	
		menuResetPicture.setOnAction(e -> {
			Layers.getLayerStack().clear();
			canvasUpdate();

		});
		
		menuAbout.setOnAction(e -> {
			Stage window = new Stage();
			AnchorPane pane = new AnchorPane();
			window.initModality(Modality.APPLICATION_MODAL);
			window.initOwner(primaryStage);
			window.initStyle(StageStyle.TRANSPARENT);
			pane.getChildren().add(new AboutView(window));
			Parent root = pane;

			Scene about = new Scene(root);

	        window.setScene(about);
			window.show();
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
		menuClickedOptions(menuColorFilter, colorFilterLevel, (new ColorShift(0, 0, 0, 0.5)));
		menuClickedOptions(menuContrast, contrastLevel, (new Contrast(100, 1.4)));
		menuClickedOptions(menuWhitebalance, wbLevel, (new WhiteBalance(40)));
		menuClickedOptions(menuLevels, levelsLevel, (new Levels(100, 40)));
		menuClickedOptions(menuBlackWhite, bwLevel, (new BlackAndWhite(123)));
		menuClickedOptions(menuGrain, grainLevel, new Grain(20));
		menuClickedOptions(menuExposure, exposureLevel, (new Exposure(40)));
		//menuClickedOptions(menuFilter, filterLevel, (new NewKernel()));
		menuClicked(menuEdge, (new Edge()));
		menuClickedOptions(menuTextFilter, textLevel, (new TextFilter()));

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

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		MainView.primaryStage = primaryStage;
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
	private FadeTransition fadeText = new FadeTransition(Duration.millis(150));
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
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(layerType));
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
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(layerType));
				updateLayerSettings(Layers.getLayerStack().get(Layers.getLayerStack().size()-1));
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
		customColor.setValue(null);
		clearColorIcon.setOnMouseClicked(e -> {
			customColor.setValue(null);
		});

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
					.getAction().uppdate();
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
			 Layers.getLayerStack().get(Layers.getLayerStack().size()-1)
			 .setRadius(sharpenIntensity.valueProperty().intValue());
			canvasUpdate();
		});
		textUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size()-1)
			.setTextFilter(textInput.getText(), fontBox.getValue() == null ? "Helvetica" : fontBox.getValue()  , (int) textSize.getValue(), fontPlacement.getValue() == null ? "mitten" : fontPlacement.getValue(), (int) (textColor.getValue().getRed() * 255), 
					(int) (textColor.getValue().getGreen() * 255), (int) (textColor.getValue().getBlue() * 255));
			canvasUpdate();
		});
		// Colors
		cfUpdate.setOnAction(e -> {
			if (customColor.getValue() != null) {
				System.out.println("customcolor");
				Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(customColor.getValue().getRed()*255,
						customColor.getValue().getGreen()*255, customColor.getValue().getBlue()*255,
						colorIntensity.valueProperty().doubleValue());
				colorGroup.selectToggle(null);
			} else {
				/*Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setColor(
						(String) colorGroup.getSelectedToggle().getUserData(),
						colorIntensity.valueProperty().doubleValue());*/
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
		filterUpdate.setOnAction(e -> {
			Layers.getLayerStack().get(Layers.getLayerStack().size()-1)
			.setKernel(NewFilterHandeler.getFilterKernel(filterBox.getSelectionModel().getSelectedItem().toString()),
					filterBox.getSelectionModel().getSelectedItem().toString());
			canvasUpdate();
		});

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
		fadeSettings(fadeText, textLevel);
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
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Exposure(1)));
				//exposureIntensity.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getDouble());
				exposureLevel.getChildren().add(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getAction().getVBox().get(0));
				exposureUpdate.toFront();
				canvasUpdate();
			}
		});
		exposureBackIcon.setOnMouseClicked(e -> {
			mouseClicked(exposureLevel, adjustLevel, fadeAdjust);
		});
		contrastIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, contrastLevel, fadeContrast);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Contrast(2, 10)));
				contrastThreshold.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getDouble());
				contrastIntensity.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getInt());
				canvasUpdate();
			}
		});
		contrastBackIcon.setOnMouseClicked(e -> {
			mouseClicked(contrastLevel, adjustLevel, fadeAdjust);
		});
		levelsIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, levelsLevel, fadeLevels);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Levels(1, 1)));
				levelsMin.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size()-1).getMinLevel());
				levelsMax.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size()-1).getMaxLevel());
				canvasUpdate();
			}
		});
		levelsBackIcon.setOnMouseClicked(e -> {
			mouseClicked(levelsLevel, adjustLevel, fadeAdjust);
		});
		grainIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, grainLevel, fadeGrain);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Grain(10)));
				grainDeviation.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size()-1).getInt());
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
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Blur(2)));
				blurRadius.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getInt());
				canvasUpdate();
			}
		});
		blurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(blurLevel, effectLevel, fadeEffect);
		});
		gBlurIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, gBlurLevel, fadeGBlur);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new GaussianBlur(2)));
				gBlurRadius.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getInt());
				canvasUpdate();
			}
		});
		gBlurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(gBlurLevel, effectLevel, fadeEffect);
		});
		sharpenIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, sharpenLevel, fadeSharpen);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Sharpen()));
				sharpenIntensity.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size()-1).getInt());
				canvasUpdate();
			}
		});
		sharpenBackIcon.setOnMouseClicked(e -> {
			mouseClicked(sharpenLevel, effectLevel, fadeEffect);
		});
		textIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, textLevel, fadeText);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new TextFilter()));
				canvasUpdate();
			}
		});
		textBackIcon.setOnMouseClicked(e -> {
			mouseClicked(textLevel, effectLevel, fadeEffect);
		});
		colorIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, colorLevel, fadeColor);
		});
		colorFilterIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, colorFilterLevel, fadeColorFilter);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(
						new Layer(new ColorShift(0.7019608020782471, 0.7019608020782471, 0.7019608020782471, 0.5)));
				colorIntensity.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getDouble());
				canvasUpdate();
			}
		});
		cfBackIcon.setOnMouseClicked(e -> {
			mouseClicked(colorFilterLevel, colorLevel, fadeColor);
			colorGroup.selectToggle(null);
		});
		grayIcon.setOnMouseClicked(e -> {
			// mouseClicked(colorLevel, grayLevel, fadeGray);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new GrayScale()));
				canvasUpdate();
			}
		});
		grayBackIcon.setOnMouseClicked(e -> {
			mouseClicked(grayLevel, colorLevel, fadeColor);
		});
		bwIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, bwLevel, fadeBW);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new BlackAndWhite(50)));
				bwIntensity.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getInt());
				canvasUpdate();
			}
		});
		bwBackIcon.setOnMouseClicked(e -> {
			mouseClicked(bwLevel, colorLevel, fadeColor);
		});
		whiteBalanceIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, wbLevel, fadeWB);
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new WhiteBalance(50)));
				wbWarm.setValue(Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).getInt());
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
			ObservableList<String> filters = FXCollections.observableArrayList(  );
			for(CreatedFilter f : NewFilterHandeler.getFilters()){
				filters.add(f.getName());
			}
			filterBox.getItems().addAll(filters);
			Layers.addLayer(new Layer(new NewKernel(new double[3][3], "Eget Filter")));
			canvasUpdate();
		});
		fBackIcon.setOnMouseClicked(e -> {
			mouseClicked(filterLevel, topLevel, fadeIn);
		});

	}

	/***
	 * Method for repainting the canvases.
	 */
	static void canvasUpdate() {
		System.out.println("Repaint");
		canvasView.repaint();
		miniCanvasView.repaint();
		layerView.update();
		changed = true;
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
		menuRedo.setDisable(b);
		menuResetWindow.setDisable(b);
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
		textUpdate.setDisable(b);
		cfUpdate.setDisable(b);
		bwUpdate.setDisable(b);
		wbUpdate.setDisable(b);
		filterUpdate.setDisable(b);
		slideZoom.setDisable(b);
	}

	private void exit(boolean changed) {
		if (changed) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Varning");
			alert.setHeaderText("Vill du spara projektet innan du avslutar?");

			ButtonType buttonTypeYes = new ButtonType("Spara");
			ButtonType buttonTypeNo = new ButtonType("Avsluta");
			ButtonType buttonTypeClose = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeClose);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeYes) {
				SaveProject.saveProject();
				Platform.exit();
			}
			if (result.get() == buttonTypeNo) {
				Platform.exit();
			}
		} else {
			Platform.exit();
		}
	}
	
	private void setChoiceBoxes() {
		fontPlacement.setItems(FXCollections.observableArrayList(FXCollections.observableArrayList("uppe","mitten","nere")));
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox.setItems(FXCollections.observableArrayList(fonts));
	}

	

	public static CanvasView getCanvas() {
		return canvasView;
	}

	/***
	 * Method that will bring forward the correct settings view for each layer.
	 * 
	 * @param layer
	 */
	public void updateLayerSettings(Layer layer) {
		toolContainer.getChildren().get(toolContainer.getChildren().size() - 1).setVisible(false);
		if (layer.getName().equals("Exponering")) {
			setVisibility(exposureLevel);
			//exposureIntensity.setValue(layer.getDouble());
		} else if (layer.getName().equals("Kontrast")) {
			setVisibility(contrastLevel);
			contrastThreshold.setValue(layer.getDouble());
			contrastIntensity.setValue(layer.getInt());
		} else if (layer.getName().equals("Nivåer")) {
			setVisibility(levelsLevel);
			levelsMin.setValue(layer.getMinLevel());
			levelsMax.setValue(layer.getMaxLevel());
		} else if (layer.getName().equals("Brus")) {
			setVisibility(grainLevel);
			grainDeviation.setValue(layer.getInt());
		} else if (layer.getName().equals("Oskärpa")) {
			setVisibility(blurLevel);
			blurRadius.setValue(layer.getInt());
		} else if (layer.getName().equals("Gaussisk Oskärpa")) {
			setVisibility(gBlurLevel);
			gBlurRadius.setValue(layer.getInt());
		} else if (layer.getName().equals("Skärpa")) {
			setVisibility(sharpenLevel);
			sharpenIntensity.setValue(layer.getInt());
		} else if (layer.getName().equals("Färgfilter")) {
			setVisibility(colorFilterLevel);
			colorIntensity.setValue(layer.getDouble());
		} else if (layer.getName().equals("Svartvitt")) {
			setVisibility(bwLevel);
			bwIntensity.setValue(layer.getInt());
		} else if (layer.getName().equals("Vitbalans")) {
			setVisibility(wbLevel);
			wbWarm.setValue(layer.getInt());
		} else if (layer.getName().equals("Gråskala")) {
			setVisibility(grayLevel);
		} else if (layer.getName().equals("Textfilter")) {
			setVisibility(textLevel);
			textSize.setValue(layer.getInt());
			//textInput.setText(layer.getText());
		}
		/**
		 * else if (layer.getName().equals("Eget filter")){
		 * setVisibility(customLevel); }
		 */
	}

	private void setVisibility(Node level) {
		level.toFront();
		level.setVisible(true);
	}
}

class Delta {
	double x, y;
	static int log(double x, int base)
	{
	    return (int) (Math.log(x) / Math.log(base));
	}
}