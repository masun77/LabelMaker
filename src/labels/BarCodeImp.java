/**
 * BarCodeImp
 * Implements BarCodeGenerator. 
 */

package labels;

import java.util.ArrayList;

public class BarCodeImp implements BarCodeGenerator {
	
	@Override
	public ArrayList<Integer> getBarCode(String gtin) {
		ArrayList<Integer> barCode = new ArrayList<Integer>();
		appendQuietZone(barCode);
		appendStartCharacters(barCode);
		appendAI(barCode);
		appendGTIN(barCode, gtin);
		appendSymbolCheck(barCode, gtin);
		appendStopCharacter(barCode);
		appendQuietZone(barCode);
		
		return barCode;
	}
	
	/**
	 * Append a quiet zone of 10 white modules, as defined by GS1-128 bar code specifications. 
	 * @param barCode the existing bar code representation as an ArrayList
	 */
	private void appendQuietZone(ArrayList<Integer> barCode) {
	    appendBlackWhite(barCode, QUIET_ZONE_LENGTH); 
	}
	
	/**
	 * Append to the given arrayList the GS1-128 GTIN start characters in language C - start character and FNC1 - 
	 * as a pattern of integers representing the number of black and white rectangles 
	 * @param g2 barCode the arrayList to append to
	 */
	private void appendStartCharacters(ArrayList<Integer> barCode) {
	    appendBlackWhite(barCode, START_CHAR);
	    appendBlackWhite(barCode, FNC1);
	}
	
	/**
	 * Append to the given arrayList the application indicator of a GTIN
	 * in GS1-128 language C. 
	 * @param barCode the arrayList to append to
	 */
	private void appendAI(ArrayList<Integer> barCode) {
	    appendBlackWhite(barCode, getCharacter(Integer.parseInt(AI_CODE)));
	}
	
	/**
	 * Append a GTIN as a pattern of black and white rectangles defined by GS1-128 to the given arrayList
	 * @param gtin the gtin to append the bar code pattern for
	 * @param barCode the arrayLIst to append to
	 */
	private void appendGTIN(ArrayList<Integer> barCode, String gtin) {
	    for (int leftDigit = 0; leftDigit < gtin.length() - 1; leftDigit += 2) {
	        appendBlackWhite(barCode,
	        		getCharacter(Integer.parseInt(gtin.substring(leftDigit, leftDigit + 2))));
	    }
	}
	
	/**
	 * Append the GS1-128 symbol check character to the given arrayList as a pattern of black and white rectangles.
	 * Calculation as described in the GS1-128 specifications. 
	 * @param barCode the arrayList to append the symbol check pattern to 
	 * @param gtin the GTIN to calculate the symbol check character for
	 */
	private void appendSymbolCheck(ArrayList<Integer> barCode, String gtin) {
	    int startCValue = 105;
	    int FNC1Value = 102;
	    int AI01Value = 1;
	    int sumTotal = startCValue + FNC1Value + AI01Value * 2;
	    int weight = 3;
	    for (int leftDigit = 0; leftDigit < gtin.length() - 1; leftDigit += 2) {
	        int currValue = Integer.parseInt(gtin.substring(leftDigit, leftDigit + 2));
	        sumTotal += currValue * weight;
	        weight += 1;
	    }
	    sumTotal = sumTotal % 103;
	    appendBlackWhite(barCode, getCharacter(sumTotal));
	}

	private void appendStopCharacter(ArrayList<Integer> barCode) {
	    appendBlackWhite(barCode, STOP_CHAR);
	}
	
	/**
	 * Append a pattern of black and white rectangles to the given ArrayList
	 * @param barCode the ArrayList to append the pattern to
	 * @param lengths an int array representing the number of modules in each black and white segment, 
	 * 		starting with black. for example [1,2,3,4] would mean 1 black module, 2 white modules, 3 black, 4 white.
	 * @return the ending x coordinate of the last module in the pattern
	 */
	private void appendBlackWhite(ArrayList<Integer> barCode, int[] lengths) {
	   for (int section = 0; section < lengths.length; section++) {
	        barCode.add(lengths[section]);
	    }
	}
	
	/**
	 * Return black-white pattern as an int[] for the given number in GS1-128 bar code language C
	 * @param number the number to return the pattern for
	 * @return the pattern of black-white modules in GS1-128 Language C as an int[] for the given number
	 */
	private static int[] getCharacter(int number) {
	    return characterCodesSetC[number];
	}

