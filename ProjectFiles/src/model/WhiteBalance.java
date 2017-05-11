package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class WhiteBalance implements Layerable {
	
	int threshold;
	public WhiteBalance (int threshold){
		this.threshold = threshold;
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
				if((pixRed + pixGreen + pixBlue) > 255-this.threshold){
					pixRed = 255;
					pixGreen = 255;
					pixBlue = 255;
				}
				newImage.getpxImage()[i][j]= Color.rgb(pixRed, pixGreen, pixBlue);
                
            }
        }
        return newImage;
	}

	@Override
	public String saveLayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		
		return "VitBalans";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
