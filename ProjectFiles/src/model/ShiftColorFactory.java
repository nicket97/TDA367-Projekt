package model;

/**
 * Created by anton on 05/04/17.
 */
public class ShiftColorFactory {

    public ShiftColor getShiftColor(ColorShiftType color) {
        if(color == null) {
            return null;
        }
        if(color == ColorShiftType.RED) {
            return new ShiftColor(1.30, 0.85, 0.85);
        }
        else if(color == ColorShiftType.GREEN) {
            return new ShiftColor(0.85, 1.30, 0.85);
        }
        else if(color == ColorShiftType.BLUE) {
            return new ShiftColor(0.85, 0.85, 1.30);
        }

        return null;
    }
}
