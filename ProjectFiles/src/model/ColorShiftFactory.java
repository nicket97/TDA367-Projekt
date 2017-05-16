package model;


public class ColorShiftFactory {

    public static ColorShift getColorShift(String color) {
        if(color == null) {
            return null;
        }

        if(color.equals("yellow")) {
            return new ColorShift(0, 0, 0);
        }
        else if(color.equals("orange")) {
            return new ColorShift(0, 0, 0);
        }
        else if(color.equals("blue")) {
            return new ColorShift(-25, -25, 50);
        }
        else if(color.equals("red")) {
            return new ColorShift(50, -25, -25);
        }
        else if(color.equals("pink")) {
            return new ColorShift(0, 0, 0);
        }
        else if(color.equals("purple")) {
            return new ColorShift(0, 0, 0);
        }
        else if(color.equals("turquoise")){
            return new ColorShift(0, 0, 0);
        }
        else if(color.equals("green")) {
            return new ColorShift(-25, 50, -25);
        }

        return null;
    }
}
