package model.transformations;


import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;
/**
 * Filter that makes the picture only contain black and white pixles
 */
public class BlackAndWhite implements Layerable{

    private int threshold;
    
    private Slider sliderThreshold = new Slider();
    private Label labelText = new Label();
	private VBox h1 = new VBox();

	public BlackAndWhite(int threshold) {
		sliderThreshold.setMin(1);
		sliderThreshold.setMax(255);
		labelText.setText("Tröskelvärde");
		
        this.threshold = threshold;
    }
    
    public BlackAndWhite(String[] arg){
    	this(Integer.parseInt(arg[1]));
    }

    public LoadedImage transform(LoadedImage img) {
        LoadedImage newImage = new LoadedImage(img);
        Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
        for(int i = 0; i < newImage.getpxImage().length; i++){
            for(int j = 0; j < newImage.getpxImage()[i].length; j++){
                int avr = (int) ((newImage.getpxImage()[i][j].getRed()*255 + newImage.getpxImage()[i][j].getGreen()*255 + newImage.getpxImage()[i][j].getBlue()*255) / 3);
                if (avr <= threshold) {
                    pxImage[i][j] = Color.rgb(0,0,0);
                }
                else if (avr > threshold) {
                    pxImage[i][j] = Color.rgb(255,255,255);

                }
            }
        }
		newImage.setPxImage(pxImage);
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

	@Override
	public String getName() {
		return "Svartvitt";
	}


	@Override
	public List<VBox> getVBox() {
		h1.getChildren().clear();
		sliderThreshold.setValue(this.threshold);
		h1.getChildren().add(sliderThreshold);
		h1.getChildren().add(labelText);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(h1);
		return vboxList;
	}

	@Override
	public void uppdate() {
		this.threshold = (int) sliderThreshold.getValue();
		
	}

    
}
