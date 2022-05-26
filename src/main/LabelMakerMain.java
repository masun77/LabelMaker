package main;


import java.util.ArrayList;

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
		 
		// Display existing orders
//		UserInterface ui = new RDFInterface();
				
		// Print labels
		ArrayList<Item> items = new ArrayList<Item>();
		LabelView lv = new LabelViewerImp();
		items.add(new RDFItem("Maya", "Kale", "10 lb case", "474747", new DateImp(5,1,2022), 1, 5f));
		//lv.showLabels(items);
		PrintManager pm = new PrintManager();
		pm.printLabels(items);
	}
}
