package userInterface;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uiSubcomponents.CompanyHeader;

class CompanyHeaderTest {

	@Test
	void test() {
		CompanyHeader ch1 = new CompanyHeader("Maya", "1234", "07/13/1999");
		CompanyHeader ch2 = new CompanyHeader("Maya", "1234", "07/13/1999");
		CompanyHeader ch3 = new CompanyHeader("Maya", "2345", "07/13/1999");
		CompanyHeader ch4 = new CompanyHeader("Maya", "1234", "07/14/1999");
		CompanyHeader ch5 = new CompanyHeader("John", "1234", "07/13/1999");
		assertEquals(ch1.equals(ch2), true);
		assertEquals(ch1.equals(ch3), false);
		assertEquals(ch1.equals(ch4), false);
		assertEquals(ch1.equals(ch5), false);
	}

}
