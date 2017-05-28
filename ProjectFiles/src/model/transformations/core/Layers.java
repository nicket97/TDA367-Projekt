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

	/**
	 * Removes a layer from the stack
	 * @param l layer to be removed
	 */
	public static void remove(Layer l) {
		layerStack.remove(l);
		

	}

	/**
	 * Gets the list of layers
	 * @return the list of layers in the stack
	 */
	public static ArrayList<Layer> getLayerStack() {
		return layerStack;
	}

	/**
	 * Clears the layer stack of all layers
	 */
	public static void clear() {
		layerStack.clear();

	}
	
	/**
	 * Gets the background image
	 * @return the background image
	 */
	public static LoadedImage getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * Sets the background image
	 * @param backgroundImage the image to be set as background
	 */
	public static void setBackgroundImage(LoadedImage backgroundImage) {
		Layers.backgroundImage = backgroundImage;
	}
	/**
	 * Gets the last object in the layer stack
	 * @return last object of the layer stack
	 */
	public static Layer getLast(){
		return layerStack.get(layerStack.size()-1);
	}
}
