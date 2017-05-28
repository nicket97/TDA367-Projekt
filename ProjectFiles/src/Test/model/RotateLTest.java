package Test.model;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.Test;

import javafx.scene.paint.Color;
import model.core.LoadedImage;
import model.transformations.RotateL;
import model.transformations.RotateR;

public class RotateLTest {

	@Test
	public void transform() throws Exception {
		BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		LoadedImage loadedImage = new LoadedImage(testImage);

		loadedImage.getpxImage()[0][0] = Color.rgb(0, 0, 0);
		loadedImage.getpxImage()[0][1] = Color.rgb(45, 108, 147);
		loadedImage.getpxImage()[1][0] = Color.rgb(0, 100, 255);
		loadedImage.getpxImage()[1][1] = Color.rgb(255, 255, 255);
		
		RotateL rotate = new RotateL();
		LoadedImage newImage = rotate.transform(loadedImage);
		
		assertTrue((int) (newImage.getpxImage()[0][0].getRed() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[0][0].getGreen() * 255) == 100);
		assertTrue((int) (newImage.getpxImage()[0][0].getBlue() * 255) == 255);
		
		assertTrue((int) (newImage.getpxImage()[0][1].getRed() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[0][1].getGreen() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[0][1].getBlue() * 255) == 0);
		
		assertTrue((int) (newImage.getpxImage()[1][0].getRed() * 255) == 255);
		assertTrue((int) (newImage.getpxImage()[1][0].getGreen() * 255) == 255);
		assertTrue((int) (newImage.getpxImage()[1][0].getBlue() * 255) == 255);
		
		assertTrue((int) (newImage.getpxImage()[1][1].getRed() * 255) == 45);
		assertTrue((int) (newImage.getpxImage()[1][1].getGreen() * 255) == 108);
		assertTrue((int) (newImage.getpxImage()[1][1].getBlue() * 255) == 147);
		
		

	}
}
