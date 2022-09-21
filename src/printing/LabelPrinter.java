package printing;

import java.util.ArrayList;

import main.LabelableItem;

public interface LabelPrinter {
	public void printLabels(ArrayList<LabelableItem> items);
}
