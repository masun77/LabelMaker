package server;

public class Main {
	public static void main(String[] args) {
		System.out.println("Starting server");
		
		Server server = new Server();
		server.startServer();
		
		 try
        {
            Thread.sleep( 10000 );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

		System.out.println("Stopping server");
        server.stopServer();
	}
}
