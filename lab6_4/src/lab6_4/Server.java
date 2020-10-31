package lab6_4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Multigame tick-tac-toe server. 
 * 
 * @version 1.0
 * @author Tong Xu
 * @since Nov 6, 2020
 *
 */
public class Server {
	
    /** Socket for receiving incoming connections. */
    private ServerSocket serverSocket;
    
	/**
	 * thread pool
	 */
	private ExecutorService pool;

    /**
     * accept two players (client sockets)
     */
    private Socket[] players;
    
    /**
     * Create a Server that listens for connection on port 4444.
     */
	public Server() {
		try {
			serverSocket = new ServerSocket(4444);
			pool = Executors.newCachedThreadPool();
			players = new Socket[2];
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void serve() {
		try {
			while(true) {

	            System.out.println("Server is running...");
	            
				for (int i = 0; i < players.length; i++) {
					players[i] = serverSocket.accept();
				}

	            pool.execute(new Game(players));
	            
	            players = new Socket[2];
	            
 			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		pool.shutdown();
		
	}
	
	public static void main (String[] args) {
		Server myServer = new Server();
		myServer.serve();
	}

}
