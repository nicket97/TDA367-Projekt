package model;


import java.awt.Point;
import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Crop implements Layerable {
	private Point topLeft;
	private Point bottomRight;
	
	public Crop(Point topLeft, Point bottomRight){
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		int height = (int) (bottomRight.getY()-topLeft.getY());
		int width = (int) (bottomRight.getX()-topLeft.getX());
		Color[][] newSize = new Color[width][height];
		
		for (int i = 0; i < newSize.length; i++) {
			for (int j = 0; j < height; j++) {
				newSize[i][j] = img.getpxImage()[(int) topLeft.getX()][(int) topLeft.getY()];
			}
		}
		for (int k = 0; k < newSize.length; k++) {
			for (int l = 0; l < height; l++) {
				newImage.getpxImage()[k][l] = newSize[k][l];
			}
		}


		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "Crop?" + topLeft + "?" + bottomRight + "?";
		return output;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Beskärning";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
