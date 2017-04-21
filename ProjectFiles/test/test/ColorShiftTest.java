package test;

import model.LoadedImage;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * Created by anton on 21/04/17.
 */
public class ColorShiftTest {
    @Test
    public void transform() throws Exception {
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        LoadedImage loadedImage = new LoadedImage(testImage);


    }

}