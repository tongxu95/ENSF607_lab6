package lab6_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Create and connect socket to the tic-tac-toe server. 
 * 
 * @version 1.0
 * @author Tong Xu
 * @since Oct 31, 2020
 */
public class Client {
    /** Socket for connecting to server. */
	private Socket socket;
	
    /**
     * Create a Client socket that that connects on localhost at port 4444.
     */
	public Client () {
		try {
			socket = new Socket ("localhost", 4444);
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Communicate with server socket.
	 */
	public void communicate () {
		try (			
			BufferedReader stdIn = new BufferedReader (new InputStreamReader (System.in));
			BufferedReader socketIn = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter (socket.getOutputStream(), true);
		){
			boolean endGame = false;
			while(! endGame) {
				String line;
				while ((line = socketIn.readLine()) != null) {
					if (line.equals("EOF")) break;
					if (line.equals("QUIT")) {
						endGame = true;
						break;
					}
					System.out.println(line);
				}
				String input = stdIn.readLine();
				socketOut.println(input);
				
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		
	}
	
	public static void main (String[] args) {
		Client aClient = new Client ();
		aClient.communicate();
	}
}
