package export;

import java.awt.Dimension;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Order;

public class SocketClient {
	private final String SERVER = "127.0.0.1";
	private final int PORT = 9998;
	private final String PATH = "saveOrders.csv";
	
	public ArrayList<Order> getOrders() {
        ArrayList<String[]> allData = new ArrayList<String[]>();
		
		try {
			Socket socket = new Socket(SERVER, PORT);
			
			PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            out.println( "GET " + PATH + " HTTP/1.0" );

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
