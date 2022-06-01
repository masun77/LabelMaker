package export;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClient {
	// todo: make normal function and call with other classes
	public static void main(String[] args) {
		String server = "127.0.0.1";
		int port = 9999;
		String path = "saveOrders.csv";
		
		try {
			Socket socket = new Socket(server, port);
			
			PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            // Follow the HTTP protocol of GET <path> HTTP/1.0 followed by an empty line
            out.println( "GET " + path + " HTTP/1.0" );
            out.println("q");

            // Read data from the server until we finish reading the document
            String line = in.readLine();
            while( line != null )
            {
                System.out.println( line );
                line = in.readLine();
            }
            
            in.close();
            out.close();
            socket.close();
            System.out.println("closing client");
			
		} 
		catch( Exception e ) {
            e.printStackTrace();
        }
	}
}
