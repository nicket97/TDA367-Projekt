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
		 kernel[0][1] = -1;
		 kernel[0][2] = 0;
		 kernel[1][0] = -1;
		 kernel[1][1] = 5;
		 kernel[1][2] = -1;
		 kernel[2][0] = 0;
		 kernel[2][1] = -1;
		 kernel[2][2] = 0;
	
		
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
LoadedImage newImage = new LoadedImage(img);
		radius = 1;
		
		for(int i = 1; i < img.getpxImage().length-1; i++) {
			for(int j = 1; j < img.getpxImage()[i].length-1; j++) {
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				int count = 0;
				int x = 0;
				for(int k = -1*radius; k <= radius; k++) {
					int y = 0;
					for (int l = -1*radius; l <= radius; l++) {
						if((i+k) >= 0 && (j+l) >= 0 && (i+k) < img.getpxImage().length && (j+l) < img.getpxImage()[i].length ){
						sumRed += img.getpxImage()[i+k][j+l].getRed()*254*kernel[x][y];
						sumGreen += img.getpxImage()[i+k][j+l].getGreen()*254*kernel[x][y];
						sumBlue += img.getpxImage()[i+k][j+l].getBlue()*254*kernel[x][y];
						count++;
						y++;
						}
						else{
							
						}
						
					}
					x++;
				}
				if(sumRed < 0) sumRed = 0;
				if(sumRed > 255) sumRed = 255;
				if(sumGreen < 0) sumGreen = 0;
				if(sumGreen > 255) sumGreen = 255;
				if(sumBlue < 0) sumBlue = 0;
				if(sumBlue > 255) sumBlue = 255;
				newImage.getpxImage()[i][j]= Color.rgb(sumRed, sumGreen, sumBlue);
				}
		}
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "Sharpen?" + radius + "?";
		return output;
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
