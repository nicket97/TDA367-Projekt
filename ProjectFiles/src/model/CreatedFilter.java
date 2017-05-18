package model;
/**
 * Puts together new filters
 *
 */
public class CreatedFilter {
	private double[][] kernel;
	private String name;
	

	public CreatedFilter(String name, double[][] kernel){
		this.kernel = kernel;
		this.name = name;
	}
	
	public double[][] getKernel() {
		return kernel;
	}

	public void setKernel(double[][] kernel) {
		this.kernel = kernel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
