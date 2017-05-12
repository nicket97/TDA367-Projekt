package controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.stage.Stage;
import main.Layers;
import main.Main;
import model.BlackAndWhite;
import model.Blur;
import model.ColorShift;
import model.Contrast;
import model.Edge;
import model.Exposure;
import model.GaussianBlur;
import model.GrayScale;
import model.Layer;
import model.Layerable;
import model.Levels;
import model.LoadedImage;
import model.HMirroring;
import model.OpenProject;
import model.RotateL;
import model.RotateR;
import model.SaveProject;
import model.Sharpen;
import model.VMirroring;
import model.WhiteBalance;

public class MainView extends AnchorPane implements Initializable {
	
	public static LayerView layerView;
	static CanvasView canvasView;
	MiniCanvasView miniCanvasView; 
	
	@FXML
	TilePane bottomContainer;
	@FXML
	AnchorPane bottomPane, canvasPane, miniCanvas, layerPane;
	@FXML
	MenuItem openImage, menuClose, menuExport, menuSaveProject, menuOpenProject;
	@FXML
	MenuItem menuGrayScale, menuColorFilter, menuBlackWhite, menuWhitebalance, menuLevels;
	@FXML
	MenuItem menuCrop, menuExposure, menuContrast, menuHReflect, menuVReflect, menuRotateL, menuRotateR;
	@FXML
	MenuItem menuBlur, menuGaussianBlur, menuSharpen, menuTextFilter, menuEdge;
	@FXML
	MenuItem menuFMatte, menuFBW, menuFVintage;
	@FXML
	MenuItem menuZoomIn, menuZoomOut, menuUndo;
	@FXML
	Button closeButton,miniButton, maxiButton;
	@FXML
	Button blurUpdate, gBlurUpdate, sharpenUpdate;
	@FXML
	Button cfUpdate, grayUpdate, bwUpdate, wbUpdate;
	@FXML
	Button yellowButton, orangeButton, blueButton, redButton, pinkButton;
	@FXML
	Button purpleButton, turquoiseButton, greenButton;
	@FXML
	ColorPicker customColor;
	@FXML
	Slider blurRadius, gBlurRadius, gBlurIntensity;
	@FXML
	Slider colorIntensity, bwThreshold, bwIntensity, wbWarm, wbIntensity;
	@FXML
	StackPane toolContainer;
	@FXML
	HBox topLevel, adjustLevel, effectLevel, colorLevel, filterLevel;
	@FXML
	HBox exposureLevel, contrastLevel, levelsLevel;
	@FXML
	HBox blurLevel, gBlurLevel, sharpenLevel;
	@FXML
	HBox colorFilterLevel, grayLevel, bwLevel, wbLevel;
	@FXML
	Label adjustIcon, colorIcon, effectIcon, filterIcon;
	@FXML
	Label exposureIcon, exposureBackIcon, contrastIcon, contrastBackIcon, levelsIcon, levelsBackIcon;
	@FXML
	Label blurIcon, blurBackIcon, gBlurIcon, gBlurBackIcon, sharpenIcon, sharpenBackIcon;
	@FXML
	Label colorFilterIcon, cfBackIcon, grayIcon, grayBackIcon, whiteBalanceIcon, wbBackIcon, bwIcon, bwBackIcon;
	@FXML
	Label aBackIcon, cBackIcon, eBackIcon, fBackIcon;
	
	Layers layerstack = new Layers();
	
	private static LoadedImage backgroundImage;

