package main;

import java.util.ArrayList;

public interface FileReader {
	public ArrayList<Order> readFileToOrders(String fileName);
}
