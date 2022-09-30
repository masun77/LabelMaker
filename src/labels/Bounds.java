/**
 * Bounds
 * Represents the boundaries of a rectangle on the display. 
 */
package labels;

public class Bounds {
	private int xMin = 0;
	private int xMax = 100;
	private int yMin = 0;
	private int yMax = 100;
	
	public Bounds() {}
	
	public Bounds(int xmin, int ymin, int xmax, int ymax) {
		xMin = xmin;
		yMin = ymin;
		xMax = xmax;
		yMax = ymax;
	}
	
	public int getxMin() {
		return xMin;
	}
	public void setxMin(int xMin) {
		this.xMin = xMin;
	}
	public int getxMax() {
		return xMax;
	}
	public void setxMax(int xMax) {
		this.xMax = xMax;
	}
	public int getyMin() {
		return yMin;
	}
	public void setyMin(int yMin) {
		this.yMin = yMin;
	}
	public int getyMax() {
		return yMax;
	}
	public void setyMax(int yMax) {
		this.yMax = yMax;
	}
	
	public int getWidth() {
		return xMax - xMin;
	}
	
	public int getHeight() {
		return yMax - yMin;
	}
}
