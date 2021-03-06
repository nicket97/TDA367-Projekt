package controllers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Project.OpenProject;
import Project.SaveProject;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
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
import model.core.Layerable;
import model.core.LoadedImage;
import model.core.NewFilterHandeler;
import model.core.FindMenuHandler;
import model.transformations.*;
import model.transformations.core.Layer;
import model.transformations.core.Layers;
import javafx.scene.input.KeyCode;

/**
 * Main controller, distributes tasks
 */

public class MainView extends AnchorPane implements Initializable {

	static MainView mainView;
	static CanvasView canvasView;
	static MiniCanvasView miniCanvasView;
	public static LayerView layerView;
	private static Stage primaryStage;
	private Point topLeft = new Point(0, 0);
	private Point bottomRight = new Point(0, 0);
	private FindMenuHandler findMenuHandler;
	
	private Layer lastAction;
	/**
	 * Indicates if the project has changed.
	 */
	private static boolean changed = false;

	@FXML
	TilePane bottomContainer;	
	@FXML
	StackPane toolContainer;
	@FXML
	AnchorPane bottomPane, canvasPane, miniCanvas, layerPane, menuBar;
	@FXML
	MenuItem openImage, menuClose, menuExport, menuSaveProject, menuOpenProject, menuResetPicture, menuGrayScale,
			menuColorFilter, menuBlackWhite, menuWhitebalance, menuLevels, menuCrop, menuExposure, menuContrast,
			menuHReflect, menuVReflect, menuRotateL, menuRotateR, menuBlur, menuGaussianBlur, menuSharpen,
			menuTextFilter, menuEdge, menuGrain, menuNewFilter, menuFilter, menuZoomIn, menuZoomOut, menuUndo, menuRedo,
			menuResetWindow, menuAbout, menuCopyRight, menuEmoji;
	@FXML
	Button closeButton, miniButton, maxiButton, exposureUpdate, contrastUpdate, levelsUpdate, grainUpdate, blurUpdate, 
			gBlurUpdate, sharpenUpdate,	textUpdate, cfUpdate, grayUpdate, bwUpdate, wbUpdate, filterUpdate, 
			copyRightUpdate, emojiUpdate;
	@FXML
	HBox topLevel, adjustLevel, effectLevel, colorLevel, filterLevel, exposureLevel, contrastLevel, levelsLevel,
			grainLevel, blurLevel, gBlurLevel, sharpenLevel, textLevel, colorFilterLevel, grayLevel, bwLevel, wbLevel, 
			copyRightLevel, emojiLevel;
	@FXML
	Label adjustIcon, colorIcon, effectIcon, filterIcon, exposureIcon, exposureBackIcon, contrastIcon, contrastBackIcon,
			levelsIcon, levelsBackIcon, grainIcon, grainBackIcon, blurIcon, blurBackIcon, gBlurIcon, gBlurBackIcon,
			sharpenIcon, sharpenBackIcon, textIcon, textBackIcon, colorFilterIcon, cfBackIcon, grayIcon, grayBackIcon,
			whiteBalanceIcon, wbBackIcon, bwIcon, bwBackIcon, aBackIcon, cBackIcon, eBackIcon, fBackIcon,
			clearColorIcon, zoomIcon, handIcon, cursorIcon, copyRightBackIcon, emojiBackIcon;
	@FXML
	Slider slideZoom, emojiSlider, emojiY, emojiX;
	@FXML
	TextField textInput, menuFind, copyRightName;
	@FXML
	ChoiceBox<String> fontBox, fontPlacement, fontBox1, emojiBox;
	@FXML
	ComboBox<String> filterBox;
	@FXML
	ContextMenu suggestionsPopUp;
	
	Layers layerstack = new Layers();
	List<MenuItem> menuItems;

