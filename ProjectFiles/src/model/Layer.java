package model;


public class Layer {
	private Layerable action;
	private boolean visable;
	private String name = "";
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
