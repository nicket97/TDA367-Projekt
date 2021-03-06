package Project;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import controllers.MainView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;
import model.core.LoadedImage;
import model.transformations.BlackAndWhite;
import model.transformations.Blur;
import model.transformations.ColorShift;
import model.transformations.Contrast;
import model.transformations.CopyRight;
import model.transformations.Crop;
import model.transformations.Edge;
import model.transformations.Emoji;
import model.transformations.Exposure;
import model.transformations.GaussianBlur;
import model.transformations.Grain;
import model.transformations.GrayScale;
import model.transformations.HMirroring;
import model.transformations.Levels;
import model.transformations.NewKernel;
import model.transformations.RotateL;
import model.transformations.RotateR;
import model.transformations.Sharpen;
import model.transformations.TextFilter;
import model.transformations.VMirroring;
import model.transformations.WhiteBalance;
import model.transformations.core.Layer;
import model.transformations.core.Layers;

/**
 * Takes care of things related to opening previous saved projects
 *
 */
public class OpenProject {

	

	public static void openFile() {
		try {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("NH files (*.nh)", "*.nh");
			fileChooser.getExtensionFilters().add(extFilter);
			;
			File f = fileChooser.showOpenDialog(new Stage());
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			int imgHeigth = 0;
			int imgWidth = 0;
			int pxArray[][] = { {} };
			int row = 0;
			String[] information;
			int stage = 1;
			while ((line = br.readLine()) != null) {

				// check stage
				if (line.equalsIgnoreCase("??????????")) {
					stage++;
					System.out.println(stage);
					continue;
				}
				// filter and values
				if (stage == 1) {
					information = line.split("\\?");
					String layerType = information[0];
					switch (layerType) {
					case "ColorShift":
						Layers.addLayer(new Layer(new ColorShift(information)));
						break;
					case "BlackAndWhite":
						Layers.addLayer(new Layer(new BlackAndWhite(information)));
						break;
					case "GaussianBlur":
						Layers.addLayer(new Layer(new GaussianBlur(information)));
						break;
					case "GreyScale":
						Layers.addLayer(new Layer(new GrayScale()));
						break;
					case "Sharpen":
						Layers.addLayer(new Layer(new Sharpen()));
						break;
					case "Blur":
						Layers.addLayer(new Layer(new Blur(information)));
						break;
					case "HMirroring":
						Layers.addLayer(new Layer(new HMirroring()));
						break;
					case "VMirroring":
						Layers.addLayer(new Layer(new VMirroring()));
						break;
					case "Contrast":
						Layers.addLayer(new Layer(new Contrast(information)));
						break;
					case "Levels":
						Layers.addLayer(new Layer(new Levels(information)));
						break;
					case "RotateR":
						Layers.addLayer(new Layer(new RotateR()));
						break;
					case "RotateL":
						Layers.addLayer(new Layer(new RotateL()));
						break;
					case "WhiteBalance":
						Layers.addLayer(new Layer(new WhiteBalance(information)));
						break;
					case "Exposure":
						Layers.addLayer(new Layer(new Exposure(information)));
						break;
					case "Grain":
						Layers.addLayer(new Layer(new Grain(information)));
						break;
					case "TextFilter":
						Layers.addLayer(new Layer(new TextFilter(information)));
						break;
					case "Crop":
						Layers.addLayer(new Layer(new Crop(information)));
						break;
					case "Kanter":
						Layers.addLayer(new Layer(new Edge(information)));
						break;
					case "NewKernel":
						Layers.addLayer(new Layer(new NewKernel(information)));
					case "CopyRight":
						Layers.addLayer(new Layer(new CopyRight(information)));
						break;
					case "Emoji":
						Layers.addLayer(new Layer(new Emoji(information)));
						break;
					}
					
				}
				if (stage == 2) {
					information = line.split("\\?");
					System.out.println(information.length + "jidjlkjfdjdö");
					System.out.println(information[0] + "       " + information[1]);
					imgWidth = Integer.parseInt("" + information[0]);
					imgHeigth = Integer.parseInt("" + information[1]);
					pxArray = new int[imgWidth][imgHeigth];
					System.out.println("stage 2");

				}
				if (stage == 3) {

					information = line.split("\\?");
					int col = 0;
					for (String x : information) {
						// System.out.println(x + " :::::: " +
						// Integer.parseInt(x));
						pxArray[row][col] = Integer.parseInt(x);
						// System.out.print(x);
						// System.out.print("?");
						col++;
					}

					row++;
				}

			}
			if (stage == 4) {
				System.out.println("Stage 4");
				BufferedImage image = new BufferedImage(imgWidth, imgHeigth, BufferedImage.TYPE_INT_ARGB);
				for (int i = 0; i < imgWidth; i++) {
					for (int j = 0; j < imgHeigth; j++) {
						image.setRGB(i, j, pxArray[i][j]);
					}
				}
				System.out.println("hhehejhejhejhje" + LoadedImage.getColorFromInt(image.getRGB(0, 0)) + "---------"
						+ LoadedImage.getColorFromInt(pxArray[0][0]));
				Layers.setBackgroundImage(new LoadedImage(image));
				System.out.println("Loading project");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
