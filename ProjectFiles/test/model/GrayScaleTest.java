package model;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * Created by anton on 08/05/17.
 */
public class GrayScaleTest {
	@Test
	public void transform() throws Exception {
		BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		LoadedImage loadedImage = new LoadedImage(testImage);

		loadedImage.getpxImage()[0][0] = Color.rgb(0, 0, 0);
		loadedImage.getpxImage()[0][1] = Color.rgb(45, 108, 147);
		loadedImage.getpxImage()[1][0] = Color.rgb(0, 100, 255);
		loadedImage.getpxImage()[1][1] = Color.rgb(255, 255, 255);

		GrayScale grayScale = new GrayScale();
		LoadedImage newImage = grayScale.transform(loadedImage);

		int avr0 = (0 + 0 + 0) / 3;
		int avr1 = (45 + 108 + 147) / 3;
		int avr2 = (0 + 100 + 255) / 3;
		int avr3 = (255 + 255 + 255) / 3;

		assertTrue((int) (newImage.getpxImage()[0][0].getRed() * 255) == avr0);
		assertTrue((int) (newImage.getpxImage()[0][0].getGreen() * 255) == avr0);
		assertTrue((int) (newImage.getpxImage()[0][0].getBlue() * 255) == avr0);

		assertTrue((int) (newImage.getpxImage()[0][1].getRed() * 255) == avr1);
		assertTrue((int) (newImage.getpxImage()[0][1].getGreen() * 255) == avr1);
		assertTrue((int) (newImage.getpxImage()[0][1].getBlue() * 255) == avr1);

		assertTrue((int) (newImage.getpxImage()[1][0].getRed() * 255) == avr2);
		assertTrue((int) (newImage.getpxImage()[1][0].getGreen() * 255) == avr2);
		assertTrue((int) (newImage.getpxImage()[1][0].getBlue() * 255) == avr2);

		assertTrue((int) (newImage.getpxImage()[1][1].getRed() * 255) == avr3);
		assertTrue((int) (newImage.getpxImage()[1][1].getGreen() * 255) == avr3);
		assertTrue((int) (newImage.getpxImage()[1][1].getBlue() * 255) == avr3);

	}
}