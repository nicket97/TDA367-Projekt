package model;

import java.util.ArrayList;
import java.util.List;

public class NewFilterHandeler {
	private List<CreatedFilter> filters = new ArrayList<>();

	public void addFilter(CreatedFilter filter){
		filters.add(filter);
	}
	public void removeFilter(CreatedFilter filter){
		filters.remove(filter);
	}
	public List<CreatedFilter> getFilters() {
		return filters;
	}

	
	
}
