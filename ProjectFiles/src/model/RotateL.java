package model;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.scene.control.Slider;

public class RotateL implements Layerable{

	@Override
	public LoadedImage transform(LoadedImage img) {
		BufferedImage newImage = new BufferedImage(img.getHeigth(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < img.getWidth(); i++){
			for(int j = 0; j < img.getHeigth(); j++){
				newImage.setRGB(i, j, LoadedImage.getIntFromColor(img.getpxImage()[j][i]));
			}
		}
		return new LoadedImage(newImage);
	}

	@Override
	public String saveLayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
