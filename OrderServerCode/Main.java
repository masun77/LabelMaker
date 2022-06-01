package server;

public class Main {
	public static void main(String[] args) {
		System.out.println("main");
		
		Server server = new Server();
		server.startServer();
		
		 try
        {
            Thread.sleep( 30000 );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

		System.out.println("main stopping");
        server.stopServer();
	}
}
