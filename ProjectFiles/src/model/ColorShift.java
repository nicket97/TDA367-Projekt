package model;

import java.util.ArrayList;
import java.util.List;

import controllers.MainView;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class ColorShift extends ColorFilter{

		private double r;
		private double g;
		private double b;
		 
	    

		public ColorShift(double redAddend, double greenAddend, double blueAddend) {
	         this.r = redAddend;
	         this.g = greenAddend;
	         this.b = blueAddend;
	     }
		
		public ColorShift(String[] arg) {
			this.r = Double.parseDouble(arg[1]);
			this.g = Double.parseDouble(arg[2]);
			this.b = Double.parseDouble(arg[3]);
			
		}
	 
		public LoadedImage transform(LoadedImage img) {
	    	 LoadedImage newImage = new LoadedImage(img);
	 
	         for(int i = 0; i < newImage.getpxImage().length; i++){
	             for(int j = 0; j < newImage.getpxImage()[i].length; j++){
	 
	                 Color pxColor = newImage.getpxImage()[i][j];
					 double newRed = pxColor.getRed() * 255 + r;
					 double newGreen = pxColor.getGreen() * 255 + g;
					 double newBlue = pxColor.getBlue() * 255 + b;
	                 //pxColor = Color.rgb((int) (((newRed) > 255) ? 255 : ((newRed) < 0) ? 0 : newRed), (int) (((newGreen) > 255) ? 255 : newGreen), (int) (((newBlue + b) > 255) ? 255 : newBlue + b));
					 pxColor = Color.rgb(getAllowedValue(newRed), getAllowedValue(newGreen), getAllowedValue(newBlue));
					 newImage.getpxImage()[i][j] = pxColor;
	             }
	         }
	         return newImage;
		}

		private int getAllowedValue(double newColor) {
			if (newColor > 255) {
				newColor = 255;
			}
			else if (newColor < 0) {
				newColor = 0;
			}
			return (int) newColor;

		}

		public double getR() {
				return r;
		}

		public void setR(double r) {
			this.r = r;
		}

		public double getG() {
			return g;
		}

		public void setG(double g) {
			this.g = g;
		}

		public double getB() {
			return b;
		}

		public void setB(double b) {
			this.b = b;
		}

		@Override
		public String saveLayer() {
			String output = "ColorShift?" + r + "?" + g + "?" + b + "?"; 
			return output;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Color Shift";
		}
		public List<Slider> getSliders(){
			List<Slider> sliders = new ArrayList<>();
			Slider redSlider = new Slider();
			redSlider.setMin(0);
			redSlider.setMax(255);
			redSlider.setValue(r);
			redSlider.setOnDragDone(e -> {
				r = redSlider.getValue();
				MainView.getCanvas().repaint();
				System.out.println("Röd " + redSlider.getValue());
			});
			
			Slider blueSlider = new Slider();
			blueSlider.setMin(0);
			blueSlider.setMax(255);
			blueSlider.setValue(r);
			blueSlider.setOnDragDone(e -> {
				b = blueSlider.getValue();
				MainView.getCanvas().repaint();
				System.out.println("Blå " + blueSlider.getValue());
			});
			
			Slider greenSlider = new Slider();
			greenSlider.setMin(0);
			greenSlider.setMax(255);
			greenSlider.setValue(r);
			greenSlider.setOnDragDone(e -> {
				g = greenSlider.getValue();
				MainView.getCanvas().repaint();
				System.out.println("grön" + greenSlider.getValue());
			});
			
			sliders.add(redSlider);
			sliders.add(greenSlider);
			sliders.add(blueSlider);
			return sliders;
			
		}

	/*	@Override
		public Layer openSavedLayer(String loadString) {
			String[] data = loadString.split("?");
			ColorShift cs = new ColorShift(Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
			return new Layer(cs);
		} */

		
	 

}
