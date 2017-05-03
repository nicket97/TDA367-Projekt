package model;

/**
 * Created by anton on 03/05/17.
 */
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
    public void openSavedLayer() {
        // TODO Auto-generated method stub

    }
}
