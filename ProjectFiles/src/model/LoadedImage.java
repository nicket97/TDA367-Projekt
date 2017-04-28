package model;

import java.awt.image.BufferedImage;

import javafx.scene.paint.Color;

public class LoadedImage {
	public Color[][] pxImage;
	public BufferedImage lImg;
	public int width;
	public int heigth;
	public LoadedImage(BufferedImage img){
		lImg = img;
		width = img.getWidth();
		heigth = img.getHeight();
		pxImage = new Color[width][heigth];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < heigth; j++){
				int argb =  img.getRGB(i, j);
				int r = (argb>>16)&0xFF;
				int g = (argb>>8)&0xFF;
				int b = (argb>>0)&0xFF;
				pxImage[i][j] = Color.rgb(r, g, b);
			}
		}
	}
	public LoadedImage(LoadedImage img){
		this.lImg = img.lImg;
		this.width = img.width;
		this.heigth = img.heigth;
		this.pxImage = new Color[width][heigth];
		for(int i = 0; i < img.pxImage.length; i++){
			for(int j = 0; j < img.pxImage[i].length; j++){
				this.pxImage[i][j] = img.pxImage[i][j];
			}
		}
		
	}
	public BufferedImage getBufferedImg(LoadedImage img){
		return lImg;
	}
}
