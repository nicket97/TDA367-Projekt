package model;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Levels implements Layerable{
	private int maxLevel;
	private int minLevel;
	private int midLevel;
	private double changeLevel;
	
	public Levels(int maxLevel, int minLevel) {
		this.maxLevel = maxLevel;
		this.minLevel = minLevel;
		this.midLevel = (maxLevel + minLevel)/2;
		this.changeLevel = ((minLevel/midLevel) + (midLevel/maxLevel))/2;
		
	}

	public Levels(String[] args) {
		this.maxLevel = Integer.parseInt(args[1]);
		this.minLevel = Integer.parseInt(args[2]);
		this.midLevel = (maxLevel + minLevel)/2;
		this.changeLevel = ((minLevel/midLevel) + (midLevel/maxLevel))/2;
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		changeLevel = 0.5;
		for (int i = 0; i < img.getpxImage().length; i++) {
			for (int j = 0; j < img.getpxImage()[1].length; j++){
				double pixRed = (((img.getpxImage()[i][j].getRed()*255)-midLevel)*changeLevel) + midLevel;
				double pixGreen = (((img.getpxImage()[i][j].getGreen()*255)-midLevel)*changeLevel) + midLevel;
				double pixBlue = (((img.getpxImage()[i][j].getBlue()*255)-midLevel)*changeLevel) + midLevel;
				pxImage[i][j]= Color.rgb((int)pixRed, (int)pixGreen, (int)pixBlue);
				}
		      }
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "Levels?"+maxLevel + "?" + minLevel + "?";
		return output;
	}

	@Override
	public String getName() {
		return "Nivåer";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public int getMidLevel() {
		return midLevel;
	}

	public void setMidLevel(int midLevel) {
		this.midLevel = midLevel;
	}

	public double getChangeLevel() {
		return changeLevel;
	}

	public void setChangeLevel(double changeLevel) {
		this.changeLevel = changeLevel;
	}
	public void setLevels(int min, int max){
		this.minLevel = min;
		this.maxLevel = max;
	}
}
