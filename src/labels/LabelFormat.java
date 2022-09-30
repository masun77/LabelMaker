/**
 * LabelFormat
 * Represents the name, size, text, and rectangles for a specific
 * label format. 
 */

package labels;

import static labels.LabelFieldOption.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class LabelFormat {
	private String name = "";
	private Bounds labelDimensions = new Bounds();
	private ArrayList<TextObject> textObjects = new ArrayList<>();
	private ArrayList<RectangleObject> rectangles = new ArrayList<>();
	private HashMap<String, LabelFieldOption> fieldTypes = new HashMap<>();
	private HashMap<LabelFieldOption, String> fieldTypesUsed = new HashMap<>();
	
	private ArrayList<String> settings = new ArrayList<>();
	
	public LabelFormat() {
		initializeSettings();
	}
	
	/**
	 * Given one line from a label format file, add it to this object
	 * as a property - whether name, dimensions, text object, or rectangle.
	 * Each line has the following format:
	 * 		propertyName: characteristic1, characteristic2...
	 * @param line the text to parse into a label property
	 */
	public void addSetting(String line) {
		int colon = line.indexOf(":");
		if (colon < 0) {
			return;
		}
		String settingName = line.substring(0,colon).strip().toLowerCase();
		String settingValue = line.substring(colon +1).strip();
		int settingNum = settings.indexOf(settingName);
		
		switch (settingNum) {
			case 0:
				name = settingValue;
				break;
			case 1:
				setLabelDimensionsFromString(settingValue);
				break;
			case 2:
				addTextFromString(settingValue);
				break;
			case 3:
				addRectangleFromString(settingValue);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Set the labels dimensions given a string with its xmin, ymin, xmax,
	 * and ymax as comma-separated values
	 * @param value the string containing the label boundaries
	 */
	private void setLabelDimensionsFromString(String value) {
		ArrayList<String> strs = parseCommaSeparatedValues(value);
		labelDimensions = new Bounds(Integer.parseInt(strs.get(0)),Integer.parseInt(strs.get(1)),
				Integer.parseInt(strs.get(2)),Integer.parseInt(strs.get(3)));
	}
	
	/**
	 * Add text to the label format given a string containing the text's
	 * color, name, value, and xmin, ymin, xmax,
	 * and ymax as comma-separated values
	 * @param value the string containing the text specifications
	 */
	private void addTextFromString(String value) {
		ArrayList<String> strs = parseCommaSeparatedValues(value);
		LabelFieldOption lf = fieldTypes.get(strs.get(1).toLowerCase());
		fieldTypesUsed.put(lf, strs.get(2));
		textObjects.add(new TextObject(getColorFromString(strs.get(0)), strs.get(1),
				strs.get(2), lf,
				Integer.parseInt(strs.get(3)),Integer.parseInt(strs.get(4)),
				Integer.parseInt(strs.get(5)),Integer.parseInt(strs.get(6))));
	}
	
	/**
	 * Add a rectangle to the label format given a string containing the rectangle's
	 * name, color, whether it's filled or not, and xmin, ymin, xmax,
	 * and ymax as comma-separated values
	 * @param value the string containing the rectangle specifications
	 */
	private void addRectangleFromString(String value) {
		ArrayList<String> strs = parseCommaSeparatedValues(value);
		rectangles.add(new RectangleObject(strs.get(0), 
				getColorFromString(strs.get(1)), 
				Boolean.parseBoolean(strs.get(2)),
				Integer.parseInt(strs.get(3)),Integer.parseInt(strs.get(4)),
				Integer.parseInt(strs.get(5)),Integer.parseInt(strs.get(6))));
	}
	
	/**
	 * Convert a string like "black" to a Color object
	 * @param s the string to convert
	 * @return the corresponding Color
	 */
	private Color getColorFromString(String s) {
		s = s.toLowerCase().strip();
		if (s.equals("black")) {
			return Color.black;
		}
		else if (s.equals("white")) {
			return Color.white;
		}
		return Color.red;
	}
	
	/**
	 * Parse a string of values separated by commas into an array
	 * of strings with each value as one item in the arry
	 * @param line the String to parse
	 * @return an array of strings with each comma-separated item as one String
	 */
	private ArrayList<String> parseCommaSeparatedValues(String line) {
		ArrayList<String> strs = new ArrayList<>();
		int commaIndex = line.indexOf(",");
		while(commaIndex >= 0) {
			strs.add(line.substring(0,commaIndex).strip());
			line = line.substring(commaIndex + 1);
			commaIndex = line.indexOf(",");
		}
		strs.add(line.strip());
		
		return strs;
	}
	
	/**
	 * Add available settings to the settings list.
	 */
	private void initializeSettings() {
		settings.add("name");    // 0
		settings.add("label dimensions");    // 1
		settings.add("text");    // 2
		settings.add("rectangle");    // 3
		
		fieldTypes.put("company", COMPANY);
		fieldTypes.put("humanreadablegtin", HUMAN_READABLE_GTIN);
		fieldTypes.put("productname", PRODUCT_NAME);
		fieldTypes.put("unit", UNIT);
		fieldTypes.put("datelabel", DATE_LABEL);
		fieldTypes.put("shipdate", SHIP_DATE);
		fieldTypes.put("vpclarge", VPC_LARGE);
		fieldTypes.put("vpcsmall", VPC_SMALL);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bounds getLabelDimensions() {
		return labelDimensions;
	}
	public void setLabelDimensions(Bounds labelDimensions) {
		this.labelDimensions = labelDimensions;
	}
	public ArrayList<TextObject> getTextObjects() {
		return textObjects;
	}
	public void setTextObjects(ArrayList<TextObject> textObjects) {
		this.textObjects = textObjects;
	}
	public ArrayList<RectangleObject> getRectangles() {
		return rectangles;
	}
	public void setRectangles(ArrayList<RectangleObject> rectangles) {
		this.rectangles = rectangles;
	}

	public HashMap<String, LabelFieldOption> getFieldTypes() {
		return fieldTypes;
	}

	public HashMap<LabelFieldOption, String> getFieldTypesUsed() {
		return fieldTypesUsed;
	}

	public ArrayList<String> getSettings() {
		return settings;
	}
}
