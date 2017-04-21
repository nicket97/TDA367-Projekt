package test;

import javafx.scene.paint.Color;
import model.ColorShift;
import model.LoadedImage;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * Created by anton on 21/04/17.
 */
public class ColorShiftTest {
    @Test
    public void transformTest() throws Exception {
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        LoadedImage loadedImage = new LoadedImage(testImage);
        loadedImage.pxImage[0][0] = Color.rgb(0, 0, 0);
        loadedImage.pxImage[0][1] = Color.rgb(100, 100, 100);
        loadedImage.pxImage[1][0] = Color.rgb(0, 100, 200);
        loadedImage.pxImage[1][1] = Color.rgb(250, 255, 255);
        ColorShift colorShift = new ColorShift(10, 10, 10);
        LoadedImage newImage = colorShift.transform(loadedImage);
        System.out.println((newImage.pxImage[1][1].getRed() * 255));
        assertTrue((int) (newImage.pxImage[1][1].getRed() * 255) == 255);
        assertTrue((int) (newImage.pxImage[0][0].getGreen() * 255) == 10);
        assertTrue((int) (newImage.pxImage[0][1].getBlue() * 255) == 110);


    }

}