package model;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Mirroring implements Layerable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		for(int i = 0; i < newImage.pxImage.length; i++){
            for(int j = 0; j < newImage.pxImage[i].length; j++){
            	newImage.pxImage[i][j] = img.pxImage[newImage.pxImage.length - i][newImage.pxImage[i].length - j];
            }
        }
		
		return newImage;
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
