package main;

import java.awt.image.BufferedImage;

public class LoadedImage {
	int[][] pxImage;
	BufferedImage lImg;
	int width;
	int heigth;
	public LoadedImage(BufferedImage img){
		lImg = img;
		width = img.getWidth();
		heigth = img.getHeight();
		pxImage = new int[width][heigth];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < heigth; j++){
				pxImage[i][j] = img.getRGB(i, j);
			}
		}
	}
	public LoadedImage(LoadedImage img){
		this.lImg = img.lImg;
		this.width = img.width;
		this.heigth = img.heigth;
		for(int i = 0; i < img.pxImage.length; i++){
			for(int j = 0; j < img.pxImage[i].length; j++){
				this.pxImage[i][j] = img.pxImage[i][j];
			}
		}
	}
}
