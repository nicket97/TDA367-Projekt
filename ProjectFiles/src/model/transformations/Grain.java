package model.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Pos;
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

	private int deviation;
	
	private boolean hasSettings = true;
	
	private Label labelDeviation = new Label("Avvikelse");
	private VBox h1 = new VBox();
	private Slider sliderDeviation = new Slider();

	public Grain(int deviation) {
		sliderDeviation.setMin(0);
		sliderDeviation.setMax(200);
		this.deviation = deviation;
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
				factor = (factor - 1) * deviation;

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
		return "Grain?" + deviation;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Brus";
	}


	/**
	 * Gets deviation 
	 * @return deviation
	 */
	public int getDiviation() {
		return deviation;
	}

	/**
	 * Sets deviation 
	 * @param diviation the deviation
	 */
	public void setDiviation(int diviation) {
		this.deviation = diviation;
	}

	@Override
	public List<VBox> getVBox() {
		initiateVBox(h1);
		sliderDeviation.setValue(this.deviation);
		h1.getChildren().add(sliderDeviation);
		h1.getChildren().add(labelDeviation);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(h1);
		
		return vboxList;
	}
	
	private void initiateVBox(VBox v) {
		v.getChildren().clear();
		v.setTranslateY(45);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
	}

	@Override
	public void uppdate() {
		this.deviation = (int) sliderDeviation.getValue();
		
	}

	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
