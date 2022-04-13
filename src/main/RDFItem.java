package main;

import java.util.HashMap;

public class RDFItem implements Item {
	private HashMap<String, String> details = new HashMap<String, String>();
	
	public RDFItem(String prodName, String unit, String packDate) {
		details.put("Product Name", prodName);
		details.put("Unit", unit);
		details.put("PackDate", packDate);
	}

	@Override
	public HashMap<String, String> getItemDetails() {
		return details;
	}
}
