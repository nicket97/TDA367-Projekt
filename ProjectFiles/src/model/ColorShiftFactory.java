package model;

import main.Layers;

public class ColorShiftFactory {

    public static ColorShift getColorShift(String color, int value) {
        if(color == null) {
            return null;
        }

        if(color.equals("yellow")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,25,0, value);
        }
        else if(color.equals("orange")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,0,-25, value);
        }
        else if(color.equals("blue")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(-25,-25,50, value);
        }
        else if(color.equals("red")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,-25,-25, value);
        }
        else if(color.equals("pink")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(35,10,15, value);
        }
        else if(color.equals("purple")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,-10,30, value);
        }
        else if(color.equals("turquoise")){
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(0,25,25, value);
        }
        else if(color.equals("green")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(-25,50,-25, value);
        }
        return null;
    }
}
