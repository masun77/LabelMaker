package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class LabelFormatTester {

	void test() {
		LabelFormatReader lfr = new LabelFormatReader();
		ArrayList<LabelFormat> formats = lfr.readLabelFormats();
		assertEquals(1, formats.size());
		LabelFormat form = formats.get(0); 
		assertTrue(form.getName().equals("RDF 4x3.5"));
		assertEquals(form.getLabelDimensions().getxMax(),410);
		assertEquals(form.getLabelDimensions().getyMax(),300);
		ArrayList<TextObject> texts = form.getTextObjects();
		ArrayList<RectangleObject> rects = form.getRectangles();
		assertEquals(8, texts.size() );
		assertEquals(3, rects.size());
		assertTrue(texts.get(2).getName().equals("productName"));
		assertEquals(5,texts.get(3).getBounds().getxMin());
		assertEquals(153,texts.get(6).getBounds().getyMax());
		assertTrue(texts.get(7).getName().equals("vpcLarge"));
	}

}
