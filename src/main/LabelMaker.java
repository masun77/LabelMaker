package main;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LabelMaker {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter order filename: "); // todo: better way to get file? e.g. select? or autoname or sth?
		String fileName = scanner.nextLine();
		
		FileReader fr = new ExcelFileReader();
		ArrayList<Order> orders = fr.readFileToOrders(fileName);
		
		System.out.println(orders.get(0).toString());
		
	}

}
