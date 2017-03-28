package model;

import javafx.scene.paint.Color;

public class BlackAndWhite implements Layrable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		for(int i = 0; i < newImage.pxImage.length; i++){
			for(int j = 0; j < newImage.pxImage[i].length; j++){
				
				int argb = img.pxImage[i][j];
				int r = (argb>>16)&0xFF;
				int g = (argb>>8)&0xFF;
				int b = (argb>>0)&0xFF;
				int avr = (r + g + b) / 3;
				newImage.pxImage[i][j] = avr + avr<<6 + avr<<6;
			}
		}
		return newImage;
	}

}
