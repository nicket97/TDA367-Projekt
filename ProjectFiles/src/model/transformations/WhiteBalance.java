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
import model.transformations.help.ColorTransformTest;

/**
 * Filter that changes the white balance in the picture
 *
 */
public class WhiteBalance implements Layerable {
	
	private boolean hasSettings = true;

	private int threshold;

	 
    private Slider sliderThreshold = new Slider();
    private Label labelText = new Label();
	private VBox v1 = new VBox();

	public WhiteBalance(int threshold) {
		sliderThreshold.setMin(1);
		sliderThreshold.setMax(100);
		labelText.setText("Värme");
		this.threshold = threshold;
	}

	public WhiteBalance(String[] args) {
		this(Integer.parseInt(args[1]));
	}

	/** 
	 * Changes the white balance of the image
	 */
	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {
				int pixRed = (int) (img.getpxImage()[i][j].getRed() * 255);
				int pixGreen = (int) (img.getpxImage()[i][j].getGreen() * 255);
				int pixBlue = (int) (img.getpxImage()[i][j].getBlue() * 255);

				pixRed += threshold - 50;
				pixBlue -= threshold - 50;
				pxImage[i][j] = Color.rgb(ColorTransformTest.getAllowedValue(pixRed),
						ColorTransformTest.getAllowedValue(pixGreen), ColorTransformTest.getAllowedValue(pixBlue));

			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	/**
	 * {@inheritDoc}	
	 */
	@Override
	public String saveLayer() {
		String output = "WhiteBalance?" + threshold + "?";
		return output;
	}

	/**
	 * {@inheritDoc}	
	 */
	@Override
	public String getName() {

		return "Vitbalans";
	}


	/**
	 * Gets the threshold of the filter
	 * @return threshold
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * Sets threshold of the filter
	 * @param threshold new threshold
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/**
	 * Gets the vBox
	 */
	@Override
	public List<VBox> getVBox() {
		initiateVBox(v1);
		sliderThreshold.setValue(this.threshold);
		v1.getChildren().add(sliderThreshold);
		v1.getChildren().add(labelText);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		
		return vboxList;
	}
	
	/**
	 * Initiates the vBox
	 * @param v the vBox
	 */
	private void initiateVBox(VBox v) {
		v.getChildren().clear();
		v.setTranslateY(45);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
	}

	/**
	 * Updates the filter
	 */
	@Override
	public void uppdate() {
		this.threshold = (int) sliderThreshold.getValue();
	}

	/**
	 * Gets the settings for filter
	 */
	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
