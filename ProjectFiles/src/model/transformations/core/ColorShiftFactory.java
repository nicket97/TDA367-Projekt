package model.transformations.core;

import model.transformations.ColorShift;

/**
 * Creates a Colorshift
 *
 */
public class ColorShiftFactory {

	public static ColorShift getColorShift(String color, double d) {
		if (color == null) {
			return null;
		}

		if (color.equals("yellow")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(25, 25, 0, d);
		} else if (color.equals("orange")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(25, 0, -25, d);
		} else if (color.equals("blue")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(-25, -25, 50, d);
		} else if (color.equals("red")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(25, -25, -25, d);
		} else if (color.equals("pink")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(35, 10, 15, d);
		} else if (color.equals("purple")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(25, -10, 30, d);
		} else if (color.equals("turquoise")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(0, 25, 25, d);
		} else if (color.equals("green")) {
			Layers.getLayerStack().get(Layers.getLayerStack().size() - 1).setRGB(-25, 50, -25, d);
		}
		return null;
	}
}
