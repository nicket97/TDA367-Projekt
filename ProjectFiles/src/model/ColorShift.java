package model;

import javafx.scene.paint.Color;

public class ColorShift extends ColorFilter{

		private double r;
		private double g;
		private double b;
		 
	    

		public ColorShift(double redAddend, double greenAddend, double blueAddend) {
	         this.r = redAddend;
	         this.g = greenAddend;
	         this.b = blueAddend;
	     }
	 
	     public LoadedImage transform(LoadedImage img) {
	    	 LoadedImage newImage = new LoadedImage(img);
	 
	         for(int i = 0; i < newImage.pxImage.length; i++){
	             for(int j = 0; j < newImage.pxImage[i].length; j++){
	 
	                 Color pxColor = newImage.pxImage[i][j];
	                 pxColor = Color.rgb((int) (pxColor.getRed() + r), (int) (pxColor.getGreen() + g), (int) (pxColor.getBlue() + b));
	                 newImage.pxImage[i][j] = pxColor;
	             }
	         }
	         return newImage;
	     }
	     public double getR() {
				return r;
		}

		public void setR(double r) {
			this.r = r;
		}

		public double getG() {
			return g;
		}

		public void setG(double g) {
			this.g = g;
		}

		public double getB() {
			return b;
		}

		public void setB(double b) {
			this.b = b;
		}
	 

}
