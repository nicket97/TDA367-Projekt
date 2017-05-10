package model;

import java.util.List;

import javafx.scene.control.Slider;

public interface Layerable {
	
	public LoadedImage transform(LoadedImage img);
	public String saveLayer();
	public String getName();
	public List<Slider> getSliders();
	
}
