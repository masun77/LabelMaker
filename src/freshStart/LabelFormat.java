package freshStart;

import java.awt.Color;
import java.util.ArrayList;

public class LabelFormat {
	private String name = "";
	Bounds labelDimensions = new Bounds();
	ArrayList<TextObject> textObjects = new ArrayList<>();
	ArrayList<RectangleObject> rectangles = new ArrayList<>();
	
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
		textObjects.add(new TextObject(strs.get(0), strs.get(1), 
				Integer.parseInt(strs.get(2)),Integer.parseInt(strs.get(3)),
				Integer.parseInt(strs.get(4)),Integer.parseInt(strs.get(5))));
	}
	
	private void addRectangleFromFile(String value) {
		ArrayList<String> strs = parseCommaSeparatedValues(value);
		rectangles.add(new RectangleObject(strs.get(0), 
				getColorFromString(strs.get(1)), 
				Boolean.parseBoolean(strs.get(2)),
				Integer.parseInt(strs.get(3)),Integer.parseInt(strs.get(4)),
				Integer.parseInt(strs.get(5)),Integer.parseInt(strs.get(6))));
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
