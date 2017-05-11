package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Levels implements Layerable{
	int maxLevel;
	int minLevel;
	int midLevel;
	double changeLevel;
	
	public Levels(int maxLevel, int minLevel) {
		this.maxLevel = maxLevel;
		this.minLevel = minLevel;
		this.midLevel = (maxLevel + minLevel)/2;
		this.changeLevel = ((minLevel/midLevel) + (midLevel/maxLevel))/2;
		
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		changeLevel = 0.5;
		for (int i = 0; i < img.getpxImage().length; i++) {
			for (int j = 0; j < img.getpxImage()[1].length; j++){
				double pixRed = (((img.getpxImage()[i][j].getRed()*255)-midLevel)*changeLevel) + midLevel;
				double pixGreen = (((img.getpxImage()[i][j].getGreen()*255)-midLevel)*changeLevel) + midLevel;
				double pixBlue = (((img.getpxImage()[i][j].getBlue()*255)-midLevel)*changeLevel) + midLevel;
				newImage.getpxImage()[i][j]= Color.rgb((int)pixRed, (int)pixGreen, (int)pixBlue);
				}
		      }
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "Levels?";
		return output;
	}

	@Override
	public String getName() {
		return "Nivåer";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
