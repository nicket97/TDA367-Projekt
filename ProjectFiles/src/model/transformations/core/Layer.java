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

	public boolean hasSettings() {
		return action.hasSettings();
	}
}
