package display;

import java.util.ArrayList;

import main.Item;

public interface LabelView {
	/**
	 * Display the labels for the given list of items. 
	 * @param items the items to show the labels for
	 */
	public void showLabels(ArrayList<Item> items);
}
