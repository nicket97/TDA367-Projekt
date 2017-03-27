package main;

import java.util.ArrayList;

public class Layers {
	private static ArrayList<Layer> layerStack = new ArrayList<>();
	
	public void addLayer(Layer l){
		layerStack.add(l);
	}
}
