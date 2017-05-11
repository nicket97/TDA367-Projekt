package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Contrast implements Layerable{
	int threshold;
	double factor;
	
	public Contrast(int threshold, double factor) {
		this.threshold = threshold;
		this.factor = factor;
		
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		for (int i = 0; i < img.getpxImage().length; i++) {
			for (int j = 0; j < img.getpxImage()[1].length; j++){
				int pixRed = (int) (img.getpxImage()[i][j].getRed()*255);
				int pixGreen = (int) (img.getpxImage()[i][j].getGreen()*255);
				int pixBlue = (int) (img.getpxImage()[i][j].getBlue()*255);
				
				if (pixRed < threshold) {
					pixRed = (int) (pixRed/factor);
				}
				if (pixRed >= threshold){
					pixRed = (int) (pixRed*factor);
					
				}
				if (pixGreen < threshold) {
					pixGreen = (int) (pixGreen/factor);
					
				}
				if (pixGreen >= threshold) {
					pixGreen = (int) (pixGreen*factor);
					
				}
				if (pixBlue < threshold) {
					pixBlue = (int) (pixBlue/factor);
					
				}
				if (pixBlue >= threshold) {
					pixBlue = (int) (pixBlue*factor);
					
				}
				//System.out.println("red: " + pixRed + "blue: " + pixBlue + "green: " + pixGreen);
				if(pixRed > 255) pixRed = 255;
				if(pixGreen > 255) pixGreen = 255;
				if(pixBlue > 255) pixBlue = 255;
				newImage.getpxImage()[i][j]= Color.rgb(pixRed, pixGreen, pixBlue);
			}
			
		}
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "Contrast?" + threshold + "?" + factor + "?";
		return output;
	}

	@Override
	public String getName() {
		return "Kontrast";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
