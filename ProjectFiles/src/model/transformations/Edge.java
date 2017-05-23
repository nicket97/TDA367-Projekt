package model.transformations;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;
import model.transformations.help.ColorTransformTest;

/**
 * Filter that makes the edges more visible and the rest less visible in the
 * picture
 *
 */
public class Edge implements Layerable {

	private double[][] kernel;
	private int radius;

	public Edge() {
		radius = 1;
		kernel = new double[3][3];
		kernel[0][0] = -1;
		kernel[0][1] = -1;
		kernel[0][2] = -1;
		kernel[1][0] = -1;
		kernel[1][1] = 8;
		kernel[1][2] = -1;
		kernel[2][0] = -1;
		kernel[2][1] = -1;
		kernel[2][2] = -1;
	}

	public Edge(String[] args) {
		this();
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
				for (int k = -1 * radius; k < radius; k++) {
					for (int l = -1 * radius; l < radius; l++) {
						if ((i + k) >= 0 && (j + l) >= 0 && (i + k) < img.getpxImage().length
								&& (j + l) < img.getpxImage()[i].length) {
							sumRed += img.getpxImage()[i + k][j + l].getRed() * 255;
							sumGreen += img.getpxImage()[i + k][j + l].getGreen() * 255;
							sumBlue += img.getpxImage()[i + k][j + l].getBlue() * 255;
							count += kernel[k + 1][l + 1];
						}
					}
				}

				pxImage[i][j] = Color.rgb(ColorTransformTest.getAllowedValue(sumRed),
						ColorTransformTest.getAllowedValue(sumGreen), ColorTransformTest.getAllowedValue(sumBlue));

			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		// TODO Auto-generated method stub
		return "Kanter";
	}

	@Override
	public String getName() {
		return "Kanter";
	}


	public double[][] getKernel() {
		return kernel;
	}

	public void setKernel(double[][] kernel) {
		this.kernel = kernel;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public List<VBox> getVBox() {
		return null;
	}

	@Override
	public void uppdate() {
		
	}

}
