package main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LabelUtilitiesTest {
	@Test
	void testVPCode() {
		assertEquals(LabelUtilities.calculateVoicePickCode("00000818182345", new DateImp(1,2,2021)), "5071");
		assertEquals(LabelUtilities.calculateVoicePickCode("00000000000000", new DateImp(1,1,2000)), "0290");
		assertEquals(LabelUtilities.calculateVoicePickCode("106141410005811234", new DateImp(1,24,2010)), "0820");
		assertEquals(LabelUtilities.calculateVoicePickCode("123456789100001111", new DateImp(1,30,2010)), "2925");
	}

	@Test
	void testCRC() {
		if (CRC16.crc16("00000818182345210102") == 45071) {
			assertTrue(true);
		}
		else {
			fail();
		}
	}
}
