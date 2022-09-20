package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import static freshStart.HeaderOptions.*;

import org.junit.jupiter.api.Test;

class ExcelFormatTester {

	@Test
	void testFormatGetter() {
		ExcelFormatGetter efg = new ExcelFormatGetter();
		efg.readExcelFormats();
		assertEquals(1, efg.getFormats().size());
		assertTrue(efg.getFormatNames().contains("Quickbooks"));
		
		efg = new ExcelFormatGetter();
		efg.addNewFormat(new File("settings/excelFileFormats/quickbooksExcelFormat.txt"));
		ExcelFormat ef = efg.getFormats().get(0);
		testEF(ef);
	}
	
	private void testEF(ExcelFormat ef) {
		assertEquals("Quickbooks", ef.getName());
		assertEquals(3, ef.getHeaderRowIndex());
		assertEquals(5, ef.getDataStartRow());
		assertEquals("Invoice", ef.getTypesToSave());
		ArrayList<String> excluded = ef.getExcludedCompanies();
		assertTrue(excluded.contains("Market") && excluded.contains("TBFM") && excluded.contains("ThBFM"));
		
		HashMap<HeaderOptions, ExcelHeader> headers = ef.getHeaders();
		assertTrue(headers.get(TYPE) != null && headers.get(TYPE).getHeader().equals("Type"));
		assertTrue(headers.get(COMPANY) != null && headers.get(COMPANY).getHeader().equals("Name"));
		assertTrue(headers.get(SHIP_DATE) != null && headers.get(SHIP_DATE).getHeader().equals("Ship Date"));
		assertTrue(headers.get(INVOICE_NUMBER) != null && headers.get(INVOICE_NUMBER).getHeader().equals("Num"));
		//assertTrue(headers.get(PO_NUMBER) != null && headers.get(PO_NUMBER).getHeader().equals("P. O. #"));
		//assertEquals(headers.get(PO_NUMBER).getHeader(), "P. O. #");
		assertTrue(headers.get(SHIP_VIA) != null && headers.get(SHIP_VIA).getHeader().equals("Via"));
		assertTrue(headers.get(GTIN) != null && headers.get(GTIN).getHeader().equals("GTIN NUMBER"));
		assertTrue(headers.get(ITEM_CODE) != null && headers.get(ITEM_CODE).getHeader().equals("Item"));
		assertTrue(headers.get(ITEM_DESCRIPTION) != null && headers.get(ITEM_DESCRIPTION).getHeader().equals("Item Description"));
		assertTrue(headers.get(QUANTITY) != null && headers.get(QUANTITY).getHeader().equals("Qty"));
		assertTrue(headers.get(PRICE) != null && headers.get(PRICE).getHeader().equals("Sales Price"));
	}

}
