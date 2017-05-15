package model;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.scene.control.Slider;

public class RotateL implements Layerable{

	@Override
	public LoadedImage transform(LoadedImage img) {
		BufferedImage newImage = new BufferedImage(img.getHeigth(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < img.getHeigth(); i++){
			for(int j = 0; j < img.getWidth(); j++){
				newImage.setRGB(i, img.getWidth()-1-j, LoadedImage.getIntFromColor(img.getpxImage()[j][i]));
			}
		}
		return new LoadedImage(newImage);
	}

	@Override
	public String saveLayer() {
		String output = "RotateL?";
		return output;
	}

	@Override
	public String getName() {
		return "Rotera 90\u00b0 vänster";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
