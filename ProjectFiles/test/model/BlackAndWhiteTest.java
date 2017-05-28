package model;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import model.core.LoadedImage;
import model.transformations.BlackAndWhite;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;


public class BlackAndWhiteTest {
	@Test
	public void transform() throws Exception {
		BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		LoadedImage loadedImage = new LoadedImage(testImage);

		int threshold = 150;

		loadedImage.getpxImage()[0][0] = Color.rgb(0, 0, 0);
		loadedImage.getpxImage()[0][1] = Color.rgb(150, 200, 255);
		loadedImage.getpxImage()[1][0] = Color.rgb(150, 150, 150);
		loadedImage.getpxImage()[1][1] = Color.rgb(50, 0, 150);

		BlackAndWhite bW = new BlackAndWhite(threshold);
		LoadedImage newImage = bW.transform(loadedImage);


		assertTrue((int) (newImage.getpxImage()[0][0].getRed() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[0][0].getGreen() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[0][0].getBlue() * 255) == 0);

		assertTrue((int) (newImage.getpxImage()[0][1].getRed() * 255) == 255);
		assertTrue((int) (newImage.getpxImage()[0][1].getGreen() * 255) == 255);
		assertTrue((int) (newImage.getpxImage()[0][1].getBlue() * 255) == 255);

		assertTrue((int) (newImage.getpxImage()[1][0].getRed() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[1][0].getGreen() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[1][0].getBlue() * 255) == 0);

		assertTrue((int) (newImage.getpxImage()[1][1].getRed() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[1][1].getGreen() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[1][1].getBlue() * 255) == 0);
	}

}