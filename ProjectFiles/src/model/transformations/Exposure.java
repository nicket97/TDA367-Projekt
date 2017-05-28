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
 * Filter that changes the exposure in the picture
 *
 */
public class Exposure implements Layerable {
	private int factor;

	private boolean hasSettings = true; 
	
	private Label labelFactor = new Label("Exponering");
	private VBox v1 = new VBox();
	private Slider sliderFactor = new Slider(); 
	
	public Exposure(int factor) {
		sliderFactor.setMin(-100);
		sliderFactor.setMax(100);
		
		this.factor = factor;

	}

	public Exposure(String[] args) {
		this(Integer.parseInt(args[1]));

	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {

				Color pxColor = newImage.getpxImage()[i][j];
				double newRed = pxColor.getRed() * 255 + factor;
				double newGreen = pxColor.getGreen() * 255 + factor;
				double newBlue = pxColor.getBlue() * 255 + factor;
				// pxColor = Color.rgb((int) (((newRed) > 255) ? 255 : ((newRed)
				// < 0) ? 0 : newRed), (int) (((newGreen) > 255) ? 255 :
				// newGreen), (int) (((newBlue + b) > 255) ? 255 : newBlue +
				// b));
				pxColor = Color.rgb(getAllowedValue(newRed), getAllowedValue(newGreen), getAllowedValue(newBlue));
				pxImage[i][j] = pxColor;
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	/**
	 * Makes sure no pixels goes above 255 or below 0
	 * @param newColor the color to be checked
	 * @return 255 or 0 if the pixel was above or below allowed numbers
	 */
	private int getAllowedValue(double newColor) {
		if (newColor > 255) {
			newColor = 255;
		} else if (newColor < 0) {
			newColor = 0;
		}
		return (int) newColor;

	}

	
	@Override
	public String saveLayer() {
		String output = "Exposure?" + factor + "?";
		return output;
	}

	
	@Override
	public String getName() {
		return "Exponering";
	}


	/**
	 * Gets the factor for the exposure
	 * @return the factor
	 */
	public int getFactor() {
		return factor;
	}

	/**
	 * Sets the factor for the exposure
	 * @param factor
	 */
	public void setFactor(int factor) {
		this.factor = factor;
	}

	@Override
	public List<VBox> getVBox() {
		initiateVBox(v1);
		sliderFactor.setValue(this.factor);
		v1.getChildren().add(sliderFactor);
		v1.getChildren().add(labelFactor);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		
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
		this.factor = (int) sliderFactor.getValue();
		
	}

	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
