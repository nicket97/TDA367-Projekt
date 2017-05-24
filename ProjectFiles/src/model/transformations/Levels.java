package model.transformations;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Filter that mutes high and low colors in the picture
 *
 */
public class Levels implements Layerable {
	private int maxLevel;
	private int minLevel;
	private int midLevel;
	private double changeLevel;
	
	private boolean hasSettings = true;
	
	private Slider sliderMin = new Slider();
	private Label labelMin = new Label("Min Värde");
	
	private Slider sliderMax = new Slider();
	private Label labelMax = new Label("Max Värde");
	
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();
	

	public Levels(int maxLevel, int minLevel) {
		sliderMin.setMin(1);
		sliderMin.setMax(255);
		
		sliderMax.setMin(1);
		sliderMax.setMax(2);
		
		this.maxLevel = maxLevel;
		this.minLevel = minLevel;
		//this.midLevel = (maxLevel + minLevel) / 2;
		setMidLevel();
		//this.changeLevel = (double)(((double)minLevel / (double)midLevel) + ((double)midLevel / (double)maxLevel));
		calculateChange();
		
	}

	public Levels(String[] args) {
		this.maxLevel = Integer.parseInt(args[1]);
		this.minLevel = Integer.parseInt(args[2]);
		//this.midLevel = (maxLevel + minLevel) / 2;
		setMidLevel();
		//this.changeLevel = (double)(((double)minLevel / (double)midLevel) + ((double)midLevel / (double)maxLevel));
		calculateChange();
		
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		System.out.println("hkjsdslkjkjdlka"  +  changeLevel);
		for (int i = 0; i < img.getpxImage().length; i++) {
			for (int j = 0; j < img.getpxImage()[1].length; j++) {
				
				double pixRed = (((img.getpxImage()[i][j].getRed() * 255) - midLevel) * changeLevel) + midLevel;
				double pixGreen = (((img.getpxImage()[i][j].getGreen() * 255) - midLevel) * changeLevel) + midLevel;
				double pixBlue = (((img.getpxImage()[i][j].getBlue() * 255) - midLevel) * changeLevel) + midLevel;
				if(pixRed < minLevel)pixRed = minLevel;
				if(pixRed > maxLevel)pixRed = maxLevel;
				if(pixGreen < minLevel)pixGreen = minLevel;
				if(pixGreen > maxLevel)pixGreen = maxLevel;
				if(pixBlue < minLevel)pixBlue = minLevel;
				if(pixBlue > maxLevel)pixBlue = maxLevel;
				pxImage[i][j] = Color.rgb((int) pixRed, (int) pixGreen, (int) pixBlue);
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "Levels?" + maxLevel + "?" + minLevel + "?";
		return output;
	}

	@Override
	public String getName() {
		return "Nivåer";
	}


	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
		calculateChange();
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
		calculateChange();
	}

	public int getMidLevel() {
		return midLevel;
	}

	public void setMidLevel() {
		this.midLevel = (maxLevel + minLevel) / 2;
	}

	public double getChangeLevel() {
		return changeLevel;
	}

	

	public void setLevels(int min, int max) {
		this.minLevel = min;
		this.maxLevel = max;
		setMidLevel();
		calculateChange();
	}
	public void calculateChange(){
		this.changeLevel = (double)(((double)minLevel / (double)midLevel) + ((double)midLevel / (double)maxLevel))*2;
	}

	@Override
	public List<VBox> getVBox() {
		initiateVBox(v1);
		initiateVBox(v2);
		sliderMin.setValue(this.minLevel);
		v1.getChildren().addAll(sliderMin, labelMin);
		
		sliderMax.setValue(this.maxLevel);
		v2.getChildren().addAll(sliderMax, labelMax);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		vboxList.add(v2);
		
		return vboxList;
	}

	private void initiateVBox(VBox v) {
		v.getChildren().clear();
		v.setTranslateY(45);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
	}
	
	@Override
	public void uppdate() {
		this.minLevel = (int) sliderMin.getValue();
		this.maxLevel = (int) sliderMax.getValue();
		setMidLevel();
		calculateChange();
	}
	
	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
