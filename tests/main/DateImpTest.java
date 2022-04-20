package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.Date;
import main.DateImp;

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
		assertEquals(d.getDate(), "01/01/1900");
		assertEquals(d2.getDate(), "04/20/2022");
	}

	@Test
	void testGetMonthName() {
		assertEquals(d.getMonthName(), "January");
		assertEquals(d2.getMonthName(), "April");
	}

}
