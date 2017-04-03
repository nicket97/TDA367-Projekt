package model;


public class Layer {
	private Layerable action;
	private boolean visable;
	
	public Layer(Layerable l){
		this.action = l;
	}

	public Layerable getL() {
		return action;
	}

	public void setaction(Layerable l) {
		this.action = l;
	}
	
	
}
