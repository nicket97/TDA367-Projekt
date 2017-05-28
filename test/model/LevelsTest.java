package model;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.core.LoadedImage;
import model.transformations.Levels;

public class LevelsTest {
	
	@Test
	public void transform() throws Exception {
		BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		LoadedImage loadedImage = new LoadedImage(testImage);
		Levels level = new Levels(0,255);
		
		loadedImage.getpxImage()[0][0] = Color.rgb(0, 0, 0);
		loadedImage.getpxImage()[0][1] = Color.rgb(150, 200, 255);
		loadedImage.getpxImage()[1][0] = Color.rgb(150, 150, 150);
		loadedImage.getpxImage()[1][1] = Color.rgb(50, 0, 150);
		
		LoadedImage newImage = level.transform(loadedImage);
		assertTrue((int) (newImage.getpxImage()[0][0].getRed() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[0][0].getGreen() * 255) == 0);
		assertTrue((int) (newImage.getpxImage()[0][0].getBlue() * 255) == 0);
	}
}
