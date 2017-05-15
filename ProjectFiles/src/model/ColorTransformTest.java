package model;

public class ColorTransformTest {
	public static int getAllowedValue(double newColor) {
			if (newColor > 255) {
				newColor = 255;
			}
			else if (newColor < 0) {
				newColor = 0;
			}
			return (int) newColor;

		}
}
