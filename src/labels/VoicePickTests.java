package labels;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VoicePickTests {
	private VoicePickCodeGenerator vpcg = new VoicePickImp();

	@Test
	void test() {
		assertEquals(vpcg.calculateVoicePickCode("00000818182345", "210102"), "5071");
		assertEquals(vpcg.calculateVoicePickCode("00000000000000", new DateImp(1,1,2000).getDateYYMMDD()), "0290");
		assertEquals(vpcg.calculateVoicePickCode("106141410005811234", "100124"), "0820");
		assertEquals(vpcg.calculateVoicePickCode("123456789100001111", "100130"), "2925");
	}

}
