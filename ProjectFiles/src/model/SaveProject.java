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
			
		    for(Layer layer : Layers.getLayerStack()){
		    	out.println(layer.getAction().saveLayer());
			}
		    LoadedImage img = MainView.getBackgroundImage();
		    out.println("??????????");
		    out.print(img.getBufferedImg().getWidth());
		    out.print("?");
		    out.print(img.getBufferedImg().getHeight());
		    out.println();
		    out.println("??????????");
		    for(int i = 0; i < img.getBufferedImg().getWidth(); i++){
		    	for(int j = 0; j < img.getBufferedImg().getHeight(); j++){
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