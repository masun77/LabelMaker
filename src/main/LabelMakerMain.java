package main;

import userInterface.RDFInterface;
import userInterface.UserInterface;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {	
		UserInterface ui = new RDFInterface();
		ui.showInterface();
	}
}
