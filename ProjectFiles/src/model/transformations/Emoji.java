package model.transformations;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

/**
 * Adds a chosen text to the picture
 *
 */
public class Emoji implements Layerable {

	private String emojiText;
	private final String font = "Arial";
	private int size;
	private int r;
	private int g;
	private int b;
	private int x;
	private int y;
	private final boolean hasSettings = true;
	
	private ComboBox<String> emoji = new ComboBox<String>();
	private Label labelEmoji = new Label("Välj emoji");
	private ColorPicker customColor = new ColorPicker();
	private Label labelColor = new Label("Välj färg");
	private Slider sliderSize = new Slider();
	private Label labelSize = new Label("Storlek");
	private Slider sliderX = new Slider();
	private Label labelX = new Label("X");
	private Slider sliderY = new Slider();
	private Label labelY = new Label("Y");
	
	
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();
	private VBox v3 = new VBox();
	private VBox v4 = new VBox();
	private VBox v5 = new VBox();
	
	

	/**
	 * Constructor of the TextFilter class
	 * @param text text to be written
	 * @param font font pof written text
	 * @param size size of written text
	 * @param yPosition y-postition of the text
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */
	public Emoji(String emojiText, int size, int r, int g, int b, int x, int y) {
		sliderSize.setMin(0);
		sliderSize.setMax(400);
		sliderX.setMin(0);
		sliderX.setMax(100);
		sliderY.setMin(0);
		sliderX.setMax(100);
				
		ObservableList<String> emojiList = FXCollections.observableArrayList();
		emojiList.add("\u263A");
		emojiList.add("\u2639");
		emojiList.add("\u263C");
		emojiList.add("\u2661");
		emojiList.add("\u2665");

		emoji.setItems(emojiList);
		
		this.emojiText = emojiText;
		this.size = size;
		this.r = r;
		this.g = g;
		this.b = b;
		this.x = x;
		this.y = y;
	}

	public Emoji(String[] args) {
		this(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]) , Integer.parseInt(args[6]) , Integer.parseInt(args[7]));
	}
	/**
	 * Sets starting options for filter
	 */
	public Emoji(){
		this("\u263A", 50, 255, 255, 255, 50, 50);
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		
		Graphics2D g2 = newImage.getBufferedImg().createGraphics();
		g2.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//FontMetrics metrics = g2.getFontMetrics(new Font(font, Font.BOLD, size));
		g2.setColor(new java.awt.Color(r, g, b));
		g2.setFont(new Font(font, Font.BOLD, size));
		int xPos = newImage.getWidth() * x / 100 ;
		int yPos = newImage.getHeigth() * (100 - y) / 100;
		
		g2.drawString(emojiText, xPos, yPos);
		
		return new LoadedImage(newImage.getBufferedImg());
	}

	
	@Override
	public String saveLayer() {
		String output = "Emoji?" + emojiText + "?" + size + "?" + r + "?" + g + "?" + b + "?" + x + "?" + y + "?";
		return output;
	}

	
	@Override
	public String getName() {
		return "Emoji";
	}

	@Override
	public List<VBox> getVBox() {
		initiateVBox(v1, 15);
		initiateVBox(v2, 20);
		initiateVBox(v3, 25);
		initiateVBox(v4, 25);
		initiateVBox(v5, 45);
		emoji.setValue(this.emojiText);
		v1.getChildren().addAll(emoji, labelEmoji);
		v1.setPrefWidth(100);
		
		customColor.setValue(Color.rgb(this.r, this.g, this.b));
		customColor.setMinHeight(25);
		v2.getChildren().addAll(customColor, labelColor);
		v2.setPrefWidth(140);
		
		sliderSize.setValue(this.size);
		v3.getChildren().addAll(sliderSize, labelSize);
		v3.setPrefWidth(200);
		
		sliderY.setValue(this.y);
		v4.getChildren().addAll(sliderY, labelY);
		
		sliderX.setValue(this.y);
		v5.getChildren().addAll(sliderX, labelX);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		vboxList.add(v2);
		vboxList.add(v3);
		vboxList.add(v4);
		vboxList.add(v5);
		
		return vboxList;
	}

	private void initiateVBox(VBox v, double d) {
		v.getChildren().clear();
		v.setTranslateY(d);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
		v.setPadding(new Insets(0, 15, 0, 15));;
	}

	@Override
	public void uppdate() {
		this.emojiText = emoji.getValue();
		this.size = (int) sliderSize.getValue();
		this.r = (int) (customColor.getValue().getRed()*255);
		this.g = (int) (customColor.getValue().getGreen()*255);
		this.b = (int) (customColor.getValue().getBlue()*255);
		this.x = (int) sliderX.getValue();
		this.y = (int) sliderY.getValue();
		
	}

	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