	public MainView() {

		FXMLLoader fxmlLoader =	new FXMLLoader(getClass().getResource("/resources/fxml/MainView.fxml"));

		System.out.println("mainview");
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		openImage.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			
			//fileChooser.setSelectedExtensionFilter(new ExtensionFilter(".jpg", ".png" , ".jpeg"));
			fileChooser.setTitle("V�lj en bild");
			
			File f = fileChooser.showOpenDialog(new Stage());
			
			BufferedImage in;
			try {
				in = ImageIO.read(f);
				BufferedImage newImage = new BufferedImage(
					    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

					Graphics2D g = newImage.createGraphics();
					g.drawImage(in, 0, 0, null);
					g.dispose();
					LoadedImage ll = new LoadedImage (newImage);
					//System.out.println(canvasView.toString());
					setBackgroundImage(ll);
					canvasView.repaint();
			} catch (IOException e1) {
				// On canceled fileopening
			}
			setDisableMenuItems(false);

		});
		menuClose.setOnAction(e ->{
			Platform.exit();
			//System.exit(0);
		});
		menuExport.setOnAction(e ->{
			
			try {
			    LoadedImage export = MainView.backgroundImage;
			    for(Layer layer : Layers.getLayerStack()){
					export = layer.getAction().transform(export);
				}
			    FileChooser fileChooser = new FileChooser();
			   
			      File outputfile =  fileChooser.showSaveDialog(new Stage());			  
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
		
		closeButton.setOnAction(e ->{
			Platform.exit();
			//System.exit(0);
		});
		
		miniButton.setOnAction(e ->{
			Main.getPrimaryStage().setIconified(true);
		});
		maxiButton.setOnAction(e ->{
		if (Main.getPrimaryStage().isMaximized()){
				Main.getPrimaryStage().setMaximized(false);
		} else { 
			Main.getPrimaryStage().setMaximized(true);
		}
		});
		
		menuZoomOut.setOnAction(e ->{
			canvasView.setZoomFactor((canvasView.getZoomFactor() * 1.5));
			System.out.println("zooooomOUT  " + canvasView.getZoomFactor());
			canvasView.repaint();
			});
		
		menuZoomIn.setOnAction(e ->{
			canvasView.setZoomFactor((canvasView.getZoomFactor() * 0.75));
			System.out.println("zooooomIN  " + canvasView.getZoomFactor());
			canvasView.repaint();
			});
		menuUndo.setOnAction(e -> {
			Layers.remove(Layers.getLayerStack().get(Layers.getLayerStack().size()-1));
			canvasView.repaint();
		});
		
		menuClicked(menuBlur, (new Blur(7)));
		menuClicked(menuGaussianBlur, (new GaussianBlur(3)));
		menuClicked(menuSharpen, (new Sharpen()));
		menuClicked(menuGrayScale, (new GrayScale()));
		menuClicked(menuColorFilter, (new ColorShift(50,1,1)));
		menuClicked(menuContrast, (new Contrast(100, 1.4)));
		menuClicked(menuHReflect, (new HMirroring()));
		menuClicked(menuVReflect, (new VMirroring()));
		menuClicked(menuWhitebalance, (new WhiteBalance(20)));
		menuClicked(menuLevels, (new Levels(100,40)));
		menuClicked(menuRotateL, (new RotateL()));
		menuClicked(menuRotateR, (new RotateR()));
		menuClicked(menuBlackWhite, (new BlackAndWhite(123)));
		menuClicked(menuExposure, (new Exposure(40)));
		menuClicked(menuEdge, (new Edge()));

	}
	
	private FadeTransition fadeIn = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeAdjust = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeExposure = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeContrast = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeLevels = new FadeTransition(Duration.millis(150));
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
	
	private void fadeSettings(FadeTransition name, Node node){
		name.setNode(node);
		name.setFromValue(0.0);
		name.setToValue(1.0);
		name.setCycleCount(1);
		name.setAutoReverse(false);
		return;
	}
	
	private void mouseClicked(Node begin, Node end, FadeTransition fade){
		begin.setVisible(false);
		end.setVisible(true);
		fade.playFromStart();
		return;
	}
	
	private void menuClicked(MenuItem name, Layerable layerType){
		name.setOnAction( e->{
			layerstack.addLayer(new Layer(layerType));
			canvasView.repaint();
		});
		return;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setDisableMenuItems(true);

		canvasView = new CanvasView();
		miniCanvasView = new MiniCanvasView();
		layerView = new LayerView();
		
		//bottomContainer.getChildren().add(new ToolView());
		canvasPane.getChildren().add(canvasView);
		miniCanvas.getChildren().add(miniCanvasView);
		layerPane.getChildren().add(layerView);
	
		fadeSettings(fadeIn, topLevel);
		fadeSettings(fadeAdjust, adjustLevel);
		fadeSettings(fadeExposure, exposureLevel);
		fadeSettings(fadeContrast, contrastLevel);
		fadeSettings(fadeLevels, levelsLevel);
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

		adjustIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, adjustLevel, fadeAdjust);
		});
		aBackIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, topLevel, fadeIn);
		});	
		exposureIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, exposureLevel, fadeExposure);
		});
		exposureBackIcon.setOnMouseClicked(e -> {
			mouseClicked(exposureLevel, adjustLevel, fadeAdjust);
		});
		contrastIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, contrastLevel, fadeContrast);
		});
		contrastBackIcon.setOnMouseClicked(e -> {
			mouseClicked(contrastLevel, adjustLevel, fadeAdjust);
		});
		levelsIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, levelsLevel, fadeLevels);
		});
		levelsBackIcon.setOnMouseClicked(e -> {
			mouseClicked(levelsLevel, adjustLevel, fadeAdjust);
		});
		effectIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, effectLevel, fadeEffect);
		});	
		eBackIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, topLevel, fadeIn);
		});	
		blurIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, blurLevel, fadeBlur);
		});
		blurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(blurLevel, effectLevel, fadeEffect);
		});
		gBlurIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, gBlurLevel, fadeGBlur);
		});
		gBlurBackIcon.setOnMouseClicked(e -> {
			mouseClicked(gBlurLevel, effectLevel, fadeEffect);
		});
		sharpenIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, sharpenLevel, fadeSharpen);
		});
		sharpenBackIcon.setOnMouseClicked(e -> {
			mouseClicked(sharpenLevel, effectLevel, fadeEffect);
		});
		colorIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, colorLevel, fadeColor);
		});
		colorFilterIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, colorFilterLevel, fadeColorFilter);
		});
		cfBackIcon.setOnMouseClicked(e -> {
			mouseClicked(colorFilterLevel, colorLevel, fadeColor);
		});
		grayIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, grayLevel, fadeGray);
		});
		grayBackIcon.setOnMouseClicked(e -> {
			mouseClicked(grayLevel, colorLevel, fadeColor);
		});
		bwIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, bwLevel, fadeBW);
		});
		bwBackIcon.setOnMouseClicked(e -> {
			mouseClicked(bwLevel, colorLevel, fadeColor);
		});
		whiteBalanceIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, wbLevel, fadeWB);
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
	}

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
		menuHReflect.setDisable(b);
		menuVReflect.setDisable(b);
		menuRotateL.setDisable(b);
		menuRotateR.setDisable(b);
		menuBlur.setDisable(b);
		menuGaussianBlur.setDisable(b);
		menuSharpen.setDisable(b);
		menuTextFilter.setDisable(b);
		menuEdge.setDisable(b);
		menuFMatte.setDisable(b);
		menuFBW.setDisable(b);
		menuFVintage.setDisable(b);
		menuZoomIn.setDisable(b);
		menuZoomOut.setDisable(b);
	}

	public static LoadedImage getBackgroundImage() {
		return backgroundImage;
	}

	public static void setBackgroundImage(LoadedImage backgroundImage) {
		MainView.backgroundImage = backgroundImage;
	}
	public static CanvasView getCanvas(){
		return canvasView;
	}	
}
