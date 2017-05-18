package model;

import java.util.ArrayList;
import java.util.List;

import controllers.MainView;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Filter that blurs the picture
 */
public class Blur implements Layerable {
	private int radius;
	private double[][] kernel;

	public Blur(int r) {
		radius = r;
		if (radius % 2 == 0) {
			radius++;
		}
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

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	public double[][] getKernel() {
		return kernel;
	}

	public void setKernel(double[][] kernel) {
		this.kernel = kernel;
	}
}