	/**
	 * Constructor of the MainView class
	 * @param pstage primary stage
	 */
	public MainView(Stage pstage) {
		
		mainView = this;
		primaryStage = pstage;
		NewFilterHandeler.loadFilters();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/MainView.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		final Delta dragDelta = new Delta();

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

		/***
		 * Menu controls.
		 */
		openImage.setOnAction(e -> {
			Layers.clear();
			canvasView.setZoomFactor(1);
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(" Bildfiler", "*.png", "*.jpg",
					"*.jpeg");
			fileChooser.getExtensionFilters().add(extFilter);
			;
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
				LoadedImage finalExport = new LoadedImage(export);
				FileChooser fileChooser = new FileChooser();

				File outputfile = fileChooser.showSaveDialog(new Stage());
				BufferedImage bufferedExport = finalExport.getBufferedImg();
				ImageIO.write(bufferedExport, "png", new File(outputfile.getAbsolutePath() + ".png"));

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
			slideZoom.setValue((Delta.log(canvasView.getZoomFactor(), 2) + 5) * 20);
			canvasUpdate();
		});

		menuZoomIn.setOnAction(e -> {
			canvasView.setZoomFactor((canvasView.getZoomFactor() / 1.5));
			slideZoom.setValue((Delta.log(canvasView.getZoomFactor(), 2) + 5) * 20);
			canvasUpdate();
		});
		menuUndo.setOnAction(e -> {
			lastAction = Layers.getLast();
			Layers.remove(Layers.getLast());
			canvasUpdate();
		});
		menuRedo.setOnAction(e -> {
			if(lastAction != null){
				Layers.addLayer(lastAction);
				lastAction = null;
				canvasUpdate();
			}
		});
		slideZoom.setOnMouseClicked(e -> {
			canvasView.setZoomFactor(((Math.pow(2, (slideZoom.getValue() / 20 - 5) * -1))));
			canvasUpdate();
		});
		slideZoom.setOnMouseDragOver(e -> {

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
		menuBlur.setOnAction(e -> {
			Layers.addLayer(new Layer(new Blur(5)));
			showBlur(Layers.getLast());
		});
		menuGaussianBlur.setOnAction(e -> {
			Layers.addLayer(new Layer(new GaussianBlur(5)));
			showGausianBlur(Layers.getLast());
		});
		menuSharpen.setOnAction(e -> {
			Layers.addLayer(new Layer(new Sharpen()));
			showSharpen(Layers.getLast());
		});
		menuGrayScale.setOnAction(e -> {
			Layers.addLayer(new Layer(new GrayScale()));
			canvasUpdate();
		});
		menuColorFilter.setOnAction(e -> {
			Layers.addLayer(new Layer(new ColorShift(25, -25, -25, 0.8)));
			showColorShift(Layers.getLast());
		});
		menuContrast.setOnAction(e -> {
			Layers.addLayer(new Layer(new Contrast(45, 1.25)));
			showContrast(Layers.getLast());
		});
		menuWhitebalance.setOnAction(e -> {
			Layers.addLayer(new Layer(new WhiteBalance(40)));
			showWhiteBalance(Layers.getLast());
		});
		menuLevels.setOnAction(e -> {
			Layers.addLayer(new Layer(new Levels(40, 160)));
			showLevels(Layers.getLast());
		});
		menuBlackWhite.setOnAction(e -> {
			Layers.addLayer(new Layer(new BlackAndWhite(128)));
			showBlackAndWhite(Layers.getLast());
		});
		menuGrain.setOnAction(e -> {
			Layers.addLayer(new Layer(new Grain(20)));
			showGrain(Layers.getLast());
		});
		menuExposure.setOnAction(e -> {
			Layers.addLayer(new Layer(new Exposure(-80)));
			showExposure(Layers.getLast());
		});
		menuClicked(menuEdge, (new Edge()));
		menuTextFilter.setOnAction(e -> {
			Layers.addLayer(new Layer(new TextFilter()));
			showTextFilter(Layers.getLast());
		});
		menuFilter.setOnAction(e -> {
			Layers.addLayer(new Layer(new NewKernel(new double [3][3], "Eget filter")));
			showCustomFilter(Layers.getLast());
		});
		menuFind.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> o, String oldValue, String newValue) {
				showSuggestions(menuFind.getText());
			}
		});
		menuFind.setOnKeyReleased(e -> {
			if(e.getCode().equals(KeyCode.ENTER) && (suggestionsPopUp.getItems().size() == 1)) {
				suggestionsPopUp.getItems().get(0).fire();
				menuFind.setText("");
			}
		});
		menuFind.setOnMouseClicked(e -> {
			showSuggestions(menuFind.getText());
		});
		menuCopyRight.setOnAction(e -> {
			Layers.addLayer(new Layer(new CopyRight()));
			showCopyRight(Layers.getLast());
		});
		menuEmoji.setOnAction(e -> {
			Layers.addLayer(new Layer(new Emoji()));
			showEmoji(Layers.getLast());
		});
		
		/***
		 * Move main window controls.
		 */
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
		
