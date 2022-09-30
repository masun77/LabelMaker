package oldFiles;

public class CompanyHeader {
	private String companyName;
	private String PONum;
	private String date;
	
	public CompanyHeader(String cn, String po, String dt) {
		companyName = cn;
		PONum = po;
		date = dt;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPONum() {
		return PONum;
	}

	public void setPONum(String pONum) {
		PONum = pONum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof CompanyHeader) {
			CompanyHeader ch = (CompanyHeader) obj;
			return (companyName.equals(ch.getCompanyName()) && date.equals(ch.getDate()) && 
					PONum.equals(ch.getPONum()));
		}
		return false;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        return prime * companyName.hashCode() * PONum.hashCode() * date.hashCode();
    }
	
	@Override
	public String toString() {
		return "{" + companyName + ", " + PONum + ", " + date + "}";
	}
}
