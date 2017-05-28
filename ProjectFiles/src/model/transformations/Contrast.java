package model.transformations;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Filter that changes the contrast in the picture
 */
public class Contrast implements Layerable {
	private int threshold;
	private double factor;
	
	private boolean hasSettings = true;
	
	private Slider sliderThreshold = new Slider();
	private Label labelThreshold = new Label("Tröskelvärde");
	
	private Slider sliderFactor = new Slider();
	private Label labelFactor = new Label("Intensitet");
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();

	public Contrast(int threshold, double factor) {
		sliderThreshold.setMin(1);
		sliderThreshold.setMax(255);
		
		sliderFactor.setMin(1);
		sliderFactor.setMax(2);
		
		this.threshold = threshold;
		this.factor = factor;

	}

	public Contrast(String[] args) {
		this( Integer.parseInt(args[1]),Double.parseDouble(args[2]) );

	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		System.out.println("Exposure" + threshold + "    " + factor);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < img.getpxImage().length; i++) {
			for (int j = 0; j < img.getpxImage()[1].length; j++) {
				int pixRed = (int) (img.getpxImage()[i][j].getRed() * 255);
				int pixGreen = (int) (img.getpxImage()[i][j].getGreen() * 255);
				int pixBlue = (int) (img.getpxImage()[i][j].getBlue() * 255);

				if (pixRed < threshold) {
					pixRed = (int) (pixRed / factor);
				}
				if (pixRed >= threshold) {
					pixRed = (int) (pixRed * factor);

				}
				if (pixGreen < threshold) {
					pixGreen = (int) (pixGreen / factor);

				}
				if (pixGreen >= threshold) {
					pixGreen = (int) (pixGreen * factor);

				}
				if (pixBlue < threshold) {
					pixBlue = (int) (pixBlue / factor);

				}
				if (pixBlue >= threshold) {
					pixBlue = (int) (pixBlue * factor);

				}
				// System.out.println("red: " + pixRed + "blue: " + pixBlue +
				// "green: " + pixGreen);
				if (pixRed > 255)
					pixRed = 255;
				if (pixGreen > 255)
					pixGreen = 255;
				if (pixBlue > 255)
					pixBlue = 255;
				pxImage[i][j] = Color.rgb(pixRed, pixGreen, pixBlue);
			}

		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	
	@Override
	public String saveLayer() {
		String output = "Contrast?" + threshold + "?" + factor + "?";
		return output;
	}

	
	@Override
	public String getName() {
		return "Kontrast";
	}


	/** 
	 * Gets the threshold for the filter
	 * @return threshold of the filter
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * Sets threshold for the filter
	 * @param threshold new threshold to be set
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/**
	 * Gets the factor of the filter
	 * @return factor
	 */
	public double getFactor() {
		return factor;
	}

	/**
	 * Sets the factor of the filter
	 * @param new factor to be set
	 */
	public void setFactor(double factor) {
		this.factor = factor;
	}

	/**
	 * Sets factor and threshold together
	 * @param threshold threshold to be set
	 * @param factor factor to be set
	 */
	public void setFactorAndThreshold(int threshold, double factor) {
		this.threshold = threshold;
		this.factor = factor;
	}

	@Override
	public List<VBox> getVBox() {
		initiateVBox(v1);
		initiateVBox(v2);
		sliderThreshold.setValue(this.threshold);
		v1.getChildren().add(sliderThreshold);
		v1.getChildren().add(labelThreshold);
		sliderFactor.setValue(this.factor);
		v2.getChildren().add(sliderFactor);
		v2.getChildren().add(labelFactor);

		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		vboxList.add(v2);
		
		return vboxList;
	}

	private void initiateVBox(VBox v) {
		v.getChildren().clear();
		v.setTranslateY(45);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
	}

	@Override
	public void uppdate() {
		this.threshold = (int) sliderThreshold.getValue();
		this.factor = sliderFactor.getValue();
		
	}

	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
