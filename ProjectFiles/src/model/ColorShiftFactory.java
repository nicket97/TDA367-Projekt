package model;


public class ColorShiftFactory {

    public ColorShift getColorShift(ColorShiftType color) {
        if(color == null) {
            return null;
        }
        if(color == ColorShiftType.RED) {
            return new ColorShift(50, -25, -25);
        }
        else if(color == ColorShiftType.GREEN) {
            return new ColorShift(-25, 50, -25);
        }
        else if(color == ColorShiftType.BLUE) {
            return new ColorShift(-25, -25, 50);
        }

        //Add more ColorShifts
        //...
        //...

        return null;
    }
}
