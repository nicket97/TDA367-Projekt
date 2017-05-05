package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Layers;

public class OpenProject {
	
	//läs rad för rad till ????????
	//kolla vad som finns i
	//lägg till det objektet i layers
	//skapa loadedImage
	//ta in höjd + bredd
	//lägg till alla pixlar
	
	public static void openFile(){
	try {
	FileChooser fileChooser = new FileChooser();
	File f = fileChooser.showOpenDialog(new Stage());
	BufferedReader br = new BufferedReader(new FileReader(f));
	String line;
	while((line = br.readLine()) != null){
		int stage = 1;
		//check stage
		if (line.equalsIgnoreCase("??????????")){
			stage++;
		}
		//filter and values
		if (stage == 1){
			String[] information = line.split("?");
			String layerType = information[0];
			switch (layerType) {
			case "ColorShift": Layers.addLayer(new Layer(new ColorShift(information)));
				break;
			case "BlackAndWhite": Layers.addLayer(new Layer(new BlackAndWhite(information)));
				break;
			case "GaussianBlur": new GaussianBlur();
				break;
			case "GrayScale": new GrayScale();
				break;
			case "Sharpen": new Sharpen();
				break;
			case "Blur": new Blur();
				break;
			}
		}
	}
	
	}
	catch(Exception e) {
	}
	}
	
}
