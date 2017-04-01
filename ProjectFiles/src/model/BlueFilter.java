package model;

import javafx.scene.paint.Color;

/**
 * Created by anton on 01/04/17.
 */
public class BlueFilter implements Layrable {
    @Override
    public LoadedImage transform(LoadedImage img) {

        return ColorFilter.transform(img, 0.85, 0.85, 1.30);
    }
}
