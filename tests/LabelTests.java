import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import display.LabelView;
import display.LabelViewerImp;
import display.Labelable;
import labels.DateImp;
import main.RDFItem;

class LabelTests {
	private ArrayList<Labelable> items = new ArrayList<Labelable>();
	
	@BeforeEach
	void setup() {
		items.add(new RDFItem());
		items.add(new RDFItem("Maya", "Arugula", "3 lbs", "00000123456789", new DateImp(5,15,2022)));
	}

	@Test
	void test() {
		LabelView lv = new LabelViewerImp();
		lv.showLabels(items);
		System.out.println("done");
	}

}
