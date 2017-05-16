package model;


public class Layer {
	private Layerable action;
	private boolean visible;
	private String name = "";
	
	public Layer(Layerable l){
		this.action = l;
		this.visible = true;
		this.name = l.getName();
	}

	public Layerable getAction() {
		return action;
	}

	public void setaction(Layerable l) {
		this.action = l;
	}
	public boolean getVisible(){
		return visible;
	}
	public void changeVisible(){
		this.visible = !this.visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/***
	 * Methods for changing values of current layer.
	 * @param intValue
	 */
	public void setRadius(int intValue) {
		if (name.equals("Blur")){
			((Blur) action).setRadius(intValue);	
		} else {
			((GaussianBlur) action).setRadius(intValue);
		}
	}
	public void setRGB(double r, double g, double b){
		((ColorShift) action).setRGB(r, g, b);
	}
	public void setColor(String color){
		ColorShiftFactory.getColorShift(color);
	}
	
}
