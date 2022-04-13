package main;

import java.util.ArrayList;

public class OrderFactory {
	public Order makeOrder(String stringType, ArrayList<String> headers, ArrayList<String> info) {
		if (stringType.equals("RDFOrder")) {
			return new RDFOrder(headers, info);
		}
		return null;		
	}
}
