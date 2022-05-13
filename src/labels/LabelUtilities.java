package labels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class LabelUtilities {
	private static int modWidth = 4;    
	private static int modHeight = 130;  
	private static final int[] START_CHAR = {2,1,1,2,3,2};
	private static final int[] FNC1 = {4,1,1,1,3,1};
	private static final int[] STOP_CHAR = {2,3,3,1,1,1,2};
	private static final int[] QUIET_ZONE_LENGTH = {0,10};
	private static final String AI_CODE = "01";
	private static final int MODS_TO_FIRST_CHAR = 32; // 10 quiet zone + 11 start char + 11 FNC1
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
	
	/**
	 * Create a GS1-128 language C GTIN bar code as a pattern of black and white bars
	 * in the given graphics context.
	 * @param g2 the graphics context
	 * @param startX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @param gtin the GTIN to represent in the bar code
	 * @param mw the module width for the bar code
	 * @param mh the module height for the bar code
	 * @param fontSize the font-size for the human-readable version under the bar code
	 */
	public static void createGS1_128GTINBarCode(Graphics2D g2, int startX, int startY, String gtin, int mw, int mh, int fontSize) {
		modWidth = mw;
		modHeight = mh;
		int currX = startX;
	    currX = appendQuietZone(g2, currX, startY);
	    currX = appendStartCharacters(g2, currX, startY);
	    currX = appendAI(g2, currX, startY);
	    currX = appendGTIN(g2, gtin, currX, startY);
	    currX = appendSymbolCheck(g2, gtin, currX, startY);
	    currX = appendStopCharacter(g2, currX, startY);
	    appendQuietZone(g2, currX, startY);
	    appendHumanReadableVersion(g2, startX, startY, gtin, fontSize);
	}
	/**
	 * Append a text version of the GTIN under the bar code in the given graphics context. 
	 * @param g2 the graphics context
	 * @param startX the starting x coordinate of the bar code
	 * @param startY the starting y coordinate of the bar code
	 * @param gtin the GTIN to represent in the text
	 * @param fontSize the size of the font to use
	 */
	private static void appendHumanReadableVersion(Graphics2D g2, int startX, int startY, String gtin, int fontSize) {
		String hrv = "(" + AI_CODE + ")" + gtin;
		
		Font font = new Font("SansSerif", Font.PLAIN, fontSize);
        g2.setFont(font);
        g2.setColor(Color.black);

        drawStringHelper(g2, font, hrv, startY + modHeight, startX +20);
	}
	
	/**
	 * Draw a string on this graphics object 
	 * @param g2 the graphics object
	 * @param font the font to use
	 * @param str the string to draw
	 * @param startY the top left y coordinate
	 * @param startX the top left x coordinate
	 */
	public static void drawStringHelper(Graphics2D g2, Font font, String str, int startY, int startX) {
		FontRenderContext frc = ((Graphics2D)g2).getFontRenderContext();
        Rectangle2D boundsTemp = font.getStringBounds(str, frc);  
		startY += (int) boundsTemp.getHeight();
        g2.drawString(str, startX, startY);
	}

	/**
	 * Append a quiet zone of white space as defined by GS1-128 bar code specifications. 
	 * @param g2 the graphics context
	 * @param currX the starting x coordinate
	 * @param startY the starting y coordinate
	 * @return the x-coordinate of the right edge of the last rectangle appended.
	 */
	private static int appendQuietZone(Graphics2D g2, int currX, int startY) {
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
	private static int appendAI(Graphics2D g2, int currX, int startY) {
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
	private static int appendStartCharacters(Graphics2D g2, int currX, int startY) {
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
	private static int appendGTIN(Graphics2D g2, String gtin, int currX, int startY) {
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
	private static int appendSymbolCheck(Graphics2D g2, String gtin, int currX, int startY) {
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

	private static int appendStopCharacter(Graphics2D g2, int currX, int startY) {
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
	private static int appendBlackWhite(Graphics2D g2, int currX, int startY, int[] lengths) {
	    Color[] colors = {Color.BLACK, Color.WHITE}; // white or transparent? whatever doesn't print
	    int currColor = 0;
	    currX += modWidth;
	    for (int mod = 0; mod < lengths.length; mod++) {
	        int numMods = lengths[mod];
	        for (int n = 0; n < numMods; n++) {	        	
	        	g2.setColor(colors[currColor]);
	        	g2.fill(new Rectangle(currX, startY, (int) modWidth, modHeight));
	            currX += modWidth;
	        }
	        if (currColor == 1) {
	            currColor = 0;
	        }
	        else {
	            currColor = 1;
	        }
	    }
	    return currX - modWidth;
	}

	/**
	 * Return black-white pattern as an int[] for the given number in GS1-128 bar code language C
	 * @param number the number to return the pattern for
	 * @return the pattern of black-white modules in GS1-128 Language C as an int[] for the given number
	 */
	private static int[] getCharacter(int number) {
	    return characterCodesSetC[number];
	}
	
	/**
	 * Add a voice pick code to this graphics object
	 * @param g2 the graphics object
	 * @param startX the top left x coordinate
	 * @param startY the top left y coordinate
	 * @param gtin the GTIN to use for calculating the voice pick code
	 * @param date the date to use for calculating the voice pick code
	 * @param smallFontSize the font size to use for the two small numbers
	 * @param largeFontSize the font size to use for the two large numbers
	 * @param fontStyle the font style to use
	 */
	public static void addVoicePickCode(Graphics2D g2, int startX, int startY, String gtin, Date date, int smallFontSize, int largeFontSize, int fontStyle) {
		String vpc = calculateVoicePickCode(gtin, date);
		String smallNums = vpc.substring(0,2);
		String bigNums = vpc.substring(2);
		
		g2.setColor(Color.BLACK);
		Font font = new Font("SansSerif", fontStyle, largeFontSize); 
		g2.setFont(font);
		
		FontRenderContext frc = ((Graphics2D)g2).getFontRenderContext();
        Rectangle2D boundsTemp = font.getStringBounds(vpc, frc);  
    	g2.fill(new Rectangle(startX, startY, (int) boundsTemp.getWidth(), (int) boundsTemp.getHeight()));
    	
    	g2.setColor(Color.WHITE);
    	drawStringHelper(g2, font, bigNums, startY - 5, startX + (int) boundsTemp.getWidth()/2);
    	
    	font = new Font("SanSerif", fontStyle, smallFontSize-2); 
    	g2.setFont(font);
    	drawStringHelper(g2, font, smallNums, startY + 3, startX + 2);
	}

	/**
	 * Calculate the Voice Pick code as defined by the specifications, by taking the last 4 digits of
	 * the CRC16 hash of the lot code (here simply the date) appended to the GTIN
	 * @param gtin the GTIN to use for calculation
	 * @param date the date to use for calculation
	 * @return the last 4 digits of the CRC16 hash of the gtin and date
	 */
	protected static String calculateVoicePickCode(String gtin, Date date) {
		String plainText = gtin + date.getDateYYMMDD();
		int result = CRC16Imp.crc16(plainText);
		String res = Integer.toString(result);
		return res.substring(res.length() -4);
	}
}
