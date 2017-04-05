package model;

/**
 * Created by anton on 05/04/17.
 */
public class ShiftColor {
    private double redFactor;
    private double greenFactor;
    private double blueFactor;

    public ShiftColor(double redFactor, double greenFactor, double blueFactor) {
        this.redFactor = redFactor;
        this.greenFactor = greenFactor;
        this.blueFactor = blueFactor;
    }

    public double getRedFactor() {
        return redFactor;
    }

    public double getGreenFactor() {
        return greenFactor;
    }

    public double getBlueFactor() {
        return blueFactor;
    }
}
