package model;

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

	public static void addFilter(CreatedFilter filter) {
		filters.add(filter);
	}

	public static void removeFilter(CreatedFilter filter) {
		filters.remove(filter);
	}

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

}
