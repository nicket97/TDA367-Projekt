package model;

public class MeanBlur implements Layerable {
	double blurValue;
	public MeanBlur(){
		
	}
	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		
		return newImage;
	}
	
}
