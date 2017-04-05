package model;


public class ColorShiftFactory {

    public ColorShift getColorShift(ColorShiftType color) {
        if(color == null) {
            return null;
        }
        if(color == ColorShiftType.RED) {
            return new ColorShift(1.30, 0.85, 0.85);
        }
        else if(color == ColorShiftType.GREEN) {
            return new ColorShift(0.85, 1.30, 0.85);
        }
        else if(color == ColorShiftType.BLUE) {
            return new ColorShift(0.85, 0.85, 1.30);
        }

        //Add more ColorShifts
        //...
        //...

        return null;
    }
}
