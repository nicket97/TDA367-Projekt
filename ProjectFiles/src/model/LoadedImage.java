package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javafx.scene.paint.Color;

public class LoadedImage {
	public final Color[][] pxImage;
	public final BufferedImage lImg;
	public final int width;
	public final int heigth;
	
	public LoadedImage(BufferedImage img){
		this.lImg = img;
		this.width = img.getWidth();
		this.heigth = img.getHeight();
		this.pxImage = new Color[width][heigth];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < heigth; j++){
				int argb =  img.getRGB(i, j);
				int r = (argb>>16)&0xFF;
				int g = (argb>>8)&0xFF;
				int b = (argb>>0)&0xFF;
				pxImage[i][j] = Color.rgb(r, g, b);
			}
		}
		System.out.println("hej" + pxImage[0][0].getRed()*255 + " eller " + ((img.getRGB(0, 0)>>16)&0xFF));
	}
	public LoadedImage(LoadedImage img){
		this.width = img.width;
		this.heigth = img.heigth;
		this.pxImage = new Color[width][heigth];
		for(int i = 0; i < img.pxImage.length; i++){
			for(int j = 0; j < img.pxImage[i].length; j++){
				this.pxImage[i][j] = img.pxImage[i][j];
			}
		}
		BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; i++){
        	for(int j = 0; j < heigth; j++){
        		image.setRGB(i, j, this.getIntFromColor(pxImage[i][j]));
        	}
        }
        this.lImg = image;
		System.out.println("heja" + pxImage[0][0].getRed()*255 + " eller " + ((lImg.getRGB(0, 0)>>16)&0xFF));
	}
	public BufferedImage getBufferedImg(){
		return lImg;
	}
	public int getIntFromColor(Color rgb){
		
	    int R = (int) Math.round(255 * rgb.getRed());
	    int G = (int) Math.round(255 * rgb.getGreen());
	    int B = (int) Math.round(255 * rgb.getBlue());

	    R = (R << 16) & 0x00FF0000;
	    G = (G << 8) & 0x0000FF00;
	    B = B & 0x000000FF;

	    return 0xFF000000 | R | G | B;
	}
}
