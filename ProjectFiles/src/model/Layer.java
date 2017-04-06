package model;


public class Layer {
	private Layerable action;
	private boolean visable;
	
	public Layer(Layerable l){
		this.action = l;
		this.visable = true;
	}

	public Layerable getAction() {
		return action;
	}

	public void setaction(Layerable l) {
		this.action = l;
	}
	public void changeVisable(){
		this.visable = !this.visable;
	}
	
	
}
