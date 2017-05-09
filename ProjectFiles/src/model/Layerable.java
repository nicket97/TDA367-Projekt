package model;

public interface Layerable {
	
	public LoadedImage transform(LoadedImage img);
	public String saveLayer();
	public String getName();
	
}
