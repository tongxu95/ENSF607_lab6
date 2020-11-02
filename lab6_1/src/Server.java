import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private Socket palinSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut; //write to client
	private BufferedReader socketIn; //read from client
	
	/**
	 * Constructor for the server
	 * 
	 * @param serverName the name of the server
	 * @param portNumber the port number of the server and the client
	 */
	public Server(String serverName, int portNumber) {
		try {
			//bind serverSocket to the port number
			serverSocket = new ServerSocket(portNumber);
			System.out.println("[SERVER] waiting for client connection......\n");
			//listen for a connection to be made to this socket and accept it
			palinSocket = serverSocket.accept();
			System.out.println("[SERVER] is now running......\n");
			//send information to client
			//getOutputStream() returns an output stream for this socket
			//parameter autoFlush - A boolean
			//if true, the println, printf, or format methods will flush the output buffer
			socketOut = new PrintWriter(palinSocket.getOutputStream(), true);
			//recerive informatoin from client
			socketIn = new BufferedReader(new InputStreamReader (palinSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * find whether the given string is a palindrome or not
	 * 
	 * @param str the given string
	 * @return
	 */
	public boolean isPalindrome(String str) {
		//index of first element of the string
		int i = 0;
		//index of last element of the string	
		int j = str.length() - 1;	
		
		//iterate through the string
		while (i < j) {
			//if a mismatch is found, return false
			if (str.charAt(i) != str.charAt(j)) 
                return false;
			//if no mismatch is found at current stage, keep checking
			i++;
			j--;
		}
		//string is confirmed to be a palindrome, return true
		return true;
	}
	
	/**
	 * communicates with the server
	 */
	private void communicate() {
		String line = "";
		boolean running = true;
		while (running) {
			try {
				line = socketIn.readLine();
				//quit the program if user input is "QUIT"
				if(line == null)
					break;
				if (isPalindrome(line))
					socketOut.println(line + " is a palindrome");
				else
					socketOut.println(line + " is NOT a palindrome");
			} catch (IOException e) {
				System.err.println(e.getStackTrace());
			}
		}
		try {
			System.out.println("[SERVER] Closed");
			serverSocket.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		Server aServer = new Server("localhost", 8099);
		aServer.communicate();
	}

}
