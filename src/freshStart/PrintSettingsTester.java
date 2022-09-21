package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.print.attribute.standard.MediaPrintableArea;

import org.junit.jupiter.api.Test;

class PrintSettingsTester {

	@Test
	void test() {
		LabelFormat lf = new LabelFormatReader().readLabelFormats().get(0);
		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("Maya", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 1, 25));
		items.add(new Item("Good Eggs", new DateImp(9,21,22), "Carrots", 
				"CAR25", "818181230", "25 lbs/cs", 1.14f, 75));
		items.add(new Item("Chez Panisse", new DateImp(9,22,22), "Eggs", 
				"AEGG", "818184440", "by the dozen", 5, 25));
		PrintSettings ps = new PrintSettings(items, lf);
		ps.showPrintDialog();
		
		while(true) {
			
		}
	}
	
	@Test
	void testPrintConfigREader() {
		PrintConfigReader pcr = new PrintConfigReader();
		PrinterDescription pd = pcr.readPrinterConfig();
		assertEquals(pd.getPrinterName(), "PDF");
		assertTrue(pd.getMediaName().toString().equals("iso-a6"));
		assertEquals(3, (int) pd.getPrintableArea().getWidth(MediaPrintableArea.INCH));
	}

}
