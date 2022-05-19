package userInterface;

import java.util.ArrayList;

import main.Order;

public interface UserInterface {
	public void showInterface(ArrayList<Order> orders); // todo: pass in necessary info? 
	
	// might need to add observable/observer pattern
}
