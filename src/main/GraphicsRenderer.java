package main;

public interface GraphicsRenderer {
	/**
	 * Render a label for printing with the bar code, name, unit, 
	 * farm info, pack date, and voice pick code. 
	 * @param it the item to render a label for
	 */
	public void renderLabel(Item it);   
}
