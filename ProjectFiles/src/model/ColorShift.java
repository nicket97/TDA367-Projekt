package model;

import javafx.scene.paint.Color;

public class ColorShift extends ColorFilter{

		private double r;
		private double g;
		private double b;
		 
	     public ColorShift(double redFactor, double greenFactor, double blueFactor) {
	         this.r = r;
	         this.g = g;
	         this.b = b;
	     }
	 
	     public LoadedImage transform(LoadedImage img) {
	    	 LoadedImage newImage = new LoadedImage(img);
	 
	         for(int i = 0; i < newImage.pxImage.length; i++){
	             for(int j = 0; j < newImage.pxImage[i].length; j++){
	 
	                 Color pxColor = newImage.pxImage[i][j];
	                 pxColor = Color.rgb((int) (pxColor.getRed() * r), (int) (pxColor.getGreen() * g), (int) (pxColor.getBlue() * b));
	             }
	         }
	         return newImage;
	     }
	 

}
