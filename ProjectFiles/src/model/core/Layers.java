package model.core;

import java.util.ArrayList;

import controllers.MainView;
import model.core.listeners.RepaintListener;

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
		RepaintListener.actionPerformed();
		

	}

	public static void remove(Layer l) {
		layerStack.remove(l);
		RepaintListener.actionPerformed();

	}

	public static ArrayList<Layer> getLayerStack() {
		return layerStack;
	}

	public static void update() {
		RepaintListener.actionPerformed();
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
}
