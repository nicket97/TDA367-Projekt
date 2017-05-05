package model;


public class BlackAndWhite extends ColorFilter {
    private int threshold;

    public BlackAndWhite(int threshold) {
        this.threshold = threshold;
    }
    
    public BlackAndWhite(String[] arg){
    	this.threshold = Integer.parseInt(arg[1]);
    }

    public LoadedImage transform(LoadedImage img) {
        LoadedImage newImage = new LoadedImage(img);

        //.....

        return newImage;
    }

    @Override
    public String saveLayer() {
        String output = "BlackAndWhite?" + threshold + "?";
        return output;
    }

    

	/* @Override
	public Layer openSavedLayer(String loadString) {
		String[] data = loadString.split("?");
		BlackAndWhite bw = new BlackAndWhite();
		return new Layer(bw);
	} */
}
