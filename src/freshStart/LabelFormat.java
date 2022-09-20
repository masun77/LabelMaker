package freshStart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import static freshStart.LabelFieldOption.*;

public class LabelFormat {
	private String name = "";
	private Bounds labelDimensions = new Bounds();
	private ArrayList<TextObject> textObjects = new ArrayList<>();
	private ArrayList<RectangleObject> rectangles = new ArrayList<>();
	private ArrayList<String> objectNames = new ArrayList<>();
	private HashMap<String, LabelFieldOption> fieldTypes = new HashMap<>();
	private ArrayList<LabelFieldOption> fieldTypesUsed = new ArrayList<>();
	
	private ArrayList<String> settings = new ArrayList<>();
	
	public LabelFormat() {
		initializeSettings();
	}
	
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
				setLabelDimensionsFromFile(settingValue);
				break;
			case 2:
				addTextFromFile(settingValue);
				break;
			case 3:
				addRectangleFromFile(settingValue);
				break;
			default:
				break;
		}
	}
	
	private void setLabelDimensionsFromFile(String value) {
		ArrayList<String> strs = parseCommaSeparatedValues(value);
		labelDimensions = new Bounds(Integer.parseInt(strs.get(0)),Integer.parseInt(strs.get(1)),
				Integer.parseInt(strs.get(2)),Integer.parseInt(strs.get(3)));
	}
	
	private void addTextFromFile(String value) {
		ArrayList<String> strs = parseCommaSeparatedValues(value);
		LabelFieldOption lf = fieldTypes.get(strs.get(0).toLowerCase());
		fieldTypesUsed.add(lf);
		textObjects.add(new TextObject(strs.get(0), strs.get(1),
				lf,
				Integer.parseInt(strs.get(2)),Integer.parseInt(strs.get(3)),
				Integer.parseInt(strs.get(4)),Integer.parseInt(strs.get(5))));
		objectNames.add(strs.get(0));
	}
	
	private void addRectangleFromFile(String value) {
		ArrayList<String> strs = parseCommaSeparatedValues(value);
		rectangles.add(new RectangleObject(strs.get(0), 
				getColorFromString(strs.get(1)), 
				Boolean.parseBoolean(strs.get(2)),
				Integer.parseInt(strs.get(3)),Integer.parseInt(strs.get(4)),
				Integer.parseInt(strs.get(5)),Integer.parseInt(strs.get(6))));
		objectNames.add(strs.get(0));
	}
	
	private Color getColorFromString(String s) {
		if (s.toLowerCase().strip().equals("black")) {
			return Color.black;
		}
		return Color.red;
	}
	
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
}
