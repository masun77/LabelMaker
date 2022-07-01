package printing;

import static org.junit.jupiter.api.Assertions.*;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;

import org.junit.jupiter.api.Test;

import export.DataSaver;
import export.FileBackup;

class PrinterDescripTests {

	@Test
	void test() {
		FileBackup fb = new DataSaver();
		PrinterDescription pd = fb.getPrinterDescription();
		assertEquals(pd.getPrinterName(), "Godex");
		assertEquals(pd.getMediaName(), MediaSizeName.ISO_A6);
		assertEquals(pd.getPrintableArea().getX(MediaPrintableArea.INCH),0.2f);
		assertEquals(pd.getPrintableArea().getY(MediaPrintableArea.INCH),2.5f);
		assertEquals(pd.getPrintableArea().getWidth(MediaPrintableArea.INCH),3.6f);
		assertEquals(pd.getPrintableArea().getHeight(MediaPrintableArea.INCH),3.5f);
	}

}

