package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class BarCodeTest {
	private BarCodeGenerator bcg = new BarCodeImp();

	@Test
	public void test() {
		ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(
				10, // quiet zone
				2,1,1,2,3,2, // startChar
				4,1,1,1,3,1, // fnc1
				2,2,2,1,2,2, // AI - 01
				2,1,2,2,2,2, // gtin
				2,1,2,2,2,2,
				1,3,2,2,1,2,
				2,2,3,2,1,1,
				2,2,3,2,1,1,
				1,2,3,1,2,2,
				1,1,3,1,2,3,
				1,2,1,1,4,2, // symbol check
				2,3,3,1,1,1,2, // stop char
				10)); // quiet zone
		assertEquals(bcg.getBarCode("00000818181545"), result);
	}

}
