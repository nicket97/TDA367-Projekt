package model.transformations;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Filter that blurs the picture
 */
public class Blur implements Layerable {
	private int radius;
	private double[][] kernel;
	private boolean hasSettings = true;
	
	private Label labelText = new Label();
	private VBox h1 = new VBox();
	private Slider sliderRadius = new Slider();

	public Blur(int r) {
		sliderRadius.setMin(1);
		sliderRadius.setMax(10);
		
		labelText.setText("Radie");
		radius = r;
		kernel = new double[2 * radius + 1][2 * radius + 1];

		for (int i = 0; i < 2 * radius + 1; i++) {
			for (int j = 0; j < 2 * radius + 1; j++) {
				kernel[i][j] = 1;
			}
		}

	}

	public Blur(String[] args) {
		this(Integer.parseInt(args[1]));
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		long time = System.nanoTime();
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < img.getpxImage().length; i++) {
			for (int j = 0; j < img.getpxImage()[i].length; j++) {
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				int count = 0;
				for (int k = -1 * radius; k < radius; k++) {
					for (int l = -1 * radius; l < radius; l++) {
						if ((i + k) >= 0 && (j + l) >= 0 && (i + k) < img.getpxImage().length
								&& (j + l) < img.getpxImage()[i].length) {
							sumRed += img.getpxImage()[i + k][j + l].getRed() * 255;
							sumGreen += img.getpxImage()[i + k][j + l].getGreen() * 255;
							sumBlue += img.getpxImage()[i + k][j + l].getBlue() * 255;
							count++;
						}
					}
				}
				pxImage[i][j] = Color.rgb(sumRed / count, sumGreen / count, sumBlue / count);
			}
		}
		System.out.println("Blur" + (System.nanoTime() - time) / 1000000000);
		newImage.setPxImage(pxImage);
		return newImage;
	}

	
	@Override
	public String saveLayer() {
		String output = "Blur?" + radius + "?";
		return output;
	}

	
	@Override
	public String getName() {
		return "Oskärpa";
	}

	/**
	 * Sets the radius of the kernel
	 * @param radius radius to be set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Gets the radius of the kernel
	 * @return radius of kernel
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Gets the kernel-values
	 * @return the kernel-values
	 */
	public double[][] getKernel() {
		return kernel;
	}

	/**
	 * Sets the kernel-values
	 * @param kernel new values to be set in kernel
	 */
	public void setKernel(double[][] kernel) {
		this.kernel = kernel;
	}

	@Override
	public List<VBox> getVBox() {
		initiateVBox(h1);
		sliderRadius.setValue(radius);
		h1.getChildren().add(sliderRadius);
		h1.getChildren().add(labelText);
		
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
		this.radius = (int) sliderRadius.getValue();
		kernel = new double[2 * radius + 1][2 * radius + 1];

		for (int i = 0; i < 2 * radius + 1; i++) {
			for (int j = 0; j < 2 * radius + 1; j++) {
				kernel[i][j] = 1;
			}
		}
		
	}

	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}