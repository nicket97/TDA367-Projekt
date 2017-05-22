package model.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
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
	
	private Label labelDiviation = new Label("Avvikelse");
	private VBox h1 = new VBox();
	private Slider sliderDiviation = new Slider();

	public Grain(int diviation) {
		sliderDiviation.setMin(0);
		sliderDiviation.setMax(200);
		this.diviation = diviation;
	}

	public Grain(String[] args) {
		this( Integer.parseInt(args[1]));
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


	public int getDiviation() {
		return diviation;
	}

	public void setDiviation(int diviation) {
		this.diviation = diviation;
	}

	@Override
	public List<VBox> getVBox() {
		h1.getChildren().clear();
		sliderDiviation.setValue(this.diviation);
		h1.getChildren().add(sliderDiviation);
		h1.getChildren().add(labelDiviation);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(h1);
		
		return vboxList;
	}

	@Override
	public void uppdate() {
		this.diviation = (int) sliderDiviation.getValue();
		
	}

}
