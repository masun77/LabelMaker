package database;    // this error is because I'm running java 1.7, not a higher version

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExcelReaderTest {

	@Test
	void test() {
		DataImporter di = new ExcelInvoiceReader();
		di.setResourcePath("resources/sales7.22.xlsx");
		di.readInvoices();

	}

}
