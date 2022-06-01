package server;

import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class Server extends Thread {
	private ServerSocket servSocket;
	private boolean running = true;
	private ArrayList<RequestHandler> runningSockets = new ArrayList<RequestHandler>();
	private final String FILE_NAME = "resources/saveOrders.csv";
	
	public void startServer( ) {
		try {
			int port = 9998;
			servSocket = new ServerSocket(port);
			running = true;
			start();
			
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				System.out.println("server listening for accept");
				Socket connectionSocket = servSocket.accept();
				
				RequestHandler rh = new RequestHandler(connectionSocket);
				runningSockets.add(rh);
				rh.start();
			}
			catch (Exception e) {
				System.out.println("socket closed");
            }
		}
	}
	
	public void stopServer() {
		running = false;
		this.interrupt();
		for (int r = 0; r < runningSockets.size(); r++) {
			runningSockets.get(r).closeAll();
		}
		runningSockets.removeAll(runningSockets);
		try {
			servSocket.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class RequestHandler extends Thread {
		private Socket socket;
		private Scanner scanner; 
		private PrintWriter sout;
		private InputStream inputToServer;
		private OutputStream outputFromServer;
		
		public RequestHandler(Socket s) {
			socket = s;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Single request received " + this);
				
				inputToServer = socket.getInputStream();
				outputFromServer = socket.getOutputStream();
				
				scanner = new Scanner(inputToServer, "UTF-8");
				sout = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
											
				boolean done = false;
				boolean readingFile = false;
				ArrayList<String> data = new ArrayList<String>();
				while (!done && scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.contains("GET")) {
						System.out.println("accessing csv");
						int space = line.indexOf(" ") + 1;
						int secondSpace = line.indexOf(" ", space);
						String fileName = line.substring(space, secondSpace);
						sendCSV(fileName);
						done = true;
					}
					else if (line.contains("PUSH")) {
						System.out.println("Saving csv");
						readingFile = true;
					}
					else if (line.contains("EOF")) {
						done = true;
					}
					else if (readingFile) {
						data.add(line + "\n");
					}
				}
				if (data.size() > 0) {
					DataSaver.writeOrdersToCSV(data, FILE_NAME);
				}
				closeAll();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void sendCSV(String fileName) {
			try {
		        FileReader filereader = new FileReader("resources/" + fileName);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		        for (int i = 0; i < allData.size(); i++) {
		        	String row = "";
		        	String[] wholeRow = allData.get(i);
		        	for (int s = 0; s < wholeRow.length; s++) {
		        		row += wholeRow[s] + "|";
		        	}
		        	sout.println(row);
		        }
		        sout.println("EOF");
		    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		public void closeAll() {
			try {
				scanner.close();
				inputToServer.close();
				outputFromServer.close();
				socket.close();
				runningSockets.remove(this);
				System.out.println("single connection closed");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 }
