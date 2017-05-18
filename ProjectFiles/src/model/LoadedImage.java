package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javafx.scene.paint.Color;

public class LoadedImage {
	private Color[][] pxImage;
	private BufferedImage lImg;
	private int width;
	private int heigth;
	
	public void setPxImage(Color[][] pxImage) {
		this.pxImage = pxImage;
	}


	public void setlImg(BufferedImage lImg) {
		this.lImg = lImg;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}


	public LoadedImage(BufferedImage img){
		this.lImg = img;
		this.width = img.getWidth();
		this.heigth = img.getHeight();
		this.pxImage = new Color[width][heigth];
		 class CreateImage implements Runnable {
			 Color[][] pxImage;
			 int n;
		        CreateImage(Color[][] s, int x) { pxImage = s;
		        n = x;}
		        public void run() {
		        	for(int i = n; i < width; i += 4){
		    			for(int j = 0; j < heigth; j++){
		    				pxImage[i][j] = getColorFromInt(img.getRGB(i, j));
		    			}
		        }
		    }
		 }
		    Thread t1 = new Thread(new CreateImage(pxImage, 0));
		    Thread t2 = new Thread(new CreateImage(pxImage, 1));
		    Thread t3 = new Thread(new CreateImage(pxImage, 2));
		    Thread t4 = new Thread(new CreateImage(pxImage, 3));
		    t1.start();
		    t2.start();
		    t3.start();
		    t4.start();
		    
		    try {
				t1.join();
				System.out.println("1");
				t2.join();
				System.out.println("2");
				t3.join();
				System.out.println("3");
				t4.join();
				System.out.println("4");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		/*for(int i = 0; i < width; i++){
			for(int j = 0; j < heigth; j++){
				pxImage[i][j] = getColorFromInt(img.getRGB(i, j));
			}
		}*/
		//System.out.println("hej" + pxImage[0][0].getRed()*255 + " eller " + ((img.getRGB(0, 0)>>16)&0xFF));
	}
	
	
	public LoadedImage(LoadedImage img){
		this.width = img.width;
		this.heigth = img.heigth;
		this.pxImage = new Color[width][heigth];
		for(int i = 0; i < img.pxImage.length; i++){
			for(int j = 0; j < img.pxImage[i].length; j++){
				this.pxImage[i][j] = img.pxImage[i][j];
			}
		}
		BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; i++){
        	for(int j = 0; j < heigth; j++){
        		image.setRGB(i, j, LoadedImage.getIntFromColor(pxImage[i][j]));
        	}
        }
        this.lImg = image;
		//System.out.println("heja" + pxImage[0][0].getRed()*255 + " eller " + ((lImg.getRGB(0, 0)>>16)&0xFF));
	}
	
	public BufferedImage getBufferedImg(){
		return lImg;
	}
	
	public static Color getColorFromInt(int color){
		int argb =  color;
		int r = (argb>>16)&0xFF;
		int g = (argb>>8)&0xFF;
		int b = (argb>>0)&0xFF;
		return Color.rgb(r, g, b);
		
	}
	
	public static int getIntFromColor(Color rgb){
		
	    int R = (int) Math.round(255 * rgb.getRed());
	    int G = (int) Math.round(255 * rgb.getGreen());
	    int B = (int) Math.round(255 * rgb.getBlue());

	    R = (R << 16) & 0x00FF0000;
	    G = (G << 8) & 0x0000FF00;
	    B = B & 0x000000FF;

	    return 0xFF000000 | R | G | B;
	}
	public Color[][] getpxImage(){
		return pxImage;
		
	}
	public int getWidth(){
		return width;
	}
	public int getHeigth(){
		return heigth;
	}
}
