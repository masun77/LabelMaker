/**
 * TextObject
 * Represents text on a label with text, color, 
 * bounds within which the text falls, a name, and a type
 * for use with Items.
 */

package labels;

import java.awt.Color;

public class TextObject {
	private String name = "";
	private String text = "";
	private Color color = Color.black;
	private Bounds bounds = new Bounds();
	private LabelFieldOption fieldType;
	
	public TextObject(Color c, String n, String t, 
			LabelFieldOption lf, int xmin, int ymin, int xmax, int ymax) {
		name = n;
		text = t;
		bounds = new Bounds(xmin, ymin, xmax, ymax);
		fieldType = lf;
		color = c;
	}

	public LabelFieldOption getFieldType() {
		return fieldType;
	}

	public void setFieldType(LabelFieldOption fieldType) {
		this.fieldType = fieldType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
