package model.transformations;

import java.util.List;
import java.util.Random;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;
import model.transformations.help.ColorTransformTest;

/**
 * Filter that adds noise to the picture
 *
 */
public class Grain implements Layerable {

	private int diviation;

	public Grain(int diviation) {
		this.diviation = diviation;
	}

	public Grain(String[] args) {
		this.diviation = Integer.parseInt(args[1]);
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		Random r = new Random();
		for (int i = 0; i < newImage.getpxImage().length; i++) {
			for (int j = 0; j < newImage.getpxImage()[i].length; j++) {

				int factor = r.nextInt(2);
				factor = (factor - 1) * diviation;

				Color pxColor = newImage.getpxImage()[i][j];
				double newRed = pxColor.getRed() * 255 + factor;
				double newGreen = pxColor.getGreen() * 255 + factor;
				double newBlue = pxColor.getBlue() * 255 + factor;
				// pxColor = Color.rgb((int) (((newRed) > 255) ? 255 : ((newRed)
				// < 0) ? 0 : newRed), (int) (((newGreen) > 255) ? 255 :
				// newGreen), (int) (((newBlue + b) > 255) ? 255 : newBlue +
				// b));
				pxColor = Color.rgb(ColorTransformTest.getAllowedValue(newRed),
						ColorTransformTest.getAllowedValue(newGreen), ColorTransformTest.getAllowedValue(newBlue));
				pxImage[i][j] = pxColor;
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		// TODO Auto-generated method stub
		return "Grain?" + diviation;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Brus";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDiviation() {
		return diviation;
	}

	public void setDiviation(int diviation) {
		this.diviation = diviation;
	}

}