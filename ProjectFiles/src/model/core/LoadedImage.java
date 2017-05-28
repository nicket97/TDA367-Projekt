package model.core;

import java.awt.image.BufferedImage;
import javafx.scene.paint.Color;

/**
 * Keeps the data of the loaded image
 *
 */
public class LoadedImage {
	private Color[][] pxImage;
	private BufferedImage lImg;
	private int width;
	private int heigth;

	/**
	 * Constructor for the LoadedImage class
	 * @param img image being used in program
	 */
	public LoadedImage(BufferedImage img) {
		this.lImg = img;
		this.width = img.getWidth();
		this.heigth = img.getHeight();
		this.pxImage = new Color[width][heigth];
		class CreateImage implements Runnable {
			Color[][] pxImage;
			int n;

			CreateImage(Color[][] s, int x) {
				pxImage = s;
				n = x;
			}

			@Override
			public void run() {
				for (int i = n; i < width; i += 4) {
					for (int j = 0; j < heigth; j++) {
						pxImage[i][j] = getColorFromInt(img.getRGB(i, j));
					}
				}
			}
		}
		Thread t1 = new Thread(new CreateImage(pxImage, 0));
		Thread t2 = new Thread(new CreateImage(pxImage, 1));
		Thread t3 = new Thread(new CreateImage(pxImage, 2));
		Thread t4 = new Thread(new CreateImage(pxImage, 3));
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		try {
			t1.join();
			System.out.println("1");
			t2.join();
			System.out.println("2");
			t3.join();
			System.out.println("3");
			t4.join();
			System.out.println("4");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * for(int i = 0; i < width; i++){ for(int j = 0; j < heigth; j++){
		 * pxImage[i][j] = getColorFromInt(img.getRGB(i, j)); } }
		 */
		// System.out.println("hej" + pxImage[0][0].getRed()*255 + " eller " +
		// ((img.getRGB(0, 0)>>16)&0xFF));
	}

	/**
	 * Constructor of the LoadedImage class
	 * @param img image being drawn in program
	 */
	public LoadedImage(LoadedImage img) {
		this.width = img.width;
		this.heigth = img.heigth;
		this.pxImage = new Color[width][heigth];
		for (int i = 0; i < img.pxImage.length; i++) {
			for (int j = 0; j < img.pxImage[i].length; j++) {
				this.pxImage[i][j] = img.pxImage[i][j];
			}
		}
		BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < heigth; j++) {
				image.setRGB(i, j, LoadedImage.getIntFromColor(pxImage[i][j]));
			}
		}
		this.lImg = image;
		// System.out.println("heja" + pxImage[0][0].getRed()*255 + " eller " +
		// ((lImg.getRGB(0, 0)>>16)&0xFF));
	}

	/**
	 * Returns the image as a BufferedImage
	 * @return the image as a BufferedImage
	 */
	public BufferedImage getBufferedImg() {
		return lImg;
	}

	/**
	 * Turns a number of a color into separate rgb-values
	 * @param color number of color being translated
	 * @return rgb-values for the new Color
	 */
	public static Color getColorFromInt(int color) {
		int argb = color;
		int r = (argb >> 16) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = (argb >> 0) & 0xFF;
		return Color.rgb(r, g, b);

	}

	/**
	 * Turns rgb-values into a number
	 * @param rgb values to be transformed
	 * @return int representing the colors
	 */
	public static int getIntFromColor(Color rgb) {

		int R = (int) Math.round(255 * rgb.getRed());
		int G = (int) Math.round(255 * rgb.getGreen());
		int B = (int) Math.round(255 * rgb.getBlue());

		R = (R << 16) & 0x00FF0000;
		G = (G << 8) & 0x0000FF00;
		B = B & 0x000000FF;

		return 0xFF000000 | R | G | B;
	}

	/**
	 * Returns matrix filled with Color-objects
	 * @return matrix with picture
	 */
	public Color[][] getpxImage() {
		return pxImage;

	}

	/**
	 * Gets the width of the image
	 * @return width of image
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets height of image
	 * @return height of image
	 */
	public int getHeigth() {
		return heigth;
	}

	/**
	 * Sets the image
	 * @param pxImage the matrix of Color-object containing the image
	 */
	public void setPxImage(Color[][] pxImage) {
		this.pxImage = pxImage;
		
	}

	/**
	 * Sets the BufferedImage
	 * @param lImg image as BufferedImage
	 */
	public void setlImg(BufferedImage lImg) {
		this.lImg = lImg;
		class CreateImage implements Runnable {
			Color[][] pxImage;
			int n;

			CreateImage(Color[][] s, int x) {
				pxImage = s;
				n = x;
			}

			@Override
			public void run() {
				for (int i = n; i < width; i += 4) {
					for (int j = 0; j < heigth; j++) {
						pxImage[i][j] = getColorFromInt(lImg.getRGB(i, j));
					}
				}
			}
		}
		Thread t1 = new Thread(new CreateImage(this.pxImage, 0));
		Thread t2 = new Thread(new CreateImage(this.pxImage, 1));
		Thread t3 = new Thread(new CreateImage(this.pxImage, 2));
		Thread t4 = new Thread(new CreateImage(this.pxImage, 3));
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		try {
			t1.join();
			System.out.println("1");
			t2.join();
			System.out.println("2");
			t3.join();
			System.out.println("3");
			t4.join();
			System.out.println("4");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sets width of image
	 * @param width new width of image
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Sets height of image
	 * @param heigth new height of image
	 */
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
}
