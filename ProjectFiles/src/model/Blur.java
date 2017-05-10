package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Blur implements Layerable {
	int radius;
	double[][] kernel;
	public Blur(int r){
		radius = r;
		if (radius % 2 == 0) {
			radius++;
		}
		 kernel = new double[2*radius+1][2*radius+1];
		
		for (int i = 0; i < 2*radius+1; i++) {
			for (int j = 0; j < 2*radius+1; j++ ) {
				kernel[i][j] = 1;
			}
		}
		
	
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		
		for(int i = 0; i < img.pxImage.length; i++) {
			for(int j = 0; j < img.pxImage[i].length; j++) {
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				int count = 0;
				for(int k = -1*radius; k < radius; k++) {
					for (int l = -1*radius; l < radius; l++) {
						if((i+k) >= 0 && (j+l) >= 0 && (i+k) < img.pxImage.length && (j+l) < img.pxImage[i].length ){
						sumRed += img.pxImage[i+k][j+l].getRed()*255;
						sumGreen += img.pxImage[i+k][j+l].getGreen()*255;
						sumBlue += img.pxImage[i+k][j+l].getBlue()*255;
						count++;
						}
					}
				}
				newImage.pxImage[i][j]= Color.rgb(sumRed/count, sumGreen / count, sumBlue/count);
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
		
		return "Blur";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}
	
}