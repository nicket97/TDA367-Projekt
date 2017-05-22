package model.transformations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

	private String text;
	private String font;
	private int size;
	private String yPosition;
	private int r;
	private int g;
	private int b;
	
	private TextField txtIn = new TextField();
	private Label labelText = new Label("Din Text");
	private ColorPicker customColor = new ColorPicker();
	private Label labelColor = new Label("Välj färg");
	private ChoiceBox<String> fontBox = new ChoiceBox<String>();
	private Label labelFont = new Label("Font");
	private ChoiceBox<String> positionBox = new ChoiceBox<String>();
	private Label labelPosition = new Label("Position");
	private Slider sliderSize = new Slider();
	private Label labelSize = new Label("Storlek");
	
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();
	private VBox v3 = new VBox();
	private VBox v4 = new VBox();
	private VBox v5 = new VBox();
	
	

	public TextFilter(String text, String font, int size, String yPosition, int r, int g, int b) {
		sliderSize.setMin(0);
		sliderSize.setMax(200);
		
		ObservableList<String> fontList = FXCollections.observableArrayList();
		fontList.add("Helvetica");
		fontBox.setItems(fontList);
		
		ObservableList<String> positionList = FXCollections.observableArrayList();
		positionList.add("uppe");
		positionList.add("mitten");
		positionList.add("nere");
		fontBox.setItems(fontList);
		
		this.text = text;
		this.font = font;
		this.size = size;
		this.yPosition = yPosition;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public TextFilter(String[] args) {
		this(args[1], args[2], Integer.parseInt(args[3]), args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]) , Integer.parseInt(args[7]));
	}
	public TextFilter(){
		this("Din text", "Helvetica", 40, "center", 255, 255, 255);
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
		String output = "TextFilter?" + text + "?" + font + "?" + size + "?" + yPosition + "?" + r + "?" + g + "?" + b + "?";
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
		
		customColor.setValue(Color.rgb(this.r, this.g, this.b));
		
		fontBox.setValue(this.font);
		
		positionBox.setValue(this.yPosition);
		
		
		return null;
	}

	@Override
	public void uppdate() {
		// TODO Auto-generated method stub
		
	}
}

