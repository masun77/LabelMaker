package labels;

public class RectangleBounds {
	private int startX, startY, endX, endY;

	public RectangleBounds(int sx, int sy, int ex, int ey) {
		startX = sx;
		startY = sy;
		endX = ex;
		endY = ey;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}
	
	public int getWidth() {
		return endX - startX;
	}
	
	public int getHeight() {
		return endY - startY;
	}
}
