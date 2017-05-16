package model;

import java.util.List;
import java.util.Random;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Grain implements Layerable {
	
	private int diviation;
	
	public Grain(int diviation){
		this.diviation = diviation;
	}
	public Grain(String[] args){
		this.diviation = Integer.parseInt(args[1]);
	}
	@Override
	public LoadedImage transform(LoadedImage img) {
		 LoadedImage newImage = new LoadedImage(img);
		 Random r = new Random();
         for(int i = 0; i < newImage.getpxImage().length; i++){
             for(int j = 0; j < newImage.getpxImage()[i].length; j++){
            	 
            	 int factor = r.nextInt(2);
            	 factor = (factor -1) * diviation;
                
            	 Color pxColor = newImage.getpxImage()[i][j];
				 double newRed = pxColor.getRed() * 255 + factor;
				 double newGreen = pxColor.getGreen() * 255 + factor;
				 double newBlue = pxColor.getBlue() * 255 + factor;
                 //pxColor = Color.rgb((int) (((newRed) > 255) ? 255 : ((newRed) < 0) ? 0 : newRed), (int) (((newGreen) > 255) ? 255 : newGreen), (int) (((newBlue + b) > 255) ? 255 : newBlue + b));
				 pxColor = Color.rgb(ColorTransformTest.getAllowedValue(newRed), ColorTransformTest.getAllowedValue(newGreen), ColorTransformTest.getAllowedValue(newBlue));
				 newImage.getpxImage()[i][j] = pxColor;
             }
         }
         return newImage;
	}

	@Override
	public String saveLayer() {
		// TODO Auto-generated method stub
		return "Grain?"+diviation;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Grain";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getDiviation() {
		return diviation;
	}
	public void setDiviation(int diviation) {
		this.diviation = diviation;
	}
	

}
