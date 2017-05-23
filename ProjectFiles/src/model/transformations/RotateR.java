package model.transformations;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Rotates the picture to the right
 *
 */
public class RotateR implements Layerable {

	@Override
	public LoadedImage transform(LoadedImage img) {
		BufferedImage newImage = new BufferedImage(img.getHeigth(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeigth(); j++) {
				newImage.setRGB(img.getHeigth() - 1 - j, i, LoadedImage.getIntFromColor(img.getpxImage()[i][j]));
			}
		}
		return new LoadedImage(newImage);
	}

	@Override
	public String saveLayer() {
		String output = "RotateR?";
		return output;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rotera 90\u00b0 höger";
	}


	@Override
	public List<VBox> getVBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void uppdate() {
		// TODO Auto-generated method stub
		
	}

}
