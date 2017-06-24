package model.transformations;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.nio.charset.Charset;
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
 * Adds a copyright mark with your name in the right bottom corner.
 * You will be able to change your name, font, size and color. 
 * @author anton
 *
 */
public class CopyRight implements Layerable {

	String name;
	private String font;
	private int size;
	private int r;
	private int g;
	private int b;
	private final boolean hasSettings = true;
	
	private TextField txtIn = new TextField();
	private Label labelText = new Label("Skriv in text");
	private ColorPicker customColor = new ColorPicker();
	private Label labelColor = new Label("Välj färg");
	private ComboBox<String> fontBox = new ComboBox<String>();
	private Label labelFont = new Label("Välj typsnitt");
	private Slider sliderSize = new Slider();
	private Label labelSize = new Label("Storlek");
	
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();
	private VBox v3 = new VBox();
	private VBox v5 = new VBox();
	
	public CopyRight(String name, String font, int size, int r, int g, int b) {
		sliderSize.setMin(0);
		sliderSize.setMax(100);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		ObservableList<String> fontList = FXCollections.observableArrayList(fonts);
		fontBox.setItems(fontList);
		
		this.name = name;
		this.font = font;
		this.size = size;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Sets starting options for filter
	 */
	public CopyRight(){
		this("Ditt namn", "Helvetica", 20, 255, 255, 255);
	}
	
	public CopyRight(String[] args) {
		this(args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
	}

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		
		Graphics2D g2 = newImage.getBufferedImg().createGraphics();
		g2.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		FontMetrics metrics = g2.getFontMetrics(new Font(font, Font.BOLD, size));
		g2.setColor(new java.awt.Color(r, g, b));
		g2.setFont(new Font(font, Font.BOLD, size));
		
		int x = (img.getWidth() - (metrics.stringWidth("\u00A9" + name) + (size / 2)));
		int y = (int) (img.getHeigth() - (size / 3));
		
		g2.drawString("\u00A9 " + name, x, y);
		
		return new LoadedImage(newImage.getBufferedImg());
	}
	
	@Override
	public String saveLayer() {
		String output = "CopyRight?" + name + "?" + font + "?" + size + "?" + r + "?" + g + "?" + b + "?";
		return output;
	}
	
	@Override
	public String getName() {
		return "Copyright";
	}
	
	@Override
	public List<VBox> getVBox() {
		initiateVBox(v1, 15);
		initiateVBox(v2, 20);
		initiateVBox(v3, 25);
		initiateVBox(v5, 45);
		txtIn.setText(this.name);
		v1.getChildren().addAll(txtIn, labelText);
		v1.setPrefWidth(170);
		
		customColor.setValue(Color.rgb(r, g, b));
		customColor.setMinHeight(25);
		v2.getChildren().addAll(customColor, labelColor);
		v2.setPrefWidth(140);
		
		fontBox.setValue(this.font);
		v3.getChildren().addAll(fontBox, labelFont);
		v3.setPrefWidth(200);
		
		sliderSize.setValue(this.size);
		v5.getChildren().addAll(sliderSize, labelSize);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v1);
		vboxList.add(v2);
		vboxList.add(v3);
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
		this.name = txtIn.getText();
		this.r = (int) ((customColor.getValue().getRed())*255);
		this.g = (int) ((customColor.getValue().getGreen())*255);
		this.b = (int) ((customColor.getValue().getBlue())*255);
		this.font = fontBox.getValue();
		this.size = (int) sliderSize.getValue();
	}
	
	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
}
