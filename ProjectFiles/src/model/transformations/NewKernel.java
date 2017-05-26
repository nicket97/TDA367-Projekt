package model.transformations;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.core.CreatedFilter;
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
	
<<<<<<< HEAD
	private Label labelText = new Label();
	private VBox v = new VBox();
	private ComboBox<String> filterBox = new ComboBox<String>();
	
	private boolean hasSettings = true;
=======
	private final boolean hasSettings = false;
>>>>>>> 7bb0fb1eb7f337201231391926505f97b2bfff5e

	public NewKernel(double[][] kernel, String name) {
		this.kernel = kernel;
		this.name = name;
		
		labelText.setText("Välj filter");
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
				pxImage[i][j] = Color.rgb(ColorTransformTest.getAllowedValue(sumRed/count), ColorTransformTest.getAllowedValue(sumGreen/count), ColorTransformTest.getAllowedValue(sumBlue/count));
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
	public List<VBox> getVBox() {
		initiateVBox(v);
		
		ObservableList<String> filters = FXCollections.observableArrayList();
		for (CreatedFilter f : NewFilterHandeler.getFilters()) {
			filters.add(f.getName());
		}
		filterBox.getItems().addAll(filters);
		filterBox.setPrefWidth(150.0);
		
		v.getChildren().addAll(filterBox, labelText);
		
		List<VBox> vboxList = new ArrayList<VBox>();
		
		vboxList.add(v);
		
		return vboxList;
	}

	private void initiateVBox(VBox v) {
		v.getChildren().clear();
		v.setTranslateY(30);
		v.setAlignment(Pos.BASELINE_CENTER);
		v.setSpacing(10);
		v.setPadding(new Insets(0, 15, 0, 15));;
	}
	
	@Override
	public void uppdate() {

	}

	@Override
	public boolean hasSettings() {
		return hasSettings;
	}
	
}
