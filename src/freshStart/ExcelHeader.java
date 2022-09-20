package freshStart;

public class ExcelHeader {
	private HeaderOptions headerType;
	private String header;
	private int columnIndex;
	
	public ExcelHeader(HeaderOptions ht, String h, int col) {
		headerType = ht;
		header = h;
		columnIndex = col;
	}

	public HeaderOptions getHeaderType() {
		return headerType;
	}

	public void setHeaderType(HeaderOptions headerType) {
		this.headerType = headerType;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
}
