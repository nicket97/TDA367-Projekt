package model.transformations;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Filter that makes the picture grayscale
 *
 */
public class GrayScale implements Layerable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {
				int avr = (int) ((newImage.getpxImage()[i][j].getRed() * 255
						+ newImage.getpxImage()[i][j].getGreen() * 255 + newImage.getpxImage()[i][j].getBlue() * 255)
						/ 3);
				pxImage[i][j] = Color.grayRgb(avr);
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "GreyScale?";
		return output;
	}

	@Override
	public String getName() {
		return "Gråskala";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
