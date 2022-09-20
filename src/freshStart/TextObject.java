package freshStart;

public class TextObject {
	private String name = "";
	private String text = "";
	private Bounds bounds = new Bounds();
	private LabelFieldOption fieldType;
	
	public TextObject(String n, String t, LabelFieldOption lf, int xmin, int ymin, int xmax, int ymax) {
		name = n;
		text = t;
		bounds = new Bounds(xmin, ymin, xmax, ymax);
		fieldType = lf;
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
	
	
}
