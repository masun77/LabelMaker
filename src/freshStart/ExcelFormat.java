/**
 * ExcelFormat
 * Each ExcelFormat represents the format an Excel file may be in
 * in order for company Orders to be successfully read from it with the ExcelReader class.
 * An ExcelFormat must have a name, header row number, data start row number,
 * and may have other information including header names such as "Company name", "GTIN" etc.
 */

package freshStart;

import java.util.ArrayList;
import java.util.HashMap;
import static freshStart.HeaderOption.*;

public class ExcelFormat {
	private ArrayList<String> settings = new ArrayList<>();
	private String name = "";
	private int headerRowIndex = 0;
	private int dataStartRow = 1;
	private String typesToSave = "";
	private ArrayList<String> excludedCompanies = new ArrayList<>();
	private ArrayList<ExcelHeader> headers = new ArrayList<>();
	
	public ExcelFormat() {
		initializeSettings();
	}
	
	/**
	 * Add a setting given one line of an Excel format file.
	 * Each line must have the format shown on the line below:
	 * 		setting name: setting value
	 * @param line the line to add the setting from.
	 */
	public void addSetting(String line) {
		int colon = line.indexOf(":");
		if (colon < 0) {
			return;
		}
		String settingName = line.substring(0,colon).strip().toLowerCase();
		String settingValue = line.substring(colon +1).strip();
		int settingNum = settings.indexOf(settingName);
		
		switch (settingNum) {
			case 0:
				name = settingValue;
				break;
			case 1:
				headerRowIndex = Integer.parseInt(settingValue);
				break;
			case 2:
				dataStartRow = Integer.parseInt(settingValue);
				break;
			case 3:
				typesToSave = settingValue;
				break;
			case 4:
				saveExcludedCustomers(settingValue);
				break;
			case 5:
				headers.add(new ExcelHeader(TYPE, settingValue));
				break;
			case 6:
				headers.add(new ExcelHeader(COMPANY, settingValue));
				break;
			case 7:
				headers.add(new ExcelHeader(SHIP_DATE, settingValue));
				break;
			case 8:
				headers.add(new ExcelHeader(INVOICE_NUMBER, settingValue));
				break;
			case 9:
				headers.add(new ExcelHeader(PO_NUMBER, settingValue));
				break;
			case 10:
				headers.add(new ExcelHeader(SHIP_VIA, settingValue));
				break;
			case 11:
				headers.add(new ExcelHeader(GTIN, settingValue));
				break;
			case 12:
				headers.add(new ExcelHeader(ITEM_CODE, settingValue));
				break;
			case 13:
				headers.add(new ExcelHeader(ITEM_DESCRIPTION, settingValue));
				break;
			case 14:
				headers.add(new ExcelHeader(QUANTITY, settingValue));
				break;
			case 15:
				headers.add(new ExcelHeader(PRICE, settingValue));
				break;
			default:
				break;
		}
	}
	
	/**
	 * Save the list of excluded customers to a list. 
	 * The list of customers must a string with customer names separated by commas.
	 * For example, "Chez Panisse, Whole Foods, Wise Sons"
	 * @param line the String containing the list of customers, separated by commas. 
	 */
	private void saveExcludedCustomers(String line) {
		int commaIndex = line.indexOf(",");
		while(commaIndex > 0) {
			excludedCompanies.add(line.substring(0,commaIndex).strip());
			line = line.substring(commaIndex + 1);
			commaIndex = line.indexOf(",");
		}
		excludedCompanies.add(line.strip());
	}
	
	/**
	 * Add available settings to the settings list.
	 */
	private void initializeSettings() {
		settings.add("format name");    // 0
		settings.add("header row");    // 1
		settings.add("data start row");    // 2
		settings.add("types to save");    // 3
		settings.add("exclude customers");    // 4
		settings.add("type header");    // 5
		settings.add("company header");    // 6
		settings.add("ship date header");    // 7
		settings.add("invoice number header");    // 8
		settings.add("po header");    // 9
		settings.add("ship via header");    // 10
		settings.add("gtin header");    // 11
		settings.add("item code header");    // 12
		settings.add("item description header");    // 13
		settings.add("quantity header");    // 14
		settings.add("price header");		    // 15
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	public int getHeaderRowIndex() {
		return headerRowIndex;
	}
	
	public int getDataStartRow() {
		return dataStartRow;
	}

	public ArrayList<String> getSettings() {
		return settings;
	}

	public String getTypeToSave() {
		return typesToSave;
	}

	public ArrayList<String> getExcludedCompanies() {
		return excludedCompanies;
	}

	public ArrayList<ExcelHeader> getHeaders() {
		return headers;
	}
}
