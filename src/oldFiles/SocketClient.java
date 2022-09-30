 package oldFiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class SocketClient implements DataClient {
	private String server;
	private int port;
	private final String PATH = "saveOrders.csv";

	@Override
	public void setIPAddress(String s) {
		server = s; // todo: save to config file
	}
	
	private void getIPSettings() {
		server = AppState.getFileBackup().getServerIPAddress();
		port = AppState.getFileBackup().getServerPort();
	}
	
	@Override
	public void saveOrders(ArrayList<Order> orders) {
		final ArrayList<Order> ords = orders;
		
		Runnable runnable = new Runnable(){
		    public void run() {
		    	getIPSettings();
	     		if (port == 0) {
	     			server = AppState.getFileBackup().getServerIPAddress();
	     			port = AppState.getFileBackup().getServerPort();
	     		}
	     		System.out.println("sending!!!");
	     		try {
	     			Socket socket = new Socket(server, port);
	     			
	     			PrintStream out = new PrintStream( socket.getOutputStream() );
	                 BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

	                 out.println("PUSH");

	                 for (int o = 0; o < ords.size(); o++) {
	                 	Order ord = ords.get(o);
	                 	ArrayList<LabelableItem> items = ord.getItems();
	             		for (int i = 0; i < items.size(); i++) {
	             			LabelableItem item = items.get(i);
	                         String data = ord.getInvoiceNumber() + "|" + ord.getPONum() + "|" + ord.getShipVia() 
	                         		+ "|" + item.getCustomer() + "|" + item.getPackDate().getDateMMDDYYYY()
	                         		+ "|" + item.getItemCode() + "|" + item.getGtin() + "|" + item.getProductName() 
	                         		+ "|" + item.getUnit()
	                         		+ "|" + Float.toString(item.getQuantity()) + "|" + Float.toString(item.getPrice());
	                 		out.println(data);
	             		}
	                 }
	                 out.println("EOF\r\n");
	                 out.flush();
	                 
	                 in.close();
	                 out.close();
	                 socket.close();
	     		} 
	     		catch( Exception e ) {
	     			e.printStackTrace();
	             }
		     }	    
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	public ArrayList<Order> getOrders() {
		getIPSettings();
        ArrayList<String[]> allData = new ArrayList<String[]>();
        System.out.println("getting data");
        	
		try {
			Socket socket = new Socket(server, port);
			
			PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            out.println( "GET " + PATH + " HTTP/1.0\r\n" );
            out.flush();

            String line = in.readLine();
            while( !line.equals("EOF") )
            {
            	String[] temp = getStrArrFromStr(line);
            	allData.add(temp);
                line = in.readLine();
            }
            
            in.close();
            out.close();
            socket.close();
			
		} 
		catch( Exception e ) {
			e.printStackTrace();
		}
		
		return ((DataSaver)AppState.getFileBackup()).getOrdersFromList(allData);
	}
	
	private String[] getStrArrFromStr(String line) {
		String[] arr = new String[15];
		int start = 0;
		int breakIndex = 1;
		int i = 0;
		while (breakIndex > 0) {
			breakIndex = line.indexOf("|", start);
			if (breakIndex < 0) {
				arr[i] = line.substring(start);
			}
			else {
				arr[i] = line.substring(start, breakIndex);
			}
			i += 1;
			start = breakIndex + 1;
		}
		
		return arr;
	}


	@Override
	public String getServerIPAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getItemDescription(String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getItemData(String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}	
}
