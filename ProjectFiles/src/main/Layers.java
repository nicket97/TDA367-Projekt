package main;

import java.util.ArrayList;

import controllers.MainView;
import model.Layer;

/**
 * Handles the layer stack
 *
 */
public class Layers {

	private static ArrayList<Layer> layerStack = new ArrayList<>();

	public static void addLayer(Layer l) {
		System.out.println("add Layer");
		layerStack.add(l);
		MainView.layerView.update();

	}

	public static void remove(Layer l) {
		layerStack.remove(l);
		MainView.layerView.update();

	}

	public static ArrayList<Layer> getLayerStack() {
		return layerStack;
	}

	public static void update() {
		MainView.getCanvas().repaint();
	}

	public static void clear() {
		layerStack.clear();

	}
}
