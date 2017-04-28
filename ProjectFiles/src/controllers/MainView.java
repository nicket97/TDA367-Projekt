package controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.Layers;
import main.Main;
import model.Blur;
import model.ColorShift;
import model.GrayScale;
import model.Layer;
import model.LoadedImage;

public class MainView extends AnchorPane implements Initializable {
	
	@FXML
	TilePane bottomContainer;
	@FXML
	AnchorPane bottomPane, 
	canvasPane, 
	miniCanvas, 
	layerPane;
	@FXML
	ToolView toolView;
	
	LayerView layerView;
	static CanvasView canvasView;
	MiniCanvasView miniCanvasView; 
	
	@FXML
	MenuItem openImage;
	@FXML
	MenuItem menuClose;
	@FXML
	MenuItem menuGrayScale;
	@FXML
	MenuItem menuCrop;
	@FXML
	MenuItem menuExposure;
	@FXML
	MenuItem menuContrast;
	@FXML
	MenuItem menuReflect;
	@FXML
	MenuItem menuBlur;
	@FXML
	MenuItem menuSharpen;
	@FXML
	MenuItem menuTextFilter;
	@FXML
	MenuItem menuColorFilter;
	@FXML
	MenuItem menuBlackWhite;
	@FXML
	MenuItem menuWhitebalance;
	@FXML
	MenuItem menuFMatte;
	@FXML
	MenuItem menuFBW;
	@FXML
	MenuItem menuFVintage;

	@FXML
	Button closeButton,miniButton, maxiButton ;
	
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
			fileChooser.setTitle("Open a Image");
			
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
					System.out.println(canvasView.toString());
					setBackgroundImage(ll);
					canvasView.repaint();
			} catch (IOException e1) {
				// On canceled fileopening
			}
		});
		menuClose.setOnAction(e ->{
			System.exit(0);
		});
		
		closeButton.setOnAction(e ->{
			System.exit(0);
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
		menuBlur.setOnAction(e ->{
			layerstack.addLayer(new Layer(new Blur()));
			canvasView.repaint();
		});
		menuGrayScale.setOnAction(e ->{
			layerstack.addLayer(new Layer(new GrayScale()));
			canvasView.repaint();
		});
		menuColorFilter.setOnAction( e->{
			layerstack.addLayer(new Layer(new ColorShift(50,1,1)));
			canvasView.repaint();
		});
}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		canvasView = new CanvasView();
		miniCanvasView = new MiniCanvasView();
		layerView = new LayerView();
		
		bottomContainer.getChildren().add(new ToolView());
		canvasPane.getChildren().add(canvasView);
		miniCanvas.getChildren().add(miniCanvasView);
		layerPane.getChildren().add(layerView);
		
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
