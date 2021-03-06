package labels;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import labels.Date;
import labels.DateImp;

class DateImpTest {
	private static Date d;
	private static Date d2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		d = new DateImp();
		d2 = new DateImp(4,20,2022);
	}

	@Test
	void testGetDate() {
		assertEquals(d.getDateMMDDYYYY(), "01/01/1900");
		assertEquals(d2.getDateMMDDYYYY(), "04/20/2022");
	}

	@Test
	void testGetMonthName() {
		assertEquals(d.getMonthName(), "January");
		assertEquals(d2.getMonthName(), "April");
	}
	
	@Test
	void testGetPackDateName() {
		assertEquals(d.getAsPackDate(), "Jan 01");
		assertEquals(d2.getAsPackDate(), "Apr 20");
	}
	
	@Test
	void testYYMMDD() {
		assertEquals(d.getDateYYMMDD(), "000101");
		assertEquals(d2.getDateYYMMDD(), "220420");
	}
	
	@Test
	void testParseDate() {
		assertEquals(DateImp.parseDate("").getDateMMDDYYYY(), "01/01/2022");
		assertEquals(DateImp.parseDate("5/1/22").getDateMMDDYYYY(), "05/01/2022");
	}

}