	private static final int[] START_CHAR = {2,1,1,2,3,2};
	private static final int[] FNC1 = {4,1,1,1,3,1};
	private static final int[] STOP_CHAR = {2,3,3,1,1,1,2};
	private static final int[] QUIET_ZONE_LENGTH = {10};
	private static final String AI_CODE = "01";
	private static final int[][] characterCodesSetC = {
	        {2,1,2,2,2,2}, //0
	        {2,2,2,1,2,2},
	        {2,2,2,2,2,1},
	        {1,2,1,2,2,3},
	        {1,2,1,3,2,2},
	        {1,3,1,2,2,2},
	        {1,2,2,2,1,3},
	        {1,2,2,3,1,2},
	        {1,3,2,2,1,2},
	        {2,2,1,2,1,3}, // 09
	        {2,2,1,3,1,2}, 
	        {2,3,1,2,1,2},
	        {1,1,2,2,3,2},
	        {1,2,2,1,3,2},
	        {1,1,3,2,2,2},
	        {1,2,3,1,2,2},
	        {1,2,3,1,2,2},
	        {1,2,3,2,2,1},
	        {2,2,3,2,1,1},
	        {2,2,1,1,3,2}, // 19
	        {2,2,1,2,3,1},
	        {2,1,3,2,1,2},
	        {2,2,3,1,1,2},
	        {3,1,2,1,3,1},
	        {3,1,1,2,2,2},
	        {3,2,1,1,2,2},
	        {3,2,1,2,2,1},
	        {3,1,2,2,1,2},
	        {3,2,2,1,1,2},
	        {3,2,2,2,1,1}, //29
	        {2,1,2,1,2,3},
	        {2,1,2,3,2,1},
	        {2,3,2,1,2,1},
	        {1,1,1,3,2,3},
	        {1,3,1,1,2,3},
	        {1,3,1,3,2,1},
	        {1,1,2,3,1,3},
	        {1,3,2,1,1,3},
	        {1,3,2,3,1,1},
	        {2,1,1,3,1,3},//39
	        {2,3,1,1,1,3},
	        {2,3,1,3,1,1},
	        {1,1,2,1,3,3},
	        {1,1,2,3,3,1},
	        {1,3,2,1,3,1},
	        {1,1,3,1,2,3},
	        {1,1,3,3,2,1},
	        {1,3,3,1,2,1},
	        {3,1,3,1,2,1},
	        {2,1,1,3,3,1},//49
	        {2,3,1,1,3,1},
	        {2,1,3,1,1,3},
	        {2,1,3,3,1,1},
	        {2,1,3,1,3,1},
	        {3,1,1,1,2,3},
	        {3,1,1,3,2,1},
	        {3,3,1,1,2,1},
	        {3,1,2,1,1,3},
	        {3,1,2,3,1,1},
	        {3,3,2,1,1,1},//59
	        {3,1,4,1,1,1},
	        {2,2,1,4,1,1},
	        {4,3,1,1,1,1},
	        {1,1,1,2,2,4},
	        {1,1,1,4,2,2},
	        {1,2,1,1,2,4},
	        {1,2,1,4,2,1},
	        {1,4,1,1,2,2},
	        {1,4,1,2,2,1},
	        {1,1,2,2,1,4}, //69
	        {1,1,2,4,1,2},
	        {1,2,2,1,1,4},
	        {1,2,2,4,1,1},
	        {1,4,2,1,1,2},
	        {1,4,2,2,1,1},
	        {2,4,1,2,1,1},
	        {2,2,1,1,1,4},
	        {4,1,3,1,1,1},
	        {2,4,1,1,1,2},
	        {1,3,4,1,1,1},//79
	        {1,1,1,2,4,2},
	        {1,2,1,1,4,2},
	        {1,2,1,2,4,1},
	        {1,1,4,2,1,2},
	        {1,2,4,1,1,2},
	        {1,2,4,2,1,1},
	        {4,1,1,2,1,2},
	        {4,2,1,1,1,2},
	        {4,2,1,2,1,1},
	        {2,1,2,1,4,1},//89
	        {2,1,4,1,2,1},
	        {4,1,2,1,2,1},
	        {1,1,1,1,4,3},
	        {1,1,1,3,4,1},
	        {1,3,1,1,4,1},
	        {1,1,4,1,1,3},
	        {1,1,4,3,1,1},
	        {4,1,1,1,1,3},
	        {4,1,1,3,1,1},
	        {1,1,3,1,4,1},//99
	        {1,1,4,1,3,1},
	        {3,1,1,1,4,1},
	        {4,1,1,1,3,1} // up to 102 for symbol check character	        
	    };
}
