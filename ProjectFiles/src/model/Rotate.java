package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Slider;

public class Rotate implements Layerable{

	@Override
	public LoadedImage transform(LoadedImage img) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveLayer() {
		String output = "Rotate?"; 
		return output;
	}

	@Override
	public String getName() {
		return "Rotate";
	}

	@Override
	public List<Slider> getSliders() {
		List<Slider> emptyList = new ArrayList();
		return emptyList;
	}

}
