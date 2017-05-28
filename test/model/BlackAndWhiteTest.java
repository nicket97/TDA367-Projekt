package model;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.core.LoadedImage;
import model.transformations.BlackAndWhite;


public class BlackAndWhiteTest {
	public static class AsNonApp extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        // noop
	    }
	}

	@BeforeClass
	public static void initJFX() {
	    Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	            Application.launch(AsNonApp.class, new String[0]);
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	}
	
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