package model;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.scene.control.Slider;

public class RotateR implements Layerable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		BufferedImage newImage = new BufferedImage(img.getHeigth(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < img.getWidth(); i++){
			for(int j = 0; j < img.getHeigth(); j++){
				newImage.setRGB(img.getHeigth()-1-j, i, LoadedImage.getIntFromColor(img.getpxImage()[i][j]));
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
		return "Rotera 90 grader Höger";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
