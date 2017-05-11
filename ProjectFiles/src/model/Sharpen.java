package model;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Sharpen implements Layerable {
	int radius;
	double[][] kernel;

	public Sharpen(){
		
		 kernel = new double[3][3];
		 kernel[0][0] = 0;
		 kernel[0][1] = -2;
		 kernel[0][2] = 0;
		 kernel[1][0] = -2;
		 kernel[1][1] = 10;
		 kernel[1][2] = -2;
		 kernel[2][0] = 0;
		 kernel[2][1] = -2;
		 kernel[2][2] = 0;
	
		
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
LoadedImage newImage = new LoadedImage(img);
		radius = 1;
		
		for(int i = 0; i < img.getpxImage().length; i++) {
			for(int j = 0; j < img.getpxImage()[i].length; j++) {
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				int count = 0;
				for(int k = -1*radius; k < radius; k++) {
					for (int l = -1*radius; l < radius; l++) {
						if((i+k) >= 0 && (j+l) >= 0 && (i+k) < img.getpxImage().length && (j+l) < img.getpxImage()[i].length ){
						sumRed += img.getpxImage()[i+k][j+l].getRed()*255;
						sumGreen += img.getpxImage()[i+k][j+l].getGreen()*255;
						sumBlue += img.getpxImage()[i+k][j+l].getBlue()*255;
						count++;
						}
					}
				}
				newImage.getpxImage()[i][j]= Color.rgb(sumRed/count, sumGreen / count, sumBlue/count);
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
		return "Sharpen";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
