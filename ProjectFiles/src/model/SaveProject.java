package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import controllers.MainView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Layers;

public class SaveProject {
	public static boolean saveProject(){
		try {
			FileChooser fileChooser = new FileChooser();
			File outputfile =  fileChooser.showSaveDialog(new Stage());
			PrintWriter out = new PrintWriter(outputfile.getName() + ".nh");
			System.out.println("sparar " +  outputfile.getName());
			out.println("Detta är en sparfile för vår fina sak");
		    for(Layer layer : Layers.getLayerStack()){
		    	out.println(layer.getAction().saveLayer());
			}
		    out.close();
		    
		    
		    
		    
		} catch (IOException e1) {
		   
		}
		return true;
	}
}
