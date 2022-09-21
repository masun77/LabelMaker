package main;

import java.awt.Component;

import freshStart.Date;

public interface LabelableItem {

	/**
	 * Get the label for this item, displayable as a component. 
	 * @return
	 */
	public Component getLabel();
	
	public String getCustomer();
	
	public String stringRep();
	
	public String getProductName();
	
	public Date getPackDate();
	
	public String getItemCode();
	
	public String getGtin();
	
	public String getUnit();
	
	public float getQuantity();
	
	public float getPrice();
	
	public int getQuantityRoundedUp();
	
	public void setCustomer(String c);
	
	public void setProductName(String name);
	
	public void setPackDate(Date d);
	
	public void setItemCode(String ic);
	
	public void setGtin(String gt);
	
	public void setUnit(String ut);
	
	public void setQuantity(float qt);
	
	public void setPrice(float qt);
	
}
