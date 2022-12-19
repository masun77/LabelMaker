package importData;

import static importData.HeaderOption.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

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
		assertEquals("Invoice", ef.getTypeToSave());
		ArrayList<String> excluded = ef.getExcludedCompanies();
		assertTrue(excluded.contains("Market") && excluded.contains("TBFM") && excluded.contains("ThBFM"));
		
		ArrayList<ExcelHeader> headers = ef.getHeaders();
		ArrayList<HeaderOption> types = new ArrayList<>();
		for (ExcelHeader h: headers ) {
			types.add(h.getHeaderType());
		}
		
		assertTrue(types.indexOf(TYPE) >= 0 && headers.get(types.indexOf(TYPE)).getHeader().equals("Type"));
		assertTrue(types.indexOf(COMPANY) >= 0 && headers.get(types.indexOf(COMPANY)).getHeader().equals("Name"));
		assertTrue(types.indexOf(SHIP_DATE) >= 0 && headers.get(types.indexOf(SHIP_DATE)).getHeader().equals("Ship Date"));
		assertTrue(types.indexOf(INVOICE_NUMBER) >= 0 && headers.get(types.indexOf(INVOICE_NUMBER)).getHeader().equals("Num"));
		assertTrue(types.indexOf(PO_NUMBER) >= 0 && headers.get(types.indexOf(PO_NUMBER)).getHeader().equals("P. O. #"));
		assertTrue(types.indexOf(SHIP_VIA) >= 0 && headers.get(types.indexOf(SHIP_VIA)).getHeader().equals("Via"));
		assertTrue(types.indexOf(GTIN) >= 0 && headers.get(types.indexOf(GTIN)).getHeader().equals("GTIN NUMBER"));
		assertTrue(types.indexOf(ITEM_CODE) >= 0 && headers.get(types.indexOf(ITEM_CODE)).getHeader().equals("Item"));
		assertTrue(types.indexOf(ITEM_DESCRIPTION) >= 0 && headers.get(types.indexOf(ITEM_DESCRIPTION)).getHeader().equals("Item Description"));
		assertTrue(types.indexOf(QUANTITY) >= 0 && headers.get(types.indexOf(QUANTITY)).getHeader().equals("Qty"));
		assertTrue(types.indexOf(PRICE) >= 0 && headers.get(types.indexOf(PRICE)).getHeader().equals("Sales Price"));
	}

}
