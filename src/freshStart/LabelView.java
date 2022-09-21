package freshStart;

import java.util.ArrayList;

import javax.swing.JPanel;

public interface LabelView {
	public void showLabel(Item i, LabelFormat lf);
	public ArrayList<JPanel> getLabelsForList(ArrayList<Item> items, LabelFormat lf);
	public ArrayList<JPanel> getLabelsForItem(Item i, LabelFormat lf);
	public JPanel getSingleLabel(Item i, LabelFormat lf);
}
