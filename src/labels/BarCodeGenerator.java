package labels;

import java.util.ArrayList;

public interface BarCodeGenerator {
	/**
	 * Calculate the white-black pattern for a GS-158 bar code for a given gtin 
	 * and return it as an array list in the form
	 * [# white modules, # black modules, # white modules, # black modules...]
	 * @param gtin the gtin to get the barcode for
	 * @return an arrayList representing the black-white pattern in the bar code
	 */
	public ArrayList<Integer> getBarCode(String gtin);
}
