import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DateClient {
	
	private PrintWriter socketOut; //write to server
	private Socket dateSocket; //handle IO for server
	private BufferedReader stdIn; //read from user input
	private BufferedReader socketIn; //read from server
	
	/**
	 * Constructor for the Client
	 * 
	 * @param serverName the name of the server
	 * @param portNumber the port number of the server and the client
	 */
	public DateClient (String serverName, int portNumber){
		try {
			dateSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(dateSocket.getInputStream()));
			socketOut = new PrintWriter((dateSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Communicates with the client
	 */
	public void communicate() {
		String line = "";
		String response = "";
		boolean running = true;
		while (running) {
			try {
				System.out.println("Please select an option (DATE/TIME)");
				line = stdIn.readLine();
				if (!line.toUpperCase().equals("QUIT")){
					socketOut.println(line.toUpperCase());
					response = socketIn.readLine();
					System.out.println(response);	
				}else{
					socketOut.println(line.toUpperCase());
					running = false;
				}
				
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
	}
		

	/**
	 * Run the Client
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		DateClient dc = new DateClient("localhost", 9090);
		dc.communicate();

	}

}
