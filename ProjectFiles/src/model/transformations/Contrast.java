package model.transformations;

import java.util.List;

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

	public Contrast(int threshold, double factor) {
		this.threshold = threshold;
		this.factor = factor;

	}

	public Contrast(String[] args) {
		threshold = Integer.parseInt(args[1]);
		factor = Double.parseDouble(args[2]);
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


	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public void setFactorAndThreshold(int threshold, double factor) {
		this.threshold = threshold;
		this.factor = factor;
	}

	@Override
	public List<VBox> getVBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void uppdate() {
		// TODO Auto-generated method stub
		
	}

}
