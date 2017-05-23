package model.transformations.core;

import java.util.ArrayList;

import controllers.MainView;
import model.core.LoadedImage;

/**
 * Handles the layer stack
 *
 */
public class Layers {
	
	private static ArrayList<Layer> layerStack = new ArrayList<>();
	private static LoadedImage backgroundImage;
	public static void addLayer(Layer l) {
		System.out.println("add Layer");
		layerStack.add(l);
		

	}

	public static void remove(Layer l) {
		layerStack.remove(l);
		

	}

	public static ArrayList<Layer> getLayerStack() {
		return layerStack;
	}

	
	public static void clear() {
		layerStack.clear();

	}
	public static LoadedImage getBackgroundImage() {
		return backgroundImage;
	}

	public static void setBackgroundImage(LoadedImage backgroundImage) {
		Layers.backgroundImage = backgroundImage;
	}
	public static Layer getLast(){
		return layerStack.get(layerStack.size()-1);
	}
}
