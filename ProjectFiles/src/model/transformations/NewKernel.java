package model.transformations;

import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;
import model.core.NewFilterHandeler;
import model.transformations.help.ColorTransformTest;

/**
 * Saving and storing new kernels
 *
 */
public class NewKernel implements Layerable {

	double[][] kernel;
	String name;

	public NewKernel(double[][] kernel, String name) {
		this.kernel = kernel;
		this.name = name;
	}

	public NewKernel(String[] args) {
		this.name = args[1];
		kernel = NewFilterHandeler.getFilterKernel(name);
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		Color[][] pxImage = new Color[newImage.getpxImage().length][newImage.getpxImage()[0].length];
		int radius = (kernel.length - 1) / 2;

		for (int i = 0; i < img.getpxImage().length ; i++) {
			for (int j = 0; j < img.getpxImage()[i].length ; j++) {
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				int count = 0;
				int x = 0;
				for (int k = -1 * radius; k <= radius; k++) {
					int y = 0;
					for (int l = -1 * radius; l <= radius; l++) {
						if ((i + k) >= 0 && (j + l) >= 0 && (i + k) < img.getpxImage().length
								&& (j + l) < img.getpxImage()[i].length) {
							sumRed += img.getpxImage()[i + k][j + l].getRed() * 255 * kernel[x][y];
							sumGreen += img.getpxImage()[i + k][j + l].getGreen() * 255 * kernel[x][y];
							sumBlue += img.getpxImage()[i + k][j + l].getBlue() * 255 * kernel[x][y];
							count += kernel[x][y];
							y++;
						} else {

						}

					}
					x++;
				}
				sumRed = sumRed ;
				sumGreen = sumGreen;    
				sumBlue = sumBlue ;
				pxImage[i][j] = Color.rgb(ColorTransformTest.getAllowedValue(sumRed), ColorTransformTest.getAllowedValue(sumGreen), ColorTransformTest.getAllowedValue(sumBlue));
			}
		}
		newImage.setPxImage(pxImage);
		return newImage;
	}

	@Override
	public String saveLayer() {
		
		return "NewKernel?" + name + "?";
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setKernelAndName(double[][] kernel, String name){
		this.name = name;
		this.kernel = kernel;
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
