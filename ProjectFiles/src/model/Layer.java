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

	public void setRadius(int intValue) {
		((Blur) action).setRadius(intValue);
	}
	
	
}
