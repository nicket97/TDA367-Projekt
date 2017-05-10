package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
		String output = "Mirroring?"; 
		return output;
	}

	@Override
	public String getName() {
		return "Mirroring";
	}

	@Override
	public List<Slider> getSliders() {
		List<Slider> emptyList = new ArrayList();
		return emptyList;
	}
	
}
