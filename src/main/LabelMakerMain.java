package main;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import uiDisplay.RDFInterface;
import uiLogic.UserInterface;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {	
		Application app = new Application();
		app.run();
	}
}
