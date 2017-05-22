package model.transformations;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import model.core.Layerable;
import model.core.LoadedImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Adds a chosen text to the picture
 *
 */
public class TextFilter implements Layerable {

	private String text = "Your text";
	private String font = "Helvetica";
	private int size = 40;
	private String yPosition = "center";
	private int r = 255;
	private int g = 255;
	private int b = 255;
	
	private TextField txtIn = new TextField();
	private ColorPicker customColor = new ColorPicker();
	private ChoiceBox<String> fontBox = new ChoiceBox<String>();
	private ChoiceBox<String> positionBox = new ChoiceBox<String>();
	private Slider sliderSize = new Slider();
	
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();
	private VBox v3 = new VBox();
	private VBox v4= new VBox();
	private VBox v5 = new VBox();
	
	

	public TextFilter(String text, String font, int size, String yPosition, int r, int g, int b) {
		this.text = text;
		this.font = font;
		this.size = size;
		this.yPosition = yPosition;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public TextFilter(String[] args) {
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		BufferedImage BImg = img.getBufferedImg();

		Graphics2D g2 = BImg.createGraphics();
		FontMetrics metrics = g2.getFontMetrics(new Font(font, Font.BOLD, size));
		g2.setColor(new java.awt.Color(r, g, b));
		g2.setFont(new Font(font, Font.BOLD, size));
		int x = (img.getWidth() - metrics.stringWidth(text)) / 2;
		int y = 75;
		if(this.yPosition.equals("uppe"))
			y = 75;
		else if(this.yPosition.equals("mitten"))
			y = img.getHeigth() / 2;
		else if(this.yPosition.equals("nere"))
			y = img.getHeigth() - 75;
			
		g2.drawString(text, x, y);

		return new LoadedImage(BImg);
	}

	@Override
	public String saveLayer() {
		String output = "TextFilter?" + text + "?" + size + "?" + yPosition + "?" + r + "?" + g + "?" + b + "?";
		return output;
	}

	@Override
	public String getName() {
		return "Textfilter";
	}

	
	public void setTextFilter(String text, String font, int size, String yPosition, int r, int g, int b) {
		this.text = text;
		this.font = font;
		this.size = size;
		this.yPosition = yPosition;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	

	public void setPosition(String yposition) {
		this.yPosition = yposition;
	}


	@Override
	public List<VBox> getVBox() {
		txtIn.setText(this.text);
		
		return null;
	}

	@Override
	public void uppdate() {
		// TODO Auto-generated method stub
		
	}
}

