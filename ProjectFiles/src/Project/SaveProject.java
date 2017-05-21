package Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import controllers.MainView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.core.Layer;
import model.core.Layers;
import model.core.LoadedImage;

/**
 * Takes care of things related to saving projects
 * 
 *
 */
public class SaveProject {
	public static boolean saveProject() {
		try {
			FileChooser fileChooser = new FileChooser();
			File outputfile = fileChooser.showSaveDialog(new Stage());
			PrintWriter out = new PrintWriter(outputfile.getAbsolutePath() + ".nh");
			System.out.println("sparar " + outputfile.getName());

			for (Layer layer : Layers.getLayerStack()) {
				out.println(layer.getAction().saveLayer());
			}
			LoadedImage img = Layers.getBackgroundImage();
			out.println("??????????");
			out.print(img.getBufferedImg().getWidth());
			out.print("?");
			out.print(img.getBufferedImg().getHeight());
			out.println();
			out.println("??????????");
			for (int i = 0; i < img.getBufferedImg().getWidth(); i++) {
				for (int j = 0; j < img.getBufferedImg().getHeight(); j++) {
					out.print(img.getBufferedImg().getRGB(i, j) + "?");
				}
				out.println();
			}
			out.println("??????????");
			out.close();

		} catch (IOException e1) {

		}
		return true;
	}
}
