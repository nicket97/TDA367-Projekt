package model;

import javafx.scene.paint.Color;
import model.ColorShift;
import model.LoadedImage;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;


public class ColorShiftTest {
	@Test
	public void transformTest() throws Exception {
		BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		LoadedImage loadedImage = new LoadedImage(testImage);

		loadedImage.getpxImage()[0][0] = Color.rgb(0, 0, 0);
		loadedImage.getpxImage()[0][1] = Color.rgb(100, 100, 100);
		loadedImage.getpxImage()[1][0] = Color.rgb(0, 100, 200);
		loadedImage.getpxImage()[1][1] = Color.rgb(200, 225, 250);

		ColorShift colorShift = new ColorShift(10, -25, 50);
		LoadedImage newImage = colorShift.transform(loadedImage);

		assertTrue((int) (newImage.getpxImage()[0][0].getRed() * 255) == 10);
		assertTrue((int) (newImage.getpxImage()[0][0].getGreen() * 255) == 0); // min
																				// 0
		assertTrue((int) (newImage.getpxImage()[0][0].getBlue() * 255) == 50);

		assertTrue((int) (newImage.getpxImage()[0][1].getRed() * 255) == 110);
		assertTrue((int) (newImage.getpxImage()[0][1].getGreen() * 255) == 75);
		assertTrue((int) (newImage.getpxImage()[0][1].getBlue() * 255) == 150);

		assertTrue((int) (newImage.getpxImage()[1][0].getRed() * 255) == 10);
		assertTrue((int) (newImage.getpxImage()[1][0].getGreen() * 255) == 75);
		assertTrue((int) (newImage.getpxImage()[1][0].getBlue() * 255) == 250);

		assertTrue((int) (newImage.getpxImage()[1][1].getRed() * 255) == 210);
		assertTrue((int) (newImage.getpxImage()[1][1].getGreen() * 255) == 200);
		assertTrue((int) (newImage.getpxImage()[1][1].getBlue() * 255) == 255); // max
																				// 255
	}

}