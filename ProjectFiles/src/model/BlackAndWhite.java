package model;


public class BlackAndWhite extends ColorFilter {
    private int threshold;

    public BlackAndWhite(int threshold) {
        this.threshold = threshold;
    }

    public LoadedImage transform(LoadedImage img) {
        LoadedImage newImage = new LoadedImage(img);

        //.....

        return newImage;
    }

    @Override
    public String saveLayer() {
        // TODO Auto-generated method stub
        return null;
    }

    

	@Override
	public Layer openSavedLayer(String loadString) {
		// TODO Auto-generated method stub
		return null;
	}
}
