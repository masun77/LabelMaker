package main;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {
		// get order file name
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter order filename: "); // todo: better way to get file? e.g. select? or autoname or sth?
//		String fileName = scanner.nextLine();
		
		// Read order file into program
		//FileReader fr = new ExcelFileReader();
		//ArrayList<Order> orders = fr.readFileToOrders(fileName);
		//System.out.println(orders.get(0).toString());
		
		// Render labels
		LabelRenderer lr = new LabelRenderer();
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("cust", "lettuce", "5 lb bag", "00081812345", new DateImp(1,2,2021)));
		items.add(new Item("Maya", "carrots", "10 bunches", "00000818187777", new DateImp(4,25,2022)));
		lr.renderLabels(items);
		
		// Print labels
		lr.print();
		
	}

}
