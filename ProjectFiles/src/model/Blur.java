package model;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Blur extends ColorFilter {

	@Override
	public LoadedImage transform(LoadedImage img) {
		LoadedImage newImage = new LoadedImage(img);
		
		/**float[] matrix = {
			        0.111f, 0.111f, 0.111f, 
			        0.111f, 0.111f, 0.111f, 
			        0.111f, 0.111f, 0.111f, 
			    };**/
		
		float[] matrix = new float[400];
		for (int i = 0; i < 400; i++)
			matrix[i] = 1.0f/400.0f;
		
		ConvolveOp op = new ConvolveOp( new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null );
		BufferedImage blurredImg = op.filter(newImage.getBufferedImg(newImage), null);
		
		LoadedImage finalImg = new LoadedImage(blurredImg);
		
		return finalImg;
	}

}