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

import labels.Date;
import labels.DateImp;
import main.Item;
import main.Order;
import main.RDFItem;

public class Server extends Thread {
	private ServerSocket servSocket;
	private boolean running = true;
	private ArrayList<RequestHandler> runningSockets = new ArrayList<RequestHandler>();
	
	public void startServer( ) {
		try {
			System.out.println("starting server");
			int port = 9999;

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
                //e.printStackTrace();
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
		private PrintWriter serverPrintOut;
		private InputStream inputToServer;
		private OutputStream outputFromServer;
		
		public RequestHandler(Socket s) {
			socket = s;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Connection received");
				
				inputToServer = socket.getInputStream();
				outputFromServer = socket.getOutputStream();
				
				scanner = new Scanner(inputToServer, "UTF-8");
				serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
							
				serverPrintOut.println("Server. Enter q to exit.");
				serverPrintOut.flush();
				
				boolean done = false;
				while (!done && scanner.hasNextLine()) {
					String line = scanner.nextLine();
					//serverPrintOut.println("Echo from server: " + line);
					serverPrintOut.flush();
					serverPrintOut.println("Server. respect.");
					serverPrintOut.flush();
					serverPrintOut.println("Server. what the.");
					serverPrintOut.flush();
					
					
//					serverPrintOut.println("what?");
//					serverPrintOut.flush();
//					if (line.trim().equals("q")) {
//						done = true;
//					}
//					else if (line.contains("GET")) {
//						int space = line.indexOf(" ");
//						int secondSpace = line.indexOf(" ", space + 1);
//						String fileName = line.substring(space + 1, secondSpace);
//						sendFile(fileName);
//						serverPrintOut.println("hmm");
//					}
				}
				closeAll();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void sendFile(String fileName) {
			serverPrintOut.println("accessing csv");
			ArrayList<Order> orders = new ArrayList<Order>();
			try {
		        FileReader filereader = new FileReader("resources/" + fileName);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		        for (String[] line: allData) {
//		        	String data = "";
//		        	for (int i = 0; i < line.length; i++) {
//		        		
//		        	}
			        serverPrintOut.println("CSv: " + line);
		        }
		        
		    }
		    catch (Exception e) {
		    	serverPrintOut.println("Server: File not found.");
		    }
			return orders;
		}
		
		public void closeAll() {
			try {
				scanner.close();
				inputToServer.close();
				outputFromServer.close();
				socket.close();
				System.out.println("connection closed");
				runningSockets.remove(this);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 }
