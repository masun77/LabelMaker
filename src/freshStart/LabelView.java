/**
 * Interface to graphically display a label. 
 */

package freshStart;

import java.util.ArrayList;

import javax.swing.JPanel;

public interface LabelView {
	/**
	 * Display the label for the given item and format.
	 * @param i the item to display the label for
	 * @param lf the label format to display
	 */
	public void showLabel(Item i, LabelFormat lf);
	
	/**
	 * Create a list of containers, each of which contains the label
	 * for one instance of one item, given a list of items
	 * which each have quantities. Labels are formatted based on
	 * the given label format.
	 * @param items the items to create labels for
	 * @param lf the label format
	 * @return a list of containers containing all the labels
	 * 		for the given items and their quantities, in the given label format
	 */
	public ArrayList<JPanel> getLabelsForList(ArrayList<Item> items, LabelFormat lf);
	
	/**
	* Create a list of containers of length equal 
	* to the given item's quantity. Each container contains the graphical label
	 * for one instance of the given item, formatted based on
	 * the given label format.
	 * @param item the item to create labels for
	 * @param lf the label format
	 * @return a list of containers containing all the labels
	 * 		for the given item's quantity, in the given label format
	 */
	public ArrayList<JPanel> getLabelsForItem(Item i, LabelFormat lf);
	
	/**
	 * Returns a JPanel with a single graphical representation of
	 * the label of the given item in the given label format, regardless
	 * of the item's quantity.  
	 * @param i the item to create the label for
	 * @param lf the label format to use
	 * @return a JPan with the graphical label for that item
	 */
	public JPanel getSingleLabel(Item i, LabelFormat lf);
}
