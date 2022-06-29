 package export;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import labels.LabelableItem;
import main.Order;

public class SocketClient implements DataClient {
	private String SERVER = "192.168.254.142";
	private final int PORT = 9998;
	private final String PATH = "saveOrders.csv";
	
	@Override
	public void setIPAddress(String s) {
		SERVER = s;
	}
	
	@Override
	public String getIPAddress() {
		return SERVER;
	}
	
	public void sendOrders(ArrayList<Order> orders) {
		System.out.println("sending!!!");
		try {
			Socket socket = new Socket(SERVER, PORT);
			
			PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            out.println("PUSH");

            for (int o = 0; o < orders.size(); o++) {
            	Order ord = orders.get(o);
            	ArrayList<LabelableItem> items = ord.getItems();
        		for (int i = 0; i < items.size(); i++) {
        			LabelableItem item = items.get(i);
                    String data = o + "|" + ord.getPONum() + "|" + ord.getShipVia() 
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
	
	public ArrayList<Order> getOrders() {
        ArrayList<String[]> allData = new ArrayList<String[]>();
        System.out.println("getting data");
        	
		try {
			Socket socket = new Socket(SERVER, PORT);
			
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
		
		return DataSaver.getOrdersFromList(allData);
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
}
