package main;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LabelMakerMain {

	public static void main(String[] args) {
		// Todo:
		// 1. get order file name
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter order filename: "); // todo: better way to get file? e.g. select? or autoname or sth?
//		String fileName = scanner.nextLine();
		
		// 2. Read order file into program
		//FileReader fr = new ExcelFileReader();
		//ArrayList<Order> orders = fr.readFileToOrders(fileName);
		//System.out.println(orders.get(0).toString());
		
		// 3. Render labels
		LabelRenderer lr = new LabelRenderer();
		lr.renderLabel(new Order("cust", "lettuce", "0000812129871", new DateImp(1,2,2021)));
		
		// 4. Print labels
		
	}

}
