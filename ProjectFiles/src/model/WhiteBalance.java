package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class WhiteBalance implements Layerable {
	
	private int threshold;
	public WhiteBalance (int threshold){
		this.threshold = threshold;
	}
	public WhiteBalance (String[] args){
		this.threshold = Integer.parseInt(args[1]);
	}
	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		 
        for(int i = 0; i < newImage.getpxImage().length; i++){
            for(int j = 0; j < newImage.getpxImage()[i].length; j++){
            	int pixRed = (int) (img.getpxImage()[i][j].getRed()*255);
				int pixGreen = (int) (img.getpxImage()[i][j].getGreen()*255);
				int pixBlue = (int) (img.getpxImage()[i][j].getBlue()*255);
				
				if((pixRed + pixGreen + pixBlue) < this.threshold){
					pixRed = 0;
					pixGreen = 0;
					pixBlue = 0;
				}
				if((pixRed + pixGreen + pixBlue) > 255*3-this.threshold){
					pixRed = (int) (((pixRed - 255)*0.5) + 255);
					pixGreen = (int) (((pixGreen - 255)*0.5) + 255);
					pixBlue = (int) (((pixBlue - 255)*0.5) + 255);
				}
				newImage.getpxImage()[i][j]= Color.rgb(pixRed, pixGreen, pixBlue);
                
            }
        }
        return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "WhiteBalance?" + threshold + "?";
		return output;
	}

	@Override
	public String getName() {
		
		return "Vitbalans";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	

}
