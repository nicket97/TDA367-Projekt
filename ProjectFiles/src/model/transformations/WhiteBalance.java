package model.transformations;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;
import model.transformations.help.ColorTransformTest;

/**
 * Filter that changes the white balance in the picture
 *
 */
public class WhiteBalance implements Layerable {

	private int threshold;

	public WhiteBalance(int threshold) {
		this.threshold = threshold;
	}

	public WhiteBalance(String[] args) {
		this.threshold = Integer.parseInt(args[1]);
	}

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

	@Override
	public String saveLayer() {
		String output = "WhiteBalance?" + threshold + "?";
		return output;
	}

	@Override
	public String getName() {

		return "Vitbalans";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

}
