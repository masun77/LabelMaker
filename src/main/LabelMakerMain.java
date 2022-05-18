package main;


import display.LabelViewerImp;
import display.Labelable;
import labels.DateImp;

import java.util.ArrayList;

import display.LabelView;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {
		// Display orders
		
		// Enter orders
		
		// Select labels to print
		
		// Display labels
		ArrayList<Labelable> items = new ArrayList<Labelable>();
		items.add(new RDFItem("Long company name", "Arugula", "3 lbs", "00000123456789", new DateImp(4,2,2022)));
		items.add(new RDFItem());
		items.add(new RDFItem("MAya", "Cucumbers", "10 lb case", "9876654", new DateImp(5,1,2022)));
		items.add(new RDFItem("Marshall", "Kale", "12 bunches", "474747", new DateImp(4,20,2022)));
		LabelView lv = new LabelViewerImp();
		lv.showLabels(items);
				
		// Print labels
		
		// Export to QB
		
		// other manipulation of existing orders?
		// multi-user
	}

}
