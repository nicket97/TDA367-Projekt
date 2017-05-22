package model.transformations.core;

import model.core.Layerable;
import model.transformations.BlackAndWhite;
import model.transformations.Blur;
import model.transformations.ColorShift;
import model.transformations.Contrast;
import model.transformations.Exposure;
import model.transformations.GaussianBlur;
import model.transformations.Grain;
import model.transformations.Levels;
import model.transformations.NewKernel;
import model.transformations.Sharpen;
import model.transformations.WhiteBalance;
import model.transformations.TextFilter;

/**
 * Keeps information regarding a layer
 *
 */
public class Layer {
	private Layerable action;
	private boolean visible;
	private String name = "";

	public Layer(Layerable l) {
		this.action = l;
		this.visible = true;
		this.name = l.getName();
	}

	public Layerable getAction() {
		return action;
	}

	public void setaction(Layerable l) {
		this.action = l;
	}

	public boolean getVisible() {
		return visible;
	}

	public void changeVisible() {
		this.visible = !this.visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/***
	 * Methods for changing values of current layer.
	 * 
	 * @param intValue
	 */
	public void setRadius(int value) {
		if (name.equals("Oskärpa")) {
			((Blur) action).setRadius(value);
		} else if (name.equals("Gaussisk Oskärpa")) {
			((GaussianBlur) action).setRadius(value);
		} else { ((Sharpen) action).setRadius(value); }
	}

	public void setRGB(double r, double g, double b, double value) {
		((ColorShift) action).setRGB(r, g, b, value);
	}

	public void setColor(String color, double d) {
		ColorShiftFactory.getColorShift(color, d);
	}

	public void setThreshold(int value) {
		if (name.equals("Svartvitt")) {
			((BlackAndWhite) action).setThreshold(value);
		} else if (name.equals("Vitbalans")) {
			((WhiteBalance) action).setThreshold(value);
		}
	}

	public void setFactor(int value) {
		((Exposure) action).setFactor(value);
	}

	public void setFactorAndThreshold(int value, double d) {
		((Contrast) action).setFactorAndThreshold(value, d);
	}

	public void setLevels(int min, int max) {
		((Levels) action).setLevels(min, max);
	}

	public void setDeviation(int value) {
		((Grain) action).setDiviation(value);
	}
	public void setKernel(double[][] kernel, String name){
		((NewKernel) action).setKernelAndName(kernel, name);
	}
	
	public void setTextFilter(String text, String font, int size, String yPosition, int r, int g, int b ) {
		((TextFilter) action).setTextFilter(text, font, size, yPosition, r, g, b);
	}

	public double getIntensity(){
		return ((ColorShift) action).getIntensity();
	}
		
	public double getDouble(){
		if (name.equals("Färgfilter")){
			return ((ColorShift) action).getIntensity();
		}
		else if (name.equals("Exponering")){
			return ((Exposure) action).getFactor();
		}
		else if (name.equals("Kontrast")){
			return ((Contrast) action).getFactor();
		}
		return 0.0;
	}
	public int getInt(){
		if (name.equals("Kontrast")){
			return ((Contrast) action).getThreshold();
		}
		else if (name.equals("Brus")){
			return ((Grain) action).getDiviation();
		}
		else if (name.equals("Oskärpa")){
			return ((Blur) action).getRadius();
		}
		else if (name.equals("Gaussisk Oskärpa")){
			return ((GaussianBlur) action).getRadius();
		}
		else if (name.equals("Skärpa")){
			return ((Sharpen) action).getRadius();
		}
		else if (name.equals("Svartvitt")){
			return ((BlackAndWhite) action).getThreshold();
		}
		else if (name.equals("Vitbalans")){
			return ((WhiteBalance) action).getThreshold();
		}
		return 0;
	}

	public double getMinLevel() {
		return ((Levels) action).getMinLevel();
	}

	public double getMaxLevel() {
		return ((Levels) action).getMaxLevel();

	}
}
