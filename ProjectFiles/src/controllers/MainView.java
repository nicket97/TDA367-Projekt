package controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.stage.Stage;
import main.Layers;
import main.Main;
import model.Blur;
import model.ColorShift;
import model.Contrast;
import model.GaussianBlur;
import model.GrayScale;
import model.Layer;
import model.Layerable;
import model.Levels;
import model.LoadedImage;
import model.OpenProject;
import model.SaveProject;
import model.Sharpen;

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
	MenuItem menuCrop, menuExposure, menuContrast, menuReflect;
	@FXML
	MenuItem menuBlur, menuGaussianBlur, menuSharpen, menuTextFilter;
	@FXML
	MenuItem menuFMatte, menuFBW, menuFVintage;
	@FXML
	MenuItem menuZoomIn, menuZoomOut;
	@FXML
	Button closeButton,miniButton, maxiButton;
	@FXML
	StackPane toolContainer;
	@FXML
	HBox topLevel, adjustLevel, effectLevel, colorLevel, filterLevel;
	@FXML
	Label adjustIcon, colorIcon, effectIcon, filterIcon;
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
			menuExport.setDisable(false);
			menuSaveProject.setDisable(false);
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
			menuExport.setDisable(false);
			menuSaveProject.setDisable(false);
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
		
		menuClicked(menuBlur, (new Blur(4)));
		menuClicked(menuGaussianBlur, (new GaussianBlur(3)));
		menuClicked(menuSharpen, (new Sharpen()));
		menuClicked(menuGrayScale, (new GrayScale()));
		menuClicked(menuColorFilter, (new ColorShift(50,1,1)));
		menuClicked(menuLevels, (new Levels(100,40)));
		menuClicked(menuContrast, (new Contrast(200, 5)));


	}
	
	private FadeTransition fadeIn = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeAdjust = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeColor = new FadeTransition(Duration.millis(150));
	private FadeTransition fadeEffect = new FadeTransition(Duration.millis(150));
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
		menuExport.setDisable(true);
		menuSaveProject.setDisable(true);

		canvasView = new CanvasView();
		miniCanvasView = new MiniCanvasView();
		layerView = new LayerView();
		
		//bottomContainer.getChildren().add(new ToolView());
		canvasPane.getChildren().add(canvasView);
		miniCanvas.getChildren().add(miniCanvasView);
		layerPane.getChildren().add(layerView);
	
		fadeSettings(fadeIn, topLevel);
		fadeSettings(fadeAdjust, adjustLevel);
		fadeSettings(fadeColor, colorLevel);
		fadeSettings(fadeEffect, effectLevel);
		fadeSettings(fadeFilter, filterLevel);

		adjustIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, adjustLevel, fadeAdjust);
		});
		aBackIcon.setOnMouseClicked(e -> {
			mouseClicked(adjustLevel, topLevel, fadeIn);
		});
		colorIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, colorLevel, fadeColor);
		});
		cBackIcon.setOnMouseClicked(e -> {
			mouseClicked(colorLevel, topLevel, fadeIn);
		});
		effectIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, effectLevel, fadeEffect);
		});	
		eBackIcon.setOnMouseClicked(e -> {
			mouseClicked(effectLevel, topLevel, fadeIn);
		});		
		filterIcon.setOnMouseClicked(e -> {
			mouseClicked(topLevel, filterLevel, fadeFilter);
		});	
		fBackIcon.setOnMouseClicked(e -> {
			mouseClicked(filterLevel, topLevel, fadeIn);
		});
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
