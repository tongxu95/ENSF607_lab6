package lab6_4;

import java.io.*;
import java.net.Socket;

/**
 * Read from the user the name of 'X' player, 'O' player, and appoint a referee to
 * start the game.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since Sep 28, 2020
 * 
 */
public class Game implements Constants, Runnable {
	/**
	 * 3x3 tic-tac-toe board
	 */
	private Board theBoard;
	
	/**
	 * referee for the game
	 */
	private Referee theRef;
	
	/**
	 * array consisting of two client sockets for X player and O player
	 */
	private Socket[] sockets;
	
	/**
	 * Construct a Game with a tic-tae-toe Board.
	 * @param player array consisting of 2 client Sockets
	 */
    public Game(Socket[] players) {
        theBoard  = new Board();      
        sockets = players;
	}
    
    /**
     * Appoint given referee to game and runs the game.
     * @param r Referee object.
     * @throws IOException can throw IOException
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
        System.out.println("\nReferee started the game...\n");
        theRef.runTheGame();
    }

	@Override
	public void run() {
		
    	try(
			PrintWriter xOut = new PrintWriter(sockets[0].getOutputStream(), true);
			BufferedReader xIn = new BufferedReader(new InputStreamReader(sockets[0].getInputStream()));
    		PrintWriter oOut = new PrintWriter(sockets[1].getOutputStream(), true);
    		BufferedReader oIn = new BufferedReader(new InputStreamReader(sockets[1].getInputStream()))
		){
    		// change return type of display() from void to String
    		xOut.println(theBoard.display());
    		oOut.println(theBoard.display());
    		
    		xOut.print("Message: WELCOME TO THE GAME.\nGet the name of the X player: ");
			String xName = xIn.readLine();
			xOut.print("Message: Waiting for opponent to connect");
			Player xPlayer = new Player(xName, LETTER_X);
			xPlayer.setBoard(theBoard);
			
    		oOut.print("Message: WELCOME TO THE GAME.\nGet the name of the O player: ");
			String oName = xIn.readLine();
			Player oPlayer = new Player(oName, LETTER_O);
			oPlayer.setBoard(theBoard);
				
			// initialize Referee; appoint referee to game and runs game
			Referee theRef = new Referee();
			theRef.setBoard(theBoard);
			theRef.setoPlayer(oPlayer);
			theRef.setxPlayer(xPlayer);       
			appointReferee(theRef);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}			
	
	}

}
