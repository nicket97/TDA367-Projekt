package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Exposure implements Layerable{
	private int factor;
	public Exposure(int factor){
		this.factor = factor;
		
	}
	
		public Exposure(String[] args){
			this.factor = Integer.parseInt(args[1]);
			
		}
	@Override
	public LoadedImage transform(LoadedImage img) {
		 LoadedImage newImage = new LoadedImage(img);
		 
         for(int i = 0; i < newImage.getpxImage().length; i++){
             for(int j = 0; j < newImage.getpxImage()[i].length; j++){
 
                 Color pxColor = newImage.getpxImage()[i][j];
				 double newRed = pxColor.getRed() * 255 + factor;
				 double newGreen = pxColor.getGreen() * 255 + factor;
				 double newBlue = pxColor.getBlue() * 255 + factor;
                 //pxColor = Color.rgb((int) (((newRed) > 255) ? 255 : ((newRed) < 0) ? 0 : newRed), (int) (((newGreen) > 255) ? 255 : newGreen), (int) (((newBlue + b) > 255) ? 255 : newBlue + b));
				 pxColor = Color.rgb(getAllowedValue(newRed), getAllowedValue(newGreen), getAllowedValue(newBlue));
				 newImage.getpxImage()[i][j] = pxColor;
             }
         }
         return newImage;
	}
	private int getAllowedValue(double newColor) {
		if (newColor > 255) {
			newColor = 255;
		}
		else if (newColor < 0) {
			newColor = 0;
		}
		return (int) newColor;

	}
	@Override
	public String saveLayer() {
		String output = "Exposure?" + factor + "?"; 
		return output;
	}

	@Override
	public String getName() {
		return "Exponering";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}
	

}
