package model;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;

import controllers.MainView;
import javafx.scene.control.Slider;

public class Blur implements Layerable {
	
	private int hRadius;
	private int vRadius;
	private int iterations = 1;
	
	public Blur() {
		this(5);
		//make it possible to change value?
	}

	public Blur(int radius) {
		setRadius(radius);
	}

	@Override
	public LoadedImage transform(LoadedImage img) {

		LoadedImage newImage = new LoadedImage(img);
		
		BufferedImage blurredImg = filter(newImage.getBufferedImg(), newImage.getBufferedImg());
	
		
		return new LoadedImage(blurredImg);
	}
	
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();

        if (dst == null)
            dst = createDestImage(src, null);

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        getRGB(src, 0, 0, width, height, inPixels);

        for (int i = 0; i < iterations; i++ ) {
            blur(inPixels, outPixels, width, height, hRadius);
            blur(outPixels, inPixels, height, width, vRadius);
        }

        setRGB(dst, 0, 0, width, height, inPixels);
        return dst;
    }
    
    public BufferedImage createDestImage(BufferedImage src, ColorModel dstCM) {
        if (dstCM == null)
            dstCM = src.getColorModel();
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }

    public static void blur(int[] in, int[] out, int width, int height, int radius) {
        int widthMinus1 = width-1;
        int tableSize = 2*radius+1;
        int divide[] = new int[256*tableSize];

        for (int i = 0; i < 256*tableSize; i++)
            divide[i] = i/tableSize;

        int inIndex = 0;
        
        for ( int y = 0; y < height; y++ ) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for ( int i = -radius; i <= radius; i++ ) {
                int rgb = in[inIndex + ImageMath.clamp(i, 0, width-1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for ( int x = 0; x < width; x++ ) {
                out[ outIndex ] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];

                int i1 = x+radius+1;
                if ( i1 > widthMinus1 )
                    i1 = widthMinus1;
                int i2 = x-radius;
                if ( i2 < 0 )
                    i2 = 0;
                int rgb1 = in[inIndex+i1];
                int rgb2 = in[inIndex+i2];
                
                ta += ((rgb1 >> 24) & 0xff)-((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000)-(rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00)-(rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff)-(rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }
        
	public void setHRadius(int hRadius) {
		this.hRadius = hRadius;
	}
	
	public int getHRadius() {
		return hRadius;
	}
	
	public void setVRadius(int vRadius) {
		this.vRadius = vRadius;
	}
	
	public int getVRadius() {
		return vRadius;
	}
	
	public void setRadius(int radius) {
		this.hRadius = this.vRadius = radius;
	}
	
	public int getRadius() {
		return hRadius;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	
	public int getIterations() {
		return iterations;
	}

	public int[] getRGB(BufferedImage img, int x, int y, int width, int height, int[] pixels) {
		int type = img.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
			return (int [])img.getRaster().getDataElements( x, y, width, height, pixels );
		return img.getRGB(x, y, width, height, pixels, 0, width);
    }

	public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
			image.getRaster().setDataElements( x, y, width, height, pixels );
		else
			image.setRGB(x, y, width, height, pixels, 0, width);
    }

	@Override
	public String saveLayer() {
		String output = "Blur?" + hRadius + "?" + vRadius + "?" + iterations + "?";
		return output;
	}

	@Override
	public String getName() {
		return "Osk�rpa";
	}

	@Override
	public List<Slider> getSliders() {
		List<Slider> sliders = new ArrayList<>();
		Slider radiusSlider = new Slider();
		radiusSlider.setMin(0);
		radiusSlider.setMax(255);
		radiusSlider.setValue(this.getRadius());
		radiusSlider.setOnDragDone(e -> {
			this.setRadius((int)radiusSlider.getValue());
			MainView.getCanvas().repaint();
			System.out.println("Radie " + radiusSlider.getValue());
		});
		sliders.add(radiusSlider);
		return sliders;
	}

	/*@Override
	public Layer openSavedLayer(String loadString) {
		String[] data = loadString.split("?");
		Blur bl = new Blur();
		//rewrite so that blur level isn't always 5
		return null;
	}*/

	
}