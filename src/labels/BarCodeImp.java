package labels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class BarCodeImp implements BarCodeGenerator {
	
	@Override
	public ArrayList<Integer> getBarCode(String gtin) {
		ArrayList<Integer> barCode = new ArrayList<Integer>();
		appendQuietZone(barCode);
		appendStartCharacters(barCode);
		appendAI(barCode);
		appendGTIN(barCode);
		appendSymbolCheck(barCode);
		appendStopCharacter(barCode);
		appendQuietZone(barCode);
		
		return new ArrayList<Integer>(Arrays.asList(10,0,10));
	}
	
	/**
	 * Append a quiet zone of 10 white modules, as defined by GS1-128 bar code specifications. 
	 * @param barCode the existing bar code representation as an ArrayList
	 * @return the arrayLIst with the quiet zone appended
	 */
	private void appendQuietZone(ArrayList<Integer> barCode) {
	    appendBlackWhite(barCode, QUIET_ZONE_LENGTH); 
	}
	
	/**
	 * Append to the given arrayList the GS1-128 GTIN start characters in language C - start character and FNC1 - 
	 * as a pattern of integers representing the number of black and white rectangles 
	 * @param g2 the graphics context
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @return the x-coordinate of the right edge of the last rectangle appended.
	 */
	private void appendStartCharacters(ArrayList<Integer> barCode) {
	    appendBlackWhite(barCode, START_CHAR);
	    appendBlackWhite(barCode, FNC1);
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
	

	private static final int[] START_CHAR = {2,1,1,2,3,2};
	private static final int[] FNC1 = {4,1,1,1,3,1};
	private static final int[] STOP_CHAR = {2,3,3,1,1,1,2};
	private static final int[] QUIET_ZONE_LENGTH = {10};
	private static final String AI_CODE = "01";
	private static final int[][] characterCodesSetC = {
	        {2,1,2,2,2,2},
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
	    };
}
