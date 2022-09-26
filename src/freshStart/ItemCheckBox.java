package freshStart;

import javax.swing.JCheckBox;

public class ItemCheckBox extends JCheckBox {
	private Item item;
	
	public ItemCheckBox(Item i) {
		item = i;
	}
	
	public Item getItem() {
		return item;
	}
}
