package model;

import java.awt.image.BufferedImage;

public class LoadedImage {
	public int[][] pxImage;
	public BufferedImage lImg;
	public int width;
	public int heigth;
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
		this.pxImage = new int[width][heigth];
		for(int i = 0; i < img.pxImage.length; i++){
			for(int j = 0; j < img.pxImage[i].length; j++){
				this.pxImage[i][j] = img.pxImage[i][j];
			}
		}
		
	}
}
