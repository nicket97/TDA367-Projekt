package test;

import javafx.scene.paint.Color;
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
        loadedImage.pxImage[0][0] = Color.rgb(100, 0, 0);
        loadedImage.pxImage[0][1] = Color.rgb(100, 100, 100);
        loadedImage.pxImage[1][0] = Color.rgb(0, 100, 200);
        loadedImage.pxImage[1][1] = Color.rgb(200, 100, 0);
        System.out.println((int) (loadedImage.pxImage[0][0].getRed() * 255));

    }

}