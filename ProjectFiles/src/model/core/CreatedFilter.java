package model.core;

/**
 * Puts together new filters
 *
 */

public class CreatedFilter {
	private double[][] kernel;
	private String name;

	/**
	 * Constructor of the CreatedFilter class
	 * @param name of the new filter
	 * @param kernel of values entered 
	 */
	public CreatedFilter(String name, double[][] kernel) {
		this.kernel = kernel;
		this.name = name;
	}

	/**
	 * Getter for the kernel-values
	 * @return the kernel-values
	 */
	public double[][] getKernel() {
		return kernel;
	}

	/**
	 * Setter for the kernel-values
	 * @param the new values for the kernel
	 */
	public void setKernel(double[][] kernel) {
		this.kernel = kernel;
	}
 
	/**
	 * Getter for the name of the filter
	 * @return the name of the filter
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name of the filter
	 * @param the new name for the filter
	 */
	public void setName(String name) {
		this.name = name;
	}

}
