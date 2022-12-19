/**
 * RectangleObject
 * Represents a rectangle on a label with color, filled or not, 
 * bounds and a name.
 */

package labels;

import java.awt.Color;

public class RectangleObject {
	private String name;
	private Bounds bounds;
	private Color color;
	private boolean filled = false;
	
	public RectangleObject(String n, Color c, boolean f, int xmin, int ymin, int xmax, int ymax) {
		name = n;
		bounds = new Bounds(xmin, ymin, xmax, ymax);
		color = c;
		filled = f;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	
}
