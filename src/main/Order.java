/**
 * Order
 * Represents an invoice with a company, date, etc.
 * and a list of items. 
 */

package main;

import java.util.ArrayList;

import labels.Date;
import labels.DateImp;

public class Order {
	private String company = "";
	private String PONum = "";
	private String shipVia = "";
	private int invoiceNum = 0;
	private Date shipDate = new DateImp(1,1,2022);
	private ArrayList<Item> items = new ArrayList<>();
	
	public Order(String comp, String ponum, String sv, int invNum, Date sd) {
		company = comp;
		PONum = ponum;
		shipVia = sv;
		invoiceNum = invNum;
		shipDate = sd;
	}
	
	public Order() {
		
	}
	
	/** 
	 * Print out all information about this order.
	 */
	public void printOrder() {
		System.out.println(this.toString());
		printItems();
	}
	
	/**
	 * Print out the info for each item in this order.
	 */
	private void printItems() {
		for (Item i: items) {
			System.out.print("\n\t" + i.toString());
		}
	}
	
	/**
	 * Add an item to this order.
	 * @param i the Item to add.
	 */
	public void addItem(Item i) {
		items.add(i);
	}
	
	@Override
	public String toString() {
		String str = "Invoice num: " + invoiceNum + "\nCompany:" + 
				company + "\n\tPO: " + PONum + 
				"\n\tShip Date: " + shipDate.getDateMMDDYYYY() + "\n\tShip via: " + shipVia;
		for (Item i: items) {
			str += "\n\t" + i.toString();
		}
		
		return str;
	}
	
	// Getters and setters
	public void setCompany(String c) {
		company = c;
	}
	
	public String getCompany() {
		return company;
	}

	public String getPONum() {
		return PONum;
	}

	public void setPONum(String pONum) {
		PONum = pONum;
	}

	public String getShipVia() {
		return shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public int getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(int invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	
}
