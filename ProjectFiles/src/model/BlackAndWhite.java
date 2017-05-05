package model;


import javafx.scene.paint.Color;

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

        for(int i = 0; i < newImage.pxImage.length; i++){
            for(int j = 0; j < newImage.pxImage[i].length; j++){
                int avr = (int) ((newImage.pxImage[i][j].getRed()*255 + newImage.pxImage[i][j].getGreen()*255 + newImage.pxImage[i][j].getBlue()*255) / 3);
                if (avr < threshold) {
                    newImage.pxImage[i][j] = Color.rgb(0,0,0);
                }
                else if (avr > threshold) {
                    newImage.pxImage[i][j] = Color.rgb(255,255,255);

                }
            }
        }
        return newImage;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
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
