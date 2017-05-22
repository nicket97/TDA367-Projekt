package model.core;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

/**
 * Interface common for all filters
 *
 */
public interface Layerable {

	public LoadedImage transform(LoadedImage img);

	public String saveLayer();

	public String getName();

	public List<HBox> getHBox();

}
