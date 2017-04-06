package main;

import java.util.ArrayList;
import java.util.Iterator;

import controllers.CanvasView;
import controllers.MainView;
import model.Layer;

public class Layers {
	
	private static ArrayList<Layer> layerStack = new ArrayList<>();
	
	public static void addLayer(Layer l){
		layerStack.add(l);
	}
	public static void remove(Layer l){
		layerStack.remove(l);
	}
	public static Iterator getLayerIterator(){
		return layerStack.iterator();
	}
	public static ArrayList<Layer> getLayerStack(){
		return layerStack;
	}
	public static void update(){
		MainView.getCanvas().repaint();
	}
}
