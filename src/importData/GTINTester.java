package importData;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GTINTester {
	@Test
	void testGetFile() {
		GTINGetter gg = new GTINGetter();
		gg.readGTINs();
	}
}
