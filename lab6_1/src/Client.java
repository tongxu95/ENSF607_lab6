import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private PrintWriter socketOut; //write to server
	private Socket palinSocket; //handle IO for server
	private BufferedReader stdIn; //read from user input
	private BufferedReader socketIn; //read from server

	public Client(String serverName, int portNumber) {
		try {
			palinSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public void communicate()  {

		String line = "";
		String response = "";
		boolean running = true;
		while (running) {
			try {
				System.out.println("please enter a word: ");
				line = stdIn.readLine();
				if (!line.equals("QUIT")){
					System.out.println(line);
					socketOut.println(line);
					response = socketIn.readLine();
					System.out.println(response);	
				}else{
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

	public static void main(String[] args) throws IOException  {
		Client aClient = new Client("localhost", 8099);
		aClient.communicate();
	}
}