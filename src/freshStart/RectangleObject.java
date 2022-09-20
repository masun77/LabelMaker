package freshStart;

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
}
