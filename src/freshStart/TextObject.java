package freshStart;

public class TextObject {
	private String name = "";
	private String text = "";
	private Bounds bounds = new Bounds();
	
	public TextObject(String n, String t, int xmin, int ymin, int xmax, int ymax) {
		name = n;
		text = t;
		bounds = new Bounds(xmin, ymin, xmax, ymax);
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
	
	
}
