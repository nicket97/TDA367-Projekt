package main;

public class BlackAndWhite implements Layrable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		for(int i = 0; i < newImage.pxImage.length; i++){
			for(int j = 0; j < newImage.pxImage[i].length; j++){
				newImage.pxImage[i][j];
			}
		}
		return newImage;
	}

}
