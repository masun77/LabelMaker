/**
 * ExcelHeader
 * Represents one header - the type of header, the String name in a given format, the column index of that header.
 */

package freshStart;

public class ExcelHeader {
	private HeaderOption headerType;
	private String header;
	
	public ExcelHeader(HeaderOption ht, String h) {
		headerType = ht;
		header = h;
	}

	public HeaderOption getHeaderType() {
		return headerType;
	}

	public void setHeaderType(HeaderOption headerType) {
		this.headerType = headerType;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
