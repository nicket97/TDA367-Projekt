package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

public class NewFilterHandeler {
	private static List<CreatedFilter> filters = new ArrayList<>();

	public static void addFilter(CreatedFilter filter){
		filters.add(filter);
	}
	public static void removeFilter(CreatedFilter filter){
		filters.remove(filter);
	}
	public static  List<CreatedFilter> getFilters() {
		return filters;
	}
	
	public static void saveFilters(){
	
		try {
			File outputfile =  new File("");
			PrintWriter out = new PrintWriter(outputfile.getPath() +  ".nh");
			for(CreatedFilter f: filters){
				out.println(f.getName());
				for(int i = 0; i < f.getKernel().length; i++){
					out.print(f.getKernel()[i][0]);
					out.print("?");
					out.print(f.getKernel()[i][1]);
					out.print("?");
					out.print(f.getKernel()[i][2]);
					out.println();
				}
				out.println("??????????");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void loadFilters(){
		
		try {
			File f = new File("");
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			int c = 0;
			double[][] kernel = new double[3][3];
			String name = "";
				while((line = br.readLine()) != null){
					if(line.equalsIgnoreCase("??????????")){
						
						NewFilterHandeler.addFilter(new CreatedFilter(name, kernel));
						c = 0;
						name = "";
						continue;
					}else{
						if(c == 0){
							name = line;
							c++;
						}
						else{
							String[] args = line.split("\\?");
							kernel[c-1][0] = Double.parseDouble(args[0]);
							kernel[c-1][1] = Double.parseDouble(args[1]);
							kernel[c-1][2] = Double.parseDouble(args[2]);
						}
					}
					
				
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
}
