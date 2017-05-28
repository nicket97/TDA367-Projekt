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

	/**
	 * Gets the action
	 * @return the action
	 */
	public Layerable getAction() {
		return action;
	}
	/**
	 * Sets the action
	 * @param l the action to be set
	 */
	public void setaction(Layerable l) {
		this.action = l;
	}
	/**
	 * Gets the visibility of the layer
	 * @return the visibility
	 */
	public boolean getVisible() {
		return visible;
	}

	/**
	 * Changes the visibility of the layer
	 */
	public void changeVisible() {
		this.visible = !this.visible;
	}
	/**
	 * Gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * @param name the name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the filter settings
	 * @return filter settings
	 */
	public boolean hasSettings() {
		return action.hasSettings();
	}
}
