package model.transformations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import com.sun.javafx.geom.Rectangle;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
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
	public Crop(String[] args){
		this(new Point((int)Double.parseDouble(args[1]), (int)Double.parseDouble(args[2])), new Point((int)Double.parseDouble(args[3]), (int)Double.parseDouble(args[4])), 
				(int)Double.parseDouble(args[5]), (int)Double.parseDouble(args[6]));
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		BufferedImage croppedImage = newImage.getBufferedImg().getSubimage((int) topLeft.getX(), (int) topLeft.getY(), width,
				height);
		LoadedImage finalImage = new LoadedImage(croppedImage);
		return finalImage;

	}

	
	@Override
	public String saveLayer() {
		String output = "Crop?" + topLeft.getX() + "?" + topLeft.getY() + "?" 
				+ bottomRight.getX() + "?"  + bottomRight.getY() + "?"
				+ this.width + "?" + this.height + "?";
		return output;
	}

	
	@Override
	public String getName() {
		return "Beskärning";
	}

	/**
	 * Gets the top left point
	 * @return top left point
	 */
	public Point getTopLeft() {
		return topLeft;
	}

	/**
	 * Sets the top left point
	 * @param topLeft point on the top left
	 */
	public void setTopLeft(Point topLeft) {
		this.topLeft = topLeft;
	}

	/**
	 * Gets the bottom right point
	 * @return bottom right point
	 */
	public Point getBottomRight() {
		return bottomRight;
	}

	/**
	 * Sets the bottom right point
	 * @param bottomRight point on the bottom right
	 */
	public void setBottomRight(Point bottomRight) {
		this.bottomRight = bottomRight;
	}

	/**
	 * Gets the width of the rectangle
	 * @return rectangle width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the rectangle
	 * @param width new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height of the rectangle
	 * @return rectangle height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the rectangle height
	 * @param height new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the rectangle
	 * @return rectangle
	 */
	public Rectangle getR() {
		return r;
	}

	/**
	 * Sets the rectangle
	 * @param r rectangle to be set
	 */
	public void setR(Rectangle r) {
		this.r = r;
	}

	@Override
	public List<VBox> getVBox() {
		return null;
	}

	@Override
	public void uppdate() {
		
		
	}
	@Override
	public boolean hasSettings() {
		return false;
	}

}
