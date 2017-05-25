package model.transformations;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;
import model.transformations.help.ColorTransformTest;

/**
 * Shifts the colors in a picture
 *
 */

public class ColorShift implements Layerable {

	private double r;
	private double g;
	private double b;
	private double intensity;
	
	private boolean hasSettings = true;
	
	private HBox h1 = new HBox();
	
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();
	private VBox v3 = new VBox();
	
	private ColorPicker customColor = new ColorPicker();
	private Label labelCustomColor = new Label("Välj egen...");
	
	private Slider sliderIntensity = new Slider();
	private Label labelIntensity = new Label("Intensitet");
	
	private Label labelColor = new Label("Välj färg");
	private ToggleGroup colorGroup = new ToggleGroup();
	
	private RadioButton yellowButton = new RadioButton();
	private RadioButton orangeButton = new RadioButton();
	private RadioButton blueButton = new RadioButton();
	private RadioButton redButton = new RadioButton();
	private RadioButton pinkButton = new RadioButton();
	private RadioButton purpleButton = new RadioButton();
	private RadioButton turquoiseButton = new RadioButton();
	private RadioButton greenButton = new RadioButton();
	
	List<RadioButton> colorButtons;
	
	public ColorShift(double redAdded, double greenAdded, double blueAdded, double intensity) {
		
		this.r = redAdded;
		this.g = greenAdded;
		this.b = blueAdded;
		this.intensity = intensity;
		
		colorButtons = new ArrayList<RadioButton>();
		colorButtons.add(yellowButton);
		colorButtons.add(orangeButton);
		colorButtons.add(blueButton);
		colorButtons.add(redButton);
		colorButtons.add(pinkButton);
		colorButtons.add(purpleButton);
		colorButtons.add(turquoiseButton);
		colorButtons.add(greenButton);
		
		for (RadioButton r : colorButtons){
			r.setToggleGroup(colorGroup);
		}
		
		yellowButton.setStyle("-fx-background-color: yellow;");
		orangeButton.setStyle("-fx-background-color: orange;");
		blueButton.setStyle("-fx-background-color: royalblue;");
		redButton.setStyle("-fx-background-color: red;");
		pinkButton.setStyle("-fx-background-color: hotpink;");
		purpleButton.setStyle("-fx-background-color: blueviolet;");
		turquoiseButton.setStyle("-fx-background-color: cyan;");
		greenButton.setStyle("-fx-background-color: limegreen;");
		
		sliderIntensity.setMin(0);
		sliderIntensity.setMax(1);	
	}

	public ColorShift(String[] arg) {
		this(Double.parseDouble(arg[1]),Double.parseDouble(arg[2]), Double.parseDouble(arg[3]) , Double.parseDouble(arg[4]));

	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = img.getpxImage();
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {
				
				Color pxColor = pxImage[i][j];
				double newRed = pxColor.getRed()*255 + (r * intensity);
				double newGreen = pxColor.getGreen()*255  + (g * intensity);
				double newBlue = pxColor.getBlue()*255  + (b * intensity);

				pxColor = Color.rgb(ColorTransformTest.getAllowedValue(newRed),
						ColorTransformTest.getAllowedValue(newGreen), ColorTransformTest.getAllowedValue(newBlue));
				pxImage[i][j] = pxColor;
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getIntensity() {
		return intensity;
	}

	public void setIntesity(double intensity) {
		this.intensity = intensity;
	}

	public void setRGB(double r, double g, double b, double value) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.intensity = value;
	}

	@Override
	public String saveLayer() {
		String output = "ColorShift?" + r + "?" + g + "?" + b + "?" + intensity + "?";
		return output;
	}

	@Override
	public String getName() {
		return "Färgfilter";
	}

	@Override
	public List<VBox> getVBox() {
		initiateVBox(v1, 35);
		initiateVBox(v2, 20);
		initiateVBox(v3, 45);
		h1.getChildren().clear();
		
		for (RadioButton r : colorButtons){
			setStyling(r);
		}
		
		yellowButton.setUserData("yellow");
 		orangeButton.setUserData("orange");
 		blueButton.setUserData("blue");
 		redButton.setUserData("red");
 		pinkButton.setUserData("pink");
 		purpleButton.setUserData("purple");
 		turquoiseButton.setUserData("turquoise");
 		greenButton.setUserData("green");

		h1.getChildren().addAll(colorButtons);
		v1.getChildren().addAll(h1, labelColor);
		h1.setSpacing(5);
		
		//customColor.setValue(Color.rgb((int)this.r*255, (int)this.g*255, (int)this.b*255));
		
		customColor.setValue(null);
		v2.getChildren().addAll(customColor, labelCustomColor);
		sliderIntensity.setValue(this.intensity);
		v3.getChildren().addAll(sliderIntensity, labelIntensity);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		vboxList.add(v2);
		vboxList.add(v3);
		return vboxList;
	}

	@Override
	public void uppdate() {
		if (customColor.getValue() != null) {
			this.setRGB(customColor.getValue().getRed()*255,
					customColor.getValue().getGreen()*255, customColor.getValue().getBlue()*255,
					sliderIntensity.valueProperty().doubleValue());
			colorGroup.selectToggle(null);
		} else {
			this.intensity = sliderIntensity.getValue();
			getDefinedColorShift((String)colorGroup.getSelectedToggle().getUserData(), this.intensity);
		}
		
	}

	private void getDefinedColorShift(String color, double d) {
		if (color.equals("yellow")) {
			this.setRGB(25, 25, 0, d);
		} else if (color.equals("orange")) {
			this.setRGB(25, 0, -25, d);
		} else if (color.equals("blue")) {
			this.setRGB(-25, -25, 50, d);
		} else if (color.equals("red")) {
			this.setRGB(25, -25, -25, d);
		} else if (color.equals("pink")) {
			this.setRGB(35, 10, 15, d);
		} else if (color.equals("purple")) {
			this.setRGB(25, -10, 30, d);
		} else if (color.equals("turquoise")) {
			this.setRGB(0, 25, 25, d);
		} else if (color.equals("green")) {
			this.setRGB(-25, 50, -25, d);
		}
		
	}

	private void initiateVBox(VBox v, double d) {
		v.getChildren().clear();
		v.setTranslateY(d);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
	}
	
	private void setStyling(RadioButton l){
		l.setId("colorButton");
	}

	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
