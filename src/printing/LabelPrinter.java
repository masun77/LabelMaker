package printing;

import java.util.ArrayList;

import labels.LabelableItem;

public interface LabelPrinter {
	public void printLabels(ArrayList<LabelableItem> items);
}
