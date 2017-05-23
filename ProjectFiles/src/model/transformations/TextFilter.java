package model.transformations;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.Layerable;
import model.core.LoadedImage;

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
	private Label labelText = new Label("Skriv in text");
	private ColorPicker customColor = new ColorPicker();
	private Label labelColor = new Label("Välj färg");
	private ComboBox<String> fontBox = new ComboBox<String>();
	private Label labelFont = new Label("Välj typsnitt");
	private ComboBox<String> positionBox = new ComboBox<String>();
	private Label labelPosition = new Label("Välj position");
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
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		ObservableList<String> fontList = FXCollections.observableArrayList(fonts);
		fontBox.setItems(fontList);
		
		ObservableList<String> positionList = FXCollections.observableArrayList();
		positionList.add("uppe");
		positionList.add("mitten");
		positionList.add("nere");
		positionBox.setItems(positionList);
		
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
		LoadedImage newImage = new LoadedImage(img);
		BufferedImage BImg = newImage.getBufferedImg();

		Graphics2D g2 = BImg.createGraphics();
		FontMetrics metrics = g2.getFontMetrics(new Font(font, Font.BOLD, size));
		g2.setColor(new java.awt.Color(r, g, b));
		g2.setFont(new Font(font, Font.BOLD, size));
		int x = (img.getWidth() - metrics.stringWidth(text)) / 2;
		int y = 125;
		if(this.yPosition.equals("uppe"))
			y = 125;
		else if(this.yPosition.equals("mitten"))
			y = img.getHeigth() / 2;
		else if(this.yPosition.equals("nere"))
			y = img.getHeigth() - 50;
			
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
		initiateVBox(v1, 15);
		initiateVBox(v2, 20);
		initiateVBox(v3, 25);
		initiateVBox(v4, 25);
		initiateVBox(v5, 45);
		txtIn.setText(this.text);
		v1.getChildren().addAll(txtIn, labelText);
		v1.setPrefWidth(170);
		
		customColor.setValue(Color.rgb(this.r, this.g, this.b));
		customColor.setMinHeight(25);
		v2.getChildren().addAll(customColor, labelColor);
		v2.setPrefWidth(140);
		
		fontBox.setValue(this.font);
		v3.getChildren().addAll(fontBox, labelFont);
		v3.setPrefWidth(200);
		
		positionBox.setValue(this.yPosition);
		v4.getChildren().addAll(positionBox, labelPosition);
		
		sliderSize.setValue(this.size);
		v5.getChildren().addAll(sliderSize, labelSize);
		
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
		this.text = txtIn.getText();
		this.r = (int) customColor.getValue().getRed()*255;
		this.g = (int) customColor.getValue().getGreen()*255;
		this.b = (int) customColor.getValue().getBlue()*255;
		this.font = fontBox.getValue();
		this.yPosition = positionBox.getValue();
		this.size = (int) sliderSize.getValue();
		
	}
}

