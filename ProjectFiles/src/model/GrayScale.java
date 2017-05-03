package model;

import javafx.scene.paint.Color;

public class GrayScale extends ColorFilter {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		for(int i = 0; i < newImage.pxImage.length; i++){
			for(int j = 0; j < newImage.pxImage[i].length; j++){
				int avr = (int) ((newImage.pxImage[i][j].getRed()*255 + newImage.pxImage[i][j].getGreen()*255 + newImage.pxImage[i][j].getBlue()*255) / 3);
				newImage.pxImage[i][j] = Color.grayRgb(avr);
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

}
