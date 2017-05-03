package model;


public class ColorShiftFactory {

    public ColorShift getColorShift(ColorShiftType color) {
        if(color == null) {
            return null;
        }

        if(color == ColorShiftType.YELLOW) {
            return new ColorShift(0, 0, 0);
        }
        else if(color == ColorShiftType.ORANGE) {
            return new ColorShift(0, 0, 0);
        }
        else if(color == ColorShiftType.BLUE) {
            return new ColorShift(-25, -25, 50);
        }
        else if(color == ColorShiftType.RED) {
            return new ColorShift(50, -25, -25);
        }
        else if(color == ColorShiftType.PINK) {
            return new ColorShift(0, 0, 0);
        }
        else if(color == ColorShiftType.PURPLE) {
            return new ColorShift(0, 0, 0);
        }
        else if(color == ColorShiftType.TURQUOISE) {
            return new ColorShift(0, 0, 0);
        }
        else if(color == ColorShiftType.GREEN) {
            return new ColorShift(-25, 50, -25);
        }

        return null;
    }
}