		handIcon.setOnMouseClicked(e -> {
			canvasView.setMouse();
		});
		cursorIcon.setOnMouseClicked(e -> {
			canvasView.resetMouse();
			
		});
		zoomIcon.setOnMouseClicked(e -> {
			canvasView.resetMouse();
			canvasPane.setOnMouseClicked(s -> {
				canvasView.setZoomFactor(canvasView.getZoomFactor()/1.5);
				slideZoom.setValue((Delta.log(canvasView.getZoomFactor(), 2) + 5) * 20);
				canvasUpdate();
			});
		});
		
	}

	/***
	

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
	 * @param name name of transition
	 * @param node which HBox node
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
	 * @param begin beginning of node
	 * @param end end of node
	 * @param fade how fade should work
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
	 * @param name name of filter
	 * @param layerType which type of layer
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

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		MainView.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		menuItems = new ArrayList<MenuItem>();
		Collections.addAll(menuItems, menuExport, menuSaveProject, menuResetPicture, menuGrayScale,
			menuColorFilter, menuBlackWhite, menuWhitebalance, menuLevels, menuCrop, menuExposure, menuContrast,
			menuHReflect, menuVReflect, menuRotateL, menuRotateR, menuBlur, menuGaussianBlur, menuSharpen,
			menuTextFilter, menuEdge, menuGrain, menuFilter, menuZoomIn, menuZoomOut, menuUndo, menuRedo,
			menuResetWindow, menuCopyRight, menuEmoji);
		
		setDisableMenuItems(true);

		canvasView = new CanvasView(primaryStage);
		miniCanvasView = new MiniCanvasView();
		layerView = new LayerView();

		canvasPane.getChildren().add(canvasView);
		miniCanvas.getChildren().add(miniCanvasView);
		miniCanvas.setTranslateX(5);
		layerPane.getChildren().add(layerView);

		topLevel.toFront();

		slideZoom.setValue(100);

		// Custom filters
		filterUpdate.setOnAction(e -> {
			Layers.addLayer(new Layer(new NewKernel(NewFilterHandeler.getFilter(
					filterBox.getValue()).getKernel()
					,NewFilterHandeler.getFilter(filterBox.getValue()).getName() )));
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
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Exposure(-60)));
				showExposure(Layers.getLast());
			}
		});
		exposureBackIcon.setOnMouseClicked(e -> {
			mouseClicked(exposureLevel, adjustLevel, fadeAdjust);
		});
		contrastIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Contrast(45, 1.25)));
				showContrast(Layers.getLast());
			}
		});
		contrastBackIcon.setOnMouseClicked(e -> {
			mouseClicked(contrastLevel, adjustLevel, fadeAdjust);
		});
		levelsIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Levels(40, 60)));
				showLevels(Layers.getLast());
			}
		});
		levelsBackIcon.setOnMouseClicked(e -> {
			mouseClicked(levelsLevel, adjustLevel, fadeAdjust);
		});
		grainIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Grain(20)));
				showGrain(Layers.getLast());
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
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Blur(5)));
				showBlur(Layers.getLast());
			}
		});
		blurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(blurLevel, effectLevel, fadeEffect);
		});
		gBlurIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new GaussianBlur(5)));
				showGausianBlur(Layers.getLast());
			}
		});
		gBlurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(gBlurLevel, effectLevel, fadeEffect);
		});
		sharpenIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new Sharpen()));
				showSharpen(Layers.getLast());
			}
		});
		sharpenBackIcon.setOnMouseClicked(e -> {
			mouseClicked(sharpenLevel, effectLevel, fadeEffect);
		});
		textIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new TextFilter()));
				showTextFilter(Layers.getLast());
			}
		});
		textBackIcon.setOnMouseClicked(e -> {
			mouseClicked(textLevel, effectLevel, fadeEffect);
		});
		colorIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, colorLevel, fadeColor);
		});
		colorFilterIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new ColorShift(0.5, 0.5, 0.5, 0.8)));
				showColorShift(Layers.getLast());
			}
		});
		cfBackIcon.setOnMouseClicked(e -> {
			mouseClicked(colorFilterLevel, colorLevel, fadeColor);
		});
		grayIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new GrayScale()));
				canvasUpdate();
			}
		});
		grayBackIcon.setOnMouseClicked(e -> {
			mouseClicked(grayLevel, colorLevel, fadeColor);
		});
		bwIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new BlackAndWhite(150)));
				showBlackAndWhite(Layers.getLast());
			}
		});
		bwBackIcon.setOnMouseClicked(e -> {
			mouseClicked(bwLevel, colorLevel, fadeColor);
		});
		whiteBalanceIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null) {
				Layers.addLayer(new Layer(new WhiteBalance(50)));
				showWhiteBalance(Layers.getLast());
			}
		});
		wbBackIcon.setOnMouseClicked(e -> {
			mouseClicked(wbLevel, colorLevel, fadeColor);
		});
		cBackIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, topLevel, fadeIn);
		});
		filterIcon.setOnMouseClicked(e -> {
			if (Layers.getBackgroundImage() != null){
				Layers.addLayer(new Layer(new NewKernel(new double[3][3], "Eget Filter")));
				showCustomFilter(Layers.getLast());
			}
		});
		fBackIcon.setOnMouseClicked(e -> {
			mouseClicked(filterLevel, topLevel, fadeIn);
		});
		copyRightBackIcon.setOnMouseClicked(e -> {
			mouseClicked(copyRightLevel, topLevel, fadeIn);
		});
		emojiBackIcon.setOnMouseClicked(e -> {
			mouseClicked(emojiLevel, topLevel, fadeIn);
		});
		
		initFindMenuHandler();
	}

	/***
	 * Method for repainting the canvases.
	 */
	static void canvasUpdate() {
		canvasView.repaint();
		miniCanvasView.repaint();
		layerView.update();
		changed = true;
	}

	/***
	 * Method for disabling menu options and update buttons when no image is
	 * open.
	 * 
	 * @param b boolean to disable or enable
	 */
	private void setDisableMenuItems(boolean b) {
		for (MenuItem m : menuItems){
			m.setDisable(b);
		}
		slideZoom.setDisable(b);
		filterUpdate.setDisable(b);
	}

	private void exit(boolean changed) {
		if (changed) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setTitle("Varning");
			alert.setHeaderText("Vill du spara projektet innan du avslutar?");

			ButtonType buttonTypeYes = new ButtonType("Spara");
			ButtonType buttonTypeNo = new ButtonType("Avsluta");
			ButtonType buttonTypeClose = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeClose);
			
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(
			getClass().getResource("../resources/css/style.css").toExternalForm());

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
	

	public static CanvasView getCanvas() {
		return canvasView;
	}

	/***
	 * Method that will bring forward the correct settings view for each layer.
	 * 
	 * @param layer layer to bring forward settings for
	 */
	public void updateLayerSettings(Layer layer) {

		if (layer.getName().equals("Exponering")) {
			showExposure(layer);
		} else if (layer.getName().equals("Kontrast")) {
			showContrast(layer);
		} else if (layer.getName().equals("Nivåer")) {
			showLevels(layer);
		} else if (layer.getName().equals("Brus")) {
			showGrain(layer);
		} else if (layer.getName().equals("Oskärpa")) {
			showBlur(layer);
		} else if (layer.getName().equals("Gaussisk Oskärpa")) {
			showGausianBlur(layer);
		} else if (layer.getName().equals("Skärpa")) {
			showSharpen(layer);
		} else if (layer.getName().equals("Färgfilter")) {
			showColorShift(layer);
		} else if (layer.getName().equals("Svartvitt")) {
			showBlackAndWhite(layer);
		} else if (layer.getName().equals("Vitbalans")) {
			showWhiteBalance(layer);
		} else if (layer.getName().equals("Textfilter")) {
			showTextFilter(layer);
			// textInput.setText(layer.getText());
		} else if (layer.getName().equals("Eget filter")){
			showCustomFilter(layer); 
		} else if (layer.getName().equals("Copyright")){
			showCopyRight(layer); 
		}
		else if (layer.getName().equals("Emoji")){
			showEmoji(layer); 
		}
	}

	private void setVisibility(Node level) {
		level.toFront();
		level.setVisible(true);
	}

	private void showFilterSettings(Button b, Layer l, HBox h, Label backIcon) {
		toolContainer.getChildren().get(toolContainer.getChildren().size() - 1).setVisible(false);
		b.setOnAction(e -> {
			l.getAction().uppdate();
			canvasUpdate();
		});
		h.getChildren().clear();
		h.getChildren().addAll(b, backIcon);
		h.getChildren().addAll(l.getAction().getVBox());
		h.toFront();
		b.toFront();
		setVisibility(h);
		canvasUpdate();
	}

	
	public void showExposure(Layer l) {
		showFilterSettings(exposureUpdate, l, exposureLevel, exposureBackIcon);
	}
	private void showBlackAndWhite(Layer l) {
		showFilterSettings(bwUpdate, l, bwLevel, bwBackIcon);
	}
	private void showTextFilter(Layer l) {
		showFilterSettings(textUpdate, l, textLevel, textBackIcon);
	}
	private void showGrain(Layer l) {
		showFilterSettings(grainUpdate, l, grainLevel, grainBackIcon);
	}
	private void showLevels(Layer l) {
		showFilterSettings(levelsUpdate, l, levelsLevel, levelsBackIcon);
	}
	private void showWhiteBalance(Layer l) {
		showFilterSettings(wbUpdate, l, wbLevel, wbBackIcon);
	}
	private void showContrast(Layer l) {
		toolContainer.getChildren().get(toolContainer.getChildren().size() - 1).setVisible(false);
		contrastUpdate.setOnAction(null);
		contrastUpdate.setOnAction(e -> {
			l.getAction().uppdate();
			canvasUpdate();
		});
		contrastLevel.getChildren().clear();
		contrastLevel.getChildren().addAll(contrastBackIcon, contrastUpdate);
		contrastLevel.getChildren().addAll(l.getAction().getVBox().get(0), l.getAction().getVBox().get(1));
		contrastLevel.toFront();
		contrastUpdate.toFront();
		setVisibility(contrastLevel);
		canvasUpdate();
	}

	private void showColorShift(Layer l) {
		showFilterSettings(cfUpdate, l, colorFilterLevel, cfBackIcon);
	}

	private void showSharpen(Layer l) { 
		// TODO
		/*
		 * toolContainer.getChildren().get(toolContainer.getChildren().size() -
		 * 1).setVisible(false); sharpenUpdate.setOnAction(e -> {
		 * l.getAction().uppdate(); canvasUpdate(); });
		 * sharpenLevel.getChildren().clear();
		 * sharpenLevel.getChildren().add(sharpenBackIcon);
		 * sharpenLevel.getChildren().add(l.getAction().getVBox().get(0));
		 * sharpenLevel.getChildren().add(l.getAction().getVBox().get(1));
		 * sharpenLevel.getChildren().add(sharpenUpdate);
		 * sharpenLevel.toFront(); sharpenUpdate.toFront();
		 * setVisibility(sharpenLevel);
		 */
		canvasUpdate();
	}
	
	private void showBlur(Layer l) {
		showFilterSettings(blurUpdate, l, blurLevel, blurBackIcon);
	}
	private void showGausianBlur(Layer l) {
		showFilterSettings(gBlurUpdate, l, gBlurLevel, gBlurBackIcon);
	}
	private void showCustomFilter(Layer l) {
		showFilterSettings(filterUpdate, l, filterLevel, fBackIcon);
	}
	private void showCopyRight(Layer l) {
		showFilterSettings(copyRightUpdate, l, copyRightLevel, copyRightBackIcon);
	}
	private void showEmoji(Layer l) {
		showFilterSettings(emojiUpdate, l, emojiLevel, emojiBackIcon);
	}

	public void topToFront() {
		toolContainer.getChildren().get(toolContainer.getChildren().size() - 1).setVisible(false);
		setVisibility(topLevel);
	}
	
	/**
	 * Shows the suggestions by the search function in a context menu under the text field.
	 * This method is called every time you release a key when the text field is focused.
	 */
	private void showSuggestions(String txt) {
		ArrayList<MenuItem> suggestions = findMenuHandler.getSuggestions(txt);
		suggestionsPopUp.getItems().clear();
		if (txt.equals("")) {
			suggestionsPopUp.hide();
			return;
		}
		for(int i = 0; i < suggestions.size(); i++) {
			MenuItem suggestedItem = suggestions.get(i);
			MenuItem item = new MenuItem(suggestedItem.getText());
			item.setAccelerator(suggestedItem.getAccelerator());
			item.setOnAction(e -> {
				suggestedItem.fire();
				menuFind.setText("");
			});
			suggestionsPopUp.getItems().add(item);
		}
		suggestionsPopUp.show(menuFind, Side.BOTTOM, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
	}
	
	/**
	 * Creates a FindMenuHandler with all the menuItems.
	 */
	private void initFindMenuHandler() {
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>(Arrays.asList(menuClose, 
				menuExport, menuSaveProject, menuOpenProject, menuResetPicture, menuGrayScale,
				menuColorFilter, menuBlackWhite, menuWhitebalance, menuLevels, menuCrop, menuExposure, 
				menuContrast, menuHReflect, menuVReflect, menuRotateL, menuRotateR, menuBlur, 
				menuGaussianBlur, menuSharpen, menuTextFilter, menuEdge, menuGrain, menuNewFilter, 
				menuFilter, menuZoomIn, menuZoomOut, menuUndo, menuRedo, openImage, 
				menuResetWindow, menuAbout, menuCopyRight, menuEmoji));
		findMenuHandler = new FindMenuHandler(menuItems);
	}
	
}

class Delta {
	double x, y;
	static double log(double x, int base) {
		return (Math.log(x) / Math.log(base));
	}
}