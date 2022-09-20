package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LabelViewTester {

	@Test
	void test() {
		LabelViewerG2 lv2 = new LabelViewerG2();
		LabelFormatReader lfr = new LabelFormatReader();
		LabelFormat lf = lfr.readLabelFormats().get(0);
		Item i = new Item("Maya", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 2, 25);
		lv2.showLabel(i, lf);
		
		while(true) {
			
		}
	}

}
