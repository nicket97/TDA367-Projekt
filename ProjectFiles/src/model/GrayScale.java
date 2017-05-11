package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class GrayScale extends ColorFilter {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		for(int i = 0; i < newImage.getpxImage().length; i++){
			for(int j = 0; j < newImage.getpxImage()[i].length; j++){
				int avr = (int) ((newImage.getpxImage()[i][j].getRed()*255 + newImage.getpxImage()[i][j].getGreen()*255 + newImage.getpxImage()[i][j].getBlue()*255) / 3);
				newImage.getpxImage()[i][j] = Color.grayRgb(avr);
			}
		}
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "GreyScale?";
		return output;
	}

	

	@Override
	public Layer openSavedLayer(String loadString) {
		return new Layer(new GrayScale());
	}

	@Override
	public String getName() {
		return "Gr�skala";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
