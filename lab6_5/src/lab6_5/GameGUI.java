package lab6_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Create a GUI for tic-tac-toe game and connect socket to the tic-tac-toe server. 
 * 
 * @version 1.0
 * @author Tong Xu
 * @since Nov 1, 2020
 *
 */
public class GameGUI extends JFrame implements ActionListener {
    
	/** Socket for connecting to server. */
	private Socket socket;
	
	/**
	 * Reading from server socket
	 */
	private BufferedReader socketIn;
	
	/**
	 * Player marking
	 */
	private String marking;
	
	/**
	 * Writing to server socket
	 */
	private PrintWriter socketOut; 
	
	private boolean makeMove;
	
	/**
	 * Displays the game messages
	 */
	private JTextArea display;
	
	/**
	 * input text box for player to enter their name
	 */
	private JTextField inField;
	
	/**
	 * Displays Player mark
	 */
	private JTextArea mark;
	
	/**
	 * Create a 3x3 tic-tac-toe board on GUI
	 */
	private JButton[][] board = new JButton[3][3];
	
	/**
	 * Create client socket; create and display GUI for playing a tic-tac-toe game.
	 */
	public GameGUI ( ) {
		
		try {
			
			JPanel p1 = new JPanel(new GridLayout(3, 3));
			
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					board[i][j] = new JButton(" ");
					board[i][j].addActionListener(this);
					p1.add(board[i][j], i, j);
				}
			}
			
			JLabel label_display = new JLabel("Message Window:");

			display = new JTextArea(20, 50);
//			JScrollPane scrollpane = new JScrollPane(inField);
//			scrollpane.getViewport().setPreferredSize(new Dimension(400, 400));

			JPanel p2 = new JPanel();
			p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
			p2.add(label_display);
			p2.add(new JScrollPane(display));
			
			JPanel p3 = new JPanel();
			p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
			p3.add(p1);
			p3.add(p2);
			
			JLabel label_player = new JLabel("User Name:    ");
			inField = new JTextField(10);
			inField.addActionListener(this);
			JPanel p4 = new JPanel();
			p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
			p4.add(label_player);
			p4.add(inField);
			
			JLabel label_mark = new JLabel("Player:    ");
			mark = new JTextArea(1, 1);
			
			JPanel p5 = new JPanel();
			p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
			p5.add(label_mark);
			p5.add(mark);

			Container contentPane = getContentPane();
			contentPane.setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();
			gc.insets = new Insets(2, 2, 2, 2); 
			
			gc.gridy = 0;
			contentPane.add(p3, gc);
			
			gc.gridy = 1;
			contentPane.add(p5, gc);
			
			gc.gridy = 2;
			contentPane.add(p4, gc); 
			
			setTitle("Tic-Tac-Toe Game");
			pack();
			setVisible(true);
			
			makeMove = false;
			
			socket = new Socket ("localhost", 4444);
			socketIn = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			socketOut = new PrintWriter (socket.getOutputStream(), true);
			communicate();
			
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
		
	}
	
	/**
	 * Communicate with server socket.
	 */
	public void communicate () {
		try {
			boolean endGame = false;
			while(! endGame) {
				String line;
				while ((line = socketIn.readLine()) != null) {
					if (line.startsWith("MARK:")) {
						marking = line.substring(5);
						mark.append(marking);
						break;
					}
					if (line.startsWith("ROW")) {
						int row = Integer.parseInt(line.substring(4, 5));
						String[] rowState = line.substring(6).split(",");
						for (int col = 0; col < 3; col++) {
							board[row][col].setText(rowState[col]);
						}
						break;
					}
					if (line.equals("MAKEMOVE")) {
						makeMove = true;
						break;
					}
					if (line.equals("QUIT")) {
						endGame = true;
						break;
					}
					display.append(line + "\n");
				}				
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// user name
		if (e.getSource() == inField) {
			socketOut.println(inField.getText());
			
		// making a move on the board
		} else {
			if (makeMove == true) {
				JButton b = (JButton) e.getSource();
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (b == board[i][j] && board[i][j].getText().equals(" ")) {
							board[i][j].setText(marking);
							socketOut.println(i);
							socketOut.println(j);
							makeMove = false;
						}
					}
				}
			}

		}
		
	}
	
	public static void main(String[] args) {
		new GameGUI();
	}

	
}
