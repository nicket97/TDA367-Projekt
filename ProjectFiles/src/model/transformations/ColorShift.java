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
	
	HBox colorBox = new HBox();
	
	public ColorShift(double redAddend, double greenAddend, double blueAddend, double intensity) {
		
		this.r = redAddend;
		this.g = greenAddend;
		this.b = blueAddend;
		this.intensity = intensity;
		
		yellowButton.setToggleGroup(colorGroup);
		yellowButton.setStyle("-fx-background-color: yellow;");
		
		orangeButton.setToggleGroup(colorGroup);
		orangeButton.setStyle("-fx-background-color: orange;");
		
		blueButton.setToggleGroup(colorGroup);
		blueButton.setStyle("-fx-background-color: royalblue;");
		
		redButton.setToggleGroup(colorGroup);
		redButton.setStyle("-fx-background-color: red;");
		
		pinkButton.setToggleGroup(colorGroup);
		pinkButton.setStyle("-fx-background-color: hotpink;");
		
		purpleButton.setToggleGroup(colorGroup);
		purpleButton.setStyle("-fx-background-color: blueviolet;");
		
		turquoiseButton.setToggleGroup(colorGroup);
		turquoiseButton.setStyle("-fx-background-color: cyan;");
		
		greenButton.setToggleGroup(colorGroup);
		greenButton.setStyle("-fx-background-color: limegreen;");
		
		colorBox.getChildren().addAll(yellowButton, orangeButton, blueButton, redButton, pinkButton,
				purpleButton, turquoiseButton, greenButton);
		/**colorBox.getChildren().add(orangeButton);
		colorBox.getChildren().add(blueButton);
		colorBox.getChildren().add(redButton);
		colorBox.getChildren().add(pinkButton);
		colorBox.getChildren().add(purpleButton);
		colorBox.getChildren().add(turquoiseButton);
		colorBox.getChildren().add(greenButton);*/
		
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
		System.out.println("kijkljsakjlk" + r * intensity + "   " + intensity + "    " + r);
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {
				
				Color pxColor = pxImage[i][j];
				double newRed = pxColor.getRed()*255 + (r * intensity);
				double newGreen = pxColor.getGreen()*255  + (g * intensity);
				double newBlue = pxColor.getBlue()*255  + (b * intensity);
				// pxColor = Color.rgb((int) (((newRed) > 255) ? 255 : ((newRed)
				// < 0) ? 0 : newRed), (int) (((newGreen) > 255) ? 255 :
				// newGreen), (int) (((newBlue + b) > 255) ? 255 : newBlue +
				// b));
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
		initiateVBox(v1);
		initiateVBox(v2);
		initiateVBox(v3);
		/**v1.getChildren().clear();
		v2.getChildren().clear();
		v3.getChildren().clear();
		v1.setTranslateY(50);
		v2.setTranslateY(50);
		v3.setTranslateY(50);*/
		
		v1.getChildren().addAll(yellowButton, orangeButton, blueButton, redButton, pinkButton,
				purpleButton, turquoiseButton, greenButton, labelColor);
		/**v1.getChildren().add(orangeButton);
		v1.getChildren().add(blueButton);
		v1.getChildren().add(redButton);
		v1.getChildren().add(pinkButton);
		v1.getChildren().add(purpleButton);
		v1.getChildren().add(turquoiseButton);
		v1.getChildren().add(greenButton);*/
		v1.getChildren().add(labelColor);
		
		customColor.setValue(Color.rgb((int)this.r*255, (int)this.g*255, (int)this.b*255));
		v2.getChildren().addAll(customColor, labelCustomColor);
		//v2.getChildren().add(labelCustomColor);
		
		sliderIntensity.setValue(this.intensity);
		v3.getChildren().addAll(sliderIntensity, labelIntensity);
		//v3.getChildren().add(labelIntensity);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		vboxList.add(v2);
		vboxList.add(v3);
		return vboxList;
	}

	@Override
	public void uppdate() {
		if (customColor.getValue() != null) {
			System.out.println("customcolor");
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

	private void initiateVBox(VBox v) {
		v.getChildren().clear();
		v.setTranslateY(45);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
	}

}
