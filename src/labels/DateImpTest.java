package labels;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
		assertEquals(d.getDateMMDDYYYY(), "01/01/2000");
		assertEquals(d2.getDateMMDDYYYY(), "04/20/2022");
	}

	@Test
	void testGetMonthName() {
		assertEquals(d.getMonthName(), "January");
		assertEquals(d2.getMonthName(), "April");
	}
	
	@Test
	void testGetPackDateName() {
		assertEquals(d.getAsLabelDate(), "Jan 01");
		assertEquals(d2.getAsLabelDate(), "Apr 20");
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
	
	@Test
	void testDateEqual() {
		assertEquals(true, new DateImp(5,1,22).dateEquals(new DateImp(5,1,22)));
		assertEquals(false, new DateImp(5,2,22).dateEquals(new DateImp(5,1,22)));
		assertEquals(false, new DateImp(5,1,21).dateEquals(new DateImp(5,1,22)));
		assertEquals(false, new DateImp(4,1,22).dateEquals(new DateImp(5,1,22)));
	}
	
	@Test
	void testDateEarlier() {
		assertEquals(true, new DateImp(4,1,22).dateEarlierThan(new DateImp(5,1,22)));
		assertEquals(false, new DateImp(5,2,22).dateEarlierThan(new DateImp(5,1,22)));
		assertEquals(true, new DateImp(5,1,21).dateEarlierThan(new DateImp(5,1,22)));
		assertEquals(false, new DateImp(6,1,22).dateEarlierThan(new DateImp(5,1,22)));
	}
	
	@Test
	void testDateLater() {
		assertEquals(false, new DateImp(4,1,22).dateLaterThan(new DateImp(5,1,22)));
		assertEquals(true, new DateImp(5,2,22).dateLaterThan(new DateImp(5,1,22)));
		assertEquals(false, new DateImp(8,10,21).dateLaterThan(new DateImp(5,1,22)));
		assertEquals(true, new DateImp(6,1,22).dateLaterThan(new DateImp(5,1,22)));
	}
	
	@Test
	void testDateCellParse() {
		DateImp.parseCellDate("Tue Aug 02 00:00:00 PDT 2022");
	}
	
	@Test
	void testAddDays() {
		Date d = new DateImp(4,1,22);
		d.addDays(16);
		assertEquals(d.getDateMMDDYYYY(), "04/17/2022");
		d.addDays(30);
		assertEquals(d.getDateMMDDYYYY(), "05/17/2022");
		d.addDays(90);
		assertEquals(d.getDateMMDDYYYY(), "08/15/2022");
		d.addDays(365);
		assertEquals(d.getDateMMDDYYYY(), "08/15/2023");
	}
}
