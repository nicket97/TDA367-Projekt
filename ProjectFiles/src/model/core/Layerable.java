package model.core;

import java.util.List;

import javafx.scene.layout.VBox;

/**
 * Interface common for all filters
 *
 */
public interface Layerable {

	public LoadedImage transform(LoadedImage img);

	public String saveLayer();

	public String getName();

	public List<VBox> getHBox();

	public void uppdate();
}
