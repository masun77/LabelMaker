package printing;

import java.util.ArrayList;

public interface GraphicsRenderAndPrinter {
	/**
	 * Render label for printing with the bar code, name, unit, 
	 * farm info, pack date, and voice pick code of the given items. 
	 * @param items the list of items to render a label for
	 */
	public void renderLabels(ArrayList<Item> items);   
	
	/**
	 * Open the print dialog for the selected items and print them if desired. 
	 */
	public void printLabels();
}
