package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Edge implements Layerable{
	
	double[][] kernel;
	int radius;
	public Edge(){
		radius = 1;
		kernel = new double[3][3];
		 kernel[0][0] = -1;
		 kernel[0][1] = -1;
		 kernel[0][2] = -1;
		 kernel[1][0] = -1;
		 kernel[1][1] = -8;
		 kernel[1][2] = -1;
		 kernel[2][0] = -1;
		 kernel[2][1] = -1;
		 kernel[2][2] = -1;
	}
	public Edge(String[] args){
		radius = 1;
		kernel = new double[3][3];
		 kernel[0][0] = -1;
		 kernel[0][1] = -1;
		 kernel[0][2] = -1;
		 kernel[1][0] = -1;
		 kernel[1][1] = 8;
		 kernel[1][2] = -1;
		 kernel[2][0] = -1;
		 kernel[2][1] = -1;
		 kernel[2][2] = -1;
	}
	@Override
	public LoadedImage transform(LoadedImage img) {
LoadedImage newImage = new LoadedImage(img);
		
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
						count += kernel[k+1][l+1];
						}
					}
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
		// TODO Auto-generated method stub
		return "Kanter";
	}

	@Override
	public String getName() {
		return "Kanter";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
