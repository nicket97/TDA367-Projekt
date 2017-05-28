package model.core;

import java.util.List;

import javafx.scene.layout.VBox;

/**
 * Interface common for all filters
 *
 */
public interface Layerable {

	/**
	 * Takes care of the transformation
	 * @param img image being transformed
	 * @return new transformed image
	 */
	public LoadedImage transform(LoadedImage img);

	/**
	 * Saves the information for the layer
	 * @return string of layer information
	 */
	public String saveLayer();

	/**
	 * Gets the name of the filter
	 * @return name of filter
	 */
	public String getName();

	/**
	 * Gets the vBox
	 * @return the vBox
	 */
	public List<VBox> getVBox();

	/**
	 * Updates the filter
	 */
	public void uppdate();

	/**
	 * Returns the filter settings
	 * @return
	 */
	public boolean hasSettings();
}
