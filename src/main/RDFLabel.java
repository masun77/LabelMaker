package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RDFLabel extends Component {
	private Item item;
	private final int MOD_WIDTH = 4;    // todo: dimensions
	private final int MOD_HEIGHT = 130;  
	private final int[] START_CHAR = {2,1,1,2,3,2};
	private final int[] FNC1 = {4,1,1,1,3,1};
	private final int[] STOP_CHAR = {2,3,3,1,1,1,2};
	private final int[] QUIET_ZONE_LENGTH = {0,10};
	private final String AI_CODE = "01";
	private final int MODS_TO_FIRST_CHAR = 32; // 10 quiet zone + 11 start char + 11 FNC1

	public RDFLabel(Item o) {
		item = o;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		createLabel(g2);
	}
	
	/**
	 * Create
	 * @param g2
	 */
	private void createLabel(Graphics2D g2) {
		createGS1_128GTINBarCode(g2, 10,10, item.getGtin());

		// todo: working on making label using graphics library
	}
	
	// may want to move this stuff to a labelUtilities class or so
	/**
	 * Create a GS1-128 language C GTIN bar code as a pattern of black and white bars
	 * in the given graphics context.
	 * @param g2 the graphics context
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @param gtin the GTIN to represent in the bar code
	 */
	private void createGS1_128GTINBarCode(Graphics2D g2, int startX, int startY, String gtin) {
		int currX = startX;
	    currX = appendQuietZone(g2, currX, startY);
	    currX = appendStartCharacters(g2, currX, startY);
	    currX = appendAI(g2, currX, startY);
	    currX = appendGTIN(g2, gtin, currX, startY);
	    currX = appendSymbolCheck(g2, gtin, currX, startY);
	    currX = appendStopCharacter(g2, currX, startY);
	    appendQuietZone(g2, currX, startY);
	    appendHumanReadableVersion(g2, startX, startY, gtin);
	}
	
	private void appendHumanReadableVersion(Graphics2D g2, int startX, int startY, String gtin) {
		int currY = startY + MOD_HEIGHT + 2;
		int currX = startX + MODS_TO_FIRST_CHAR;
		String hrv = "(" + AI_CODE + ")" + gtin;
		
	}

	/**
	 * Append a quiet zone of white space as defined by GS1-128 bar code specifications. 
	 * @param g2 the graphics context
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @return the x-coordinate of the right edge of the last rectangle appended.
	 */
	private int appendQuietZone(Graphics2D g2, int currX, int startY) {
	    return appendBlackWhite(g2, currX, startY, QUIET_ZONE_LENGTH); 
	}

	/**
	 * Append to the given graphics context the application indicator of a GTIN
	 * in GS1-128 language C as a pattern of black and white rectangles. 
	 * @param g2 the graphics context
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @return the x-coordinate of the right edge of the last rectangle appended.
	 */
	private int appendAI(Graphics2D g2, int currX, int startY) {
	    return appendBlackWhite(g2, currX, startY, getCharacter(Integer.parseInt(AI_CODE)));
	}

	/**
	 * Append to the given graphics context the GS1-128 GTIN start characters in language C - start character and FNC1 - 
	 * as a pattern of black and white rectangles 
	 * @param g2 the graphics context
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @return the x-coordinate of the right edge of the last rectangle appended.
	 */
	private int appendStartCharacters(Graphics2D g2, int currX, int startY) {
	    currX = appendBlackWhite(g2, currX, startY, START_CHAR);
	    currX = appendBlackWhite(g2, currX, startY, FNC1);
	    return currX;
	}

	/**
	 * Append a GTIN as a pattern of black and white rectangles defined by GS1-128 to the given graphics context
	 * @param g2 the graphics context
	 * @param gtin the number to put into a bar code
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @return the x-coordinate of the right edge of the last rectangle appended.
	 */
	private int appendGTIN(Graphics2D g2, String gtin, int currX, int startY) {
	    for (int leftDigit = 0; leftDigit < gtin.length() - 1; leftDigit += 2) {
	        currX = appendBlackWhite(g2, currX, startY, 
	        		getCharacter(Integer.parseInt(gtin.substring(leftDigit, leftDigit + 2))));
	    }
	    return currX;
	}

	/**
	 * Append the GS1-128 symbol check character to the given graphics context as a pattern of black and white rectangles.
	 * Calculation as described in the GS1-128 specifications. 
	 * @param g2 the graphics context
	 * @param gtin the GTIN to calculate the symbol check character for
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @return the x-coordinate of the right edge of the last rectangle appended. 
	 */
	private int appendSymbolCheck(Graphics2D g2, String gtin, int currX, int startY) {
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
	    return appendBlackWhite(g2, currX, startY, getCharacter(sumTotal));
	}

	private int appendStopCharacter(Graphics2D g2, int currX, int startY) {
	    return appendBlackWhite(g2, currX, startY, STOP_CHAR);
	}

	/**
	 * Append a pattern of black and white rectangles to the given graphics context.
	 * @param g2 the graphics context
	 * @param currX the x coordinate to start at and move right
	 * @param startY the y coordinate to start at
	 * @param lengths an int array representing the number of modules in each black and white segment, 
	 * 		starting with black. for example [1,2,3,4] would mean 1 black module, 2 white modules, 3 black, 4 white.
	 * @return the ending x coordinate of the last module in the pattern
	 */
	private int appendBlackWhite(Graphics2D g2, int currX, int startY, int[] lengths) {
	    Color[] colors = {Color.BLACK, Color.WHITE}; // white or transparent? whatever doesn't print
	    int currColor = 0;
	    currX += MOD_WIDTH;
	    for (int mod = 0; mod < lengths.length; mod++) {
	        int numMods = lengths[mod];
	        for (int n = 0; n < numMods; n++) {	        	
	        	g2.setColor(colors[currColor]);
	        	g2.fill(new Rectangle(currX, startY, (int) MOD_WIDTH, MOD_HEIGHT));
	            currX += MOD_WIDTH;
	        }
	        if (currColor == 1) {
	            currColor = 0;
	        }
	        else {
	            currColor = 1;
	        }
	    }
	    return currX - MOD_WIDTH;
	}

	/**
	 * Return black-white pattern as an int[] for the given number in GS1-128 bar code language C
	 * @param number the number to return the pattern for
	 * @return the pattern of black-white modules in GS1-128 Language C as an int[] for the given number
	 */
	private int[] getCharacter(int number) {
	    int[][] characterCodesSetC = {
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
	    return characterCodesSetC[number];
	}
}
