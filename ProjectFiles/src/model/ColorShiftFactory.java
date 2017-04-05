package model;

/**
 * Created by anton on 05/04/17.
 */
public class ColorShiftFactory {

    public ColorShift getColorShift(ColorShiftType color) {
        if(color == null) {
            return null;
        }
        if(color == ColorShiftType.BLUE) {
            return new ColorShift(, 0.85, 1.30);
        }
        else if(color == ColorShiftType.GREEN) {

        }

        return null;
    }
}
