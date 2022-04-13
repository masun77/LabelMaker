package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class RDFOrder implements Order {
	private String customerName = "";
	private ArrayList<RDFItem> items = new ArrayList<RDFItem>();
	
	public RDFOrder(String custName) {
		this.customerName = custName;
	}

	public void addItem(ArrayList<String> headers, ArrayList<String> info) {    
		
		for (int h = 0; h < headers.size(); h++) {
			String currKey = headers.get(h);
			if (headerColumns.containsKey(currKey)) {
				headerColumns.put(currKey, info.get(h));
			}
		}				
	}
	
	@Override
	public String toString() {
		return "";   // todo
	}
}
