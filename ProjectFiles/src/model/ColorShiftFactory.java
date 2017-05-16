package model;

import main.Layers;

public class ColorShiftFactory {

    public static ColorShift getColorShift(String color) {
        if(color == null) {
            return null;
        }

        if(color.equals("yellow")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,25,0);
        }
        else if(color.equals("orange")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,0,-25);
        }
        else if(color.equals("blue")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(-25,-25,50);
        }
        else if(color.equals("red")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,-25,-25);
        }
        else if(color.equals("pink")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(35,10,15);
        }
        else if(color.equals("purple")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(25,-10,30);
        }
        else if(color.equals("turquoise")){
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(0,25,25);
        }
        else if(color.equals("green")) {
        	Layers.getLayerStack().get(Layers.getLayerStack().size()-1).setRGB(-25,50,-25);
        }
        return null;
    }
}
