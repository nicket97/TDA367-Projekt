package model.transformations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sun.javafx.geom.Rectangle;

import javafx.scene.control.Slider;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Filter that crops the picture
 *
 */
public class Crop implements Layerable {
	private Point topLeft;
	private Point bottomRight;
	private int width;
	private int height;

	Rectangle r;

	public Crop(Point topLeft, Point bottomRight, int width, int height) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.width = width;
		this.height = height;

	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		System.out.println((int) topLeft.getX() + "   " + (int) topLeft.getY() + "   " + width + "    " + height);
		BufferedImage croppedImage = img.getBufferedImg().getSubimage((int) topLeft.getX(), (int) topLeft.getY(), width,
				height);
		LoadedImage newImage = new LoadedImage(croppedImage);
		return newImage;

	}

	@Override
	public String saveLayer() {
		String output = "Crop?" + topLeft + "?" + bottomRight + "?";
		return output;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Beskärning";
	}

	@Override
	public List<Slider> getSliders() {
		// TODO Auto-generated method stub
		return null;
	}

	public Point getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(Point topLeft) {
		this.topLeft = topLeft;
	}

	public Point getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Point bottomRight) {
		this.bottomRight = bottomRight;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		this.r = r;
	}

}
