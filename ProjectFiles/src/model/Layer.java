package model;


public class Layer {
	private Layrable action;
	private boolean visable;
	
	public Layer(Layrable l){
		this.action = l;
	}

	public Layrable getL() {
		return action;
	}

	public void setaction(Layrable l) {
		this.action = l;
	}
	
	
}
