package main;


import labels.DateImp;

import labels.LabelView;
import labels.LabelViewerImp;
import labels.Labelable;
import printing.PrintManager;
import userInterface.RDFInterface;
import userInterface.UserInterface;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {		
		// Enter orders
		 
		// Display existing orders
		UserInterface ui = new RDFInterface();
		
		// Select labels to print
		
		// Display labels
//		LabelView lv = new LabelViewerImp();
//		lv.showLabels(getLabelableArrayList(new RDFItem()));
				
		// Print labels
//		ArrayList<Item> items = new ArrayList<Item>();
//		items.add(new RDFItem("Maya", "Kale", "10 lb case", "474747", new DateImp(5,1,2022), 1));
//		PrintManager pm = new PrintManager();
//		pm.printLabels(items);
		
		// Export to QB
		
		// other manipulation of existing orders?
		// multi-user
	}
}
