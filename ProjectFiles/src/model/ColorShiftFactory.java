package model;

/**
 * Created by anton on 05/04/17.
 */
public class ColorShiftFactory {

    public ColorShift getColorShift(Color color) {
        if(color == null) {
            return null;
        }
        if(color == Color.BLUE) {
            return new ColorShift(0.85, 0.85, 1.30);
        }
        else if(color == Color.GREEN) {

        }
        
        return null;
    }
}
