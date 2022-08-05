package labels;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class CRCTest {
	private CRC16Generator crcgen = new CRC16Imp();

	@Test
	void test() {
		assertEquals(crcgen.crc16("123456789"), 47933, .01);
		assertEquals(crcgen.crc16("00000818181234"), 21913, .01);
		assertEquals(crcgen.crc16("00000818181234220512"), 22695, .01);
		assertEquals(crcgen.crc16("00000818181234220101"), 2215, .01);
		assertEquals(crcgen.crc16("00000818188678"), 	52697, .01);
	}

}
