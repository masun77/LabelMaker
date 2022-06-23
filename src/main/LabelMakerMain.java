package main;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import userInterface.RDFInterface;
import userInterface.UserInterface;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {	
		UserInterface ui = new RDFInterface();
		ui.showInterface();
		
//		PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
//		
//		for (int p = 0; p < ps.length; p++) {
//			PrintService currP = ps[p];
//			System.out.println("\n" + currP.getName());
//			DocFlavor[] supportedFlavors = currP.getSupportedDocFlavors();
//
//			for (int i = 0; i < supportedFlavors.length; i++) {
//				DocFlavor f = supportedFlavors[i];
//				System.out.println(f.getMimeType());
//			}
//		}
		
	}
}
