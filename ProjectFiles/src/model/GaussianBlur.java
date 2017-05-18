package model;

import java.util.ArrayList;
import java.util.List;

import controllers.MainView;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Filter that adds gaussian blur to the picture
 *
 */
public class GaussianBlur implements Layerable {
	private int radius;
	private double[][] kernel;

	public GaussianBlur(int r) {

		radius = r;
		/*
		 * if (radius % 2 == 0) { radius++; }
		 */
		kernel = new double[2 * radius + 1][2 * radius + 1];

		int[] factors = new int[2 * radius + 1];

		for (int k = 0; k < radius + 1; k++) {
			factors[k] = (int) Math.pow(2, k);
			factors[factors.length - k - 1] = (int) Math.pow(2, k);
		}

		for (int i = 0; i < 2 * radius + 1; i++) {
			for (int j = 0; j < 2 * radius + 1; j++) {
				kernel[i][j] = factors[i] * factors[j];
			}
		}

	}

	public GaussianBlur(String[] args) {
		this(Integer.parseInt(args[1]));
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		for (int i = 0; i < img.getpxImage().length; i++) {
			for (int j = 0; j < img.getpxImage()[i].length; j++) {
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				int count = 0;
				int x = 0;
				for (int k = -1 * radius; k < radius; k++) {
					int y = 0;
					for (int l = -1 * radius; l < radius; l++) {
						if ((i + k) >= 0 && (j + l) >= 0 && (i + k) < img.getpxImage().length
								&& (j + l) < img.getpxImage()[i].length) {
							sumRed += img.getpxImage()[i + k][j + l].getRed() * 255 * kernel[x][y];
							sumGreen += img.getpxImage()[i + k][j + l].getGreen() * 255 * kernel[x][y];
							sumBlue += img.getpxImage()[i + k][j + l].getBlue() * 255 * kernel[x][y];
							count += kernel[x][y];
							y++;
						}

					}
					x++;
				}
				pxImage[i][j] = Color.rgb(sumRed / count, sumGreen / count, sumBlue / count);
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		String output = "GaussianBlur?" + radius + "?";
		return output;

	}

	@Override
	public String getName() {
		return "Gaussisk Oskärpa";
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
		uppdateKernel(radius);

	}

	private void uppdateKernel(int r) {
		radius = r;
		/*
		 * if (radius % 2 == 0) { radius++; }
		 */
		kernel = new double[2 * radius + 1][2 * radius + 1];

		int[] factors = new int[2 * radius + 1];

		for (int k = 0; k < radius + 1; k++) {
			factors[k] = (int) Math.pow(2, k);
			factors[factors.length - k - 1] = (int) Math.pow(2, k);
		}

		for (int i = 0; i < 2 * radius + 1; i++) {
			for (int j = 0; j < 2 * radius + 1; j++) {
				kernel[i][j] = factors[i] * factors[j];
			}
		}

	}

	@Override
	public List<Slider> getSliders() {
		List<Slider> sliders = new ArrayList<>();
		Slider radiusSlider = new Slider();
		radiusSlider.setMin(0);
		radiusSlider.setMax(255);
		radiusSlider.setValue(this.getRadius());
		radiusSlider.setOnDragDone(e -> {
			this.setRadius((int) radiusSlider.getValue());
			MainView.getCanvas().repaint();
			System.out.println("Radie " + radiusSlider.getValue());
		});
		sliders.add(radiusSlider);
		return sliders;
	}

	public double[][] getKernel() {
		return kernel;
	}

	public void setKernel(double[][] kernel) {
		this.kernel = kernel;
	}

}