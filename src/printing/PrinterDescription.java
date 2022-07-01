package printing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;

public class PrinterDescription {
	private String printerName;
	private MediaSizeName mediaName;
	private MediaPrintableArea printableArea;
	
	private final int NAME_INDEX = 0;
	private final int MEDIA_NAME_INDEX = 1;
	private final int PRINTABLE_AREA_INDEX = 2;
	
	public PrinterDescription(List<String[]> allData) {
		printerName = allData.get(NAME_INDEX)[0];
		mediaName = getMediaSizeNameFromString(allData.get(MEDIA_NAME_INDEX)[0]);
		printableArea = getPrintableAreaFromString(allData.get(PRINTABLE_AREA_INDEX));
	}
	
	private MediaSizeName getMediaSizeNameFromString(String str) {
		Map<String, MediaSizeName> msns = getMSNs();
		return msns.get(str);
	}
	
	private MediaPrintableArea getPrintableAreaFromString(String[] strArray) {
		return new MediaPrintableArea(Float.parseFloat(strArray[0]),Float.parseFloat(strArray[1]),
				Float.parseFloat(strArray[2]),Float.parseFloat(strArray[3]),MediaPrintableArea.INCH);
	}
	
	public String getPrinterName() {
		return printerName;
	}
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	public MediaSizeName getMediaName() {
		return mediaName;
	}
	public void setMediaName(MediaSizeName mediaName) {
		this.mediaName = mediaName;
	}
	public MediaPrintableArea getPrintableArea() {
		return printableArea;
	}
	public void setPrintableArea(MediaPrintableArea printableArea) {
		this.printableArea = printableArea;
	}	
	
	private Map<String, MediaSizeName> getMSNs() {
		Map<String, MediaSizeName> msns = new HashMap<String,MediaSizeName>();
		msns.put("A0", MediaSizeName.ISO_A0);
		msns.put("A1", MediaSizeName.ISO_A1);
		msns.put("A2", MediaSizeName.ISO_A2);
		msns.put("A3", MediaSizeName.ISO_A3);
		msns.put("A4", MediaSizeName.ISO_A4);
		msns.put("A5", MediaSizeName.ISO_A5);
		msns.put("A6", MediaSizeName.ISO_A6);
		msns.put("A7", MediaSizeName.ISO_A7);
		msns.put("A8", MediaSizeName.ISO_A8);
		msns.put("A9", MediaSizeName.ISO_A9);
		msns.put("A10", MediaSizeName.ISO_A10);
		
		msns.put("B0", MediaSizeName.ISO_B0);
		msns.put("B1", MediaSizeName.ISO_B1);
		msns.put("B2", MediaSizeName.ISO_B2);
		msns.put("B3", MediaSizeName.ISO_B3);
		msns.put("B4", MediaSizeName.ISO_B4);
		msns.put("B5", MediaSizeName.ISO_B5);
		msns.put("B6", MediaSizeName.ISO_B6);
		msns.put("B7", MediaSizeName.ISO_B7);
		msns.put("B8", MediaSizeName.ISO_B8);
		msns.put("B9", MediaSizeName.ISO_B9);
		msns.put("B10", MediaSizeName.ISO_B10);
		
		msns.put("C0", MediaSizeName.ISO_C0);
		msns.put("C1", MediaSizeName.ISO_C1);
		msns.put("C2", MediaSizeName.ISO_C2);
		msns.put("C3", MediaSizeName.ISO_C3);
		msns.put("C4", MediaSizeName.ISO_C4);
		msns.put("C5", MediaSizeName.ISO_C5);
		msns.put("C6", MediaSizeName.ISO_C6);

		return msns;
	}
}
