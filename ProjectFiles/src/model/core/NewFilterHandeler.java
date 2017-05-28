package model.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Keep a list of filters created by the user
 *
 */
public class NewFilterHandeler {
	private static List<CreatedFilter> filters = new ArrayList<>();

	/**
	 * Adds filter to new filters
	 * @param filter filter being added
	 */
	public static void addFilter(CreatedFilter filter) {
		filters.add(filter);
	}

	/**
	 * Removes filters
	 * @param filter filter being removed
	 */
	public static void removeFilter(CreatedFilter filter) {
		filters.remove(filter);
	}
	
	/**
	 * Gets the kernel for the filter
	 * @param name name of the filter
	 * @return the kernel-values for the filter
	 */
	public static double[][] getFilterKernel(String name){
		for(CreatedFilter f : filters){
			if (name.equals(f.getName())){
				return f.getKernel();
			}
		}
		return null;
	}

	/**
	 * Gets the list of filters
	 * @return list of filters
	 */
	public static List<CreatedFilter> getFilters() {
		return filters;
	}

	public static void saveFilters() {
		System.out.println("saving filters" + filters.size());
		try {
			File outputfile = new File("filters.txt");
			PrintWriter out = new PrintWriter(outputfile.getPath());
			out.flush();
			for (CreatedFilter f : filters) {
				out.println(f.getName());
				for (int i = 0; i < f.getKernel().length; i++) {
					out.print(f.getKernel()[0][i]);
					out.print("?");
					out.print(f.getKernel()[1][i]);
					out.print("?");
					out.print(f.getKernel()[2][i]);
					out.println();
				}
				out.println("??????????");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	/**
	 * Loads the list of filters
	 */
	public static void loadFilters() {

		try {
			File f = new File("filters.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			int c = 0;
			double[][] kernel = new double[3][3];
			String name = "";
			while ((line = br.readLine()) != null) {
				
				if (line.equalsIgnoreCase("??????????")) {

					NewFilterHandeler.addFilter(new CreatedFilter(name, kernel));
					c = 0;
					kernel = new double[3][3];
					name = "";
					continue;
				} else {
					if (c == 0) {
						name = line;
						c++;
					} else {
						String[] args = line.split("\\?");
						kernel[c - 1][0] = Double.parseDouble(args[0]);
						kernel[c - 1][1] = Double.parseDouble(args[1]);
						kernel[c - 1][2] = Double.parseDouble(args[2]);
						c++;
					}
				}

			}
			System.out.println("filters" + filters.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	/**
	 * Gets all created filters
	 * @param value values of filters
	 * @return filter values 
	 */
	public static CreatedFilter getFilter(String value) {
		for(CreatedFilter f : filters){
			if(f.getName().equals(value)){
				return f;
			}
			
		}
		return null;
	}

}
