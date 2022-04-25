package main;

import java.util.ArrayList;

public interface GraphicsRenderer {
	/**
	 * Render label for printing with the bar code, name, unit, 
	 * farm info, pack date, and voice pick code of the given items. 
	 * @param items the list of items to render a label for
	 */
	public void renderLabels(ArrayList<Item> items);   
}
