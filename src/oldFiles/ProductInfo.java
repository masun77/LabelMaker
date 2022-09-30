package oldFiles;

public class ProductInfo {
	private String displayName;
	private String unit;
	private String itemCode;
	
	public ProductInfo(String name, String unt, String code) {
		displayName = name;
		unit = unt;
		itemCode = code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other instanceof ProductInfo) {
			ProductInfo ch = (ProductInfo) other;
			return (displayName.equals(ch.getDisplayName()) && unit.equals(ch.getUnit()) && 
					itemCode.equals(ch.getItemCode()));
		}
		return false;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        return prime * displayName.hashCode() * unit.hashCode() * itemCode.hashCode();
    }
	
	@Override
	public String toString() {
		return "{" + displayName + ", " + unit + ", " + itemCode + "}";
	}
}
