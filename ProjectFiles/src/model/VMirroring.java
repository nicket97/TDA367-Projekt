package model;

import java.util.List;

import javafx.scene.control.Slider;

public class VMirroring implements Layerable{

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		for(int i = 0; i < newImage.getpxImage().length; i++){
            for(int j = 0; j < newImage.getpxImage()[i].length; j++){
            	newImage.getpxImage()[i][j] = img.getpxImage()[i][img.getpxImage()[1].length-1-j];
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
		return "Vertical mirroring";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
