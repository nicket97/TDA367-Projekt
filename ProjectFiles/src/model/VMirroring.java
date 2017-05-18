package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

/**
 * Flips the image vertically
 *
 */
public class VMirroring implements Layerable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {
				pxImage[i][j] = img.getpxImage()[i][img.getpxImage()[1].length - 1 - j];
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "VMirroring?";
		return output;
	}

	@Override
	public String getName() {
		return "Spegla Horisontellt";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
