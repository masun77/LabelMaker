package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

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
					serverPrintOut.println("Echo from server: " + line);
					if (line.trim().equals("q")) {
						done = true;
					}
				}
				closeAll();
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
				System.out.println("connection closed");
				runningSockets.remove(this);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 }
