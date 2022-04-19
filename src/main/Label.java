package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Label extends Component {
	private Order order;

	private final int MOD_WIDTH = 4;    // todo: dimensions
	private final int MOD_HEIGHT = 130;  
	private final int[] START_CHAR = {2,1,1,2,3,2};
	private final int[] FNC1 = {4,1,1,1,3,1};
	private final int[] STOP_CHAR = {2,3,3,1,1,1,2};

	public Label(Order o) {	
		order = o;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// todo: working on making label using graphics library
		createBarCode(g2, 10,10, "42434445464748");
	}
	
	private void createBarCode(Graphics2D g2, int currX, int startY, String gtin) {
	    currX = appendQuietZone(g2, currX, startY);
	    currX = appendStartCharacters(g2, currX, startY);
	    currX = appendAI(g2, currX, startY);
	    currX = appendGTIN(g2, gtin, currX, startY);
	    currX = appendSymbolCheck(g2, gtin, currX, startY);
	    currX = appendStopCharacter(g2, currX, startY);
	    appendQuietZone(g2, currX, startY);
	}

	private int appendQuietZone(Graphics2D g2, int currX, int startY) {
		int[] length = {0,10};
	    return appendBlackWhite(g2, currX, startY, length); 
	}

	private int appendAI(Graphics2D g2, int currX, int startY) {
	    return appendBlackWhite(g2, currX, startY,getCharacter(1));
	}

	private int appendStartCharacters(Graphics2D g2, int currX, int startY) {
	    currX = appendBlackWhite(g2, currX, startY, START_CHAR);
	    currX = appendBlackWhite(g2, currX, startY, FNC1);
	    return currX;
	}

	private int appendGTIN(Graphics2D g2, String gtin, int currX, int startY) {
	    for (int leftDigit = 0; leftDigit < gtin.length() - 1; leftDigit += 2) {
	        currX = appendBlackWhite(g2, currX, startY, getCharacter(Integer.parseInt(gtin.substring(leftDigit, leftDigit + 2))));
	    }
	    return currX;
	}

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

	private int appendBlackWhite(Graphics2D g2, int currX, int startY, int[] lengths) {
	    Color[] colors = {Color.BLACK, Color.WHITE}; // todo change to white/transparent
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
