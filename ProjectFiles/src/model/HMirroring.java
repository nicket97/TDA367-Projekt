package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class HMirroring implements Layerable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {
				pxImage[i][j] = img.getpxImage()[img.getpxImage().length - 1 - i][j];
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "HMirroring?";
		return output;
	}

	@Override
	public String getName() {
		return "Spegla Vertikalt";
	}

	@Override
	public List<Slider> getSliders() {
		List<Slider> emptyList = new ArrayList();
		return emptyList;
	}

}
