package labels;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class BarCodeTest {
	private BarCodeGenerator bcg = new BarCodeImp();

	@Test
	void test() {
		ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(10,0,10));
		assertEquals(bcg.getBarCode("00000"), result);
	}

}
