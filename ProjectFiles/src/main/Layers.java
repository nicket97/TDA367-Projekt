package main;

import java.util.ArrayList;

import model.Layer;

public class Layers {
	private static ArrayList<Layer> layerStack = new ArrayList<>();
	
	public static void addLayer(Layer l){
		layerStack.add(l);
	}
	public static void remove(Layer l){
		layerStack.remove(l);
	}
}
