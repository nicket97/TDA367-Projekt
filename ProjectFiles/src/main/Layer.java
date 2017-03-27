package main;


public class Layer {
	private Layrable action;
	
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
