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
	 * X Player, O Player 
	 */
	private Player xPlayer, oPlayer;
	
	/**
	 * Writing to client socket
	 */
	private PrintWriter xOut, oOut;
	
	/**
	 * Reading from client socket
	 */
	private BufferedReader xIn, oIn;
	
	/**
	 * Construct a Game with a tic-tae-toe Board.
	 * @param sockets array consisting of 2 client Sockets
	 */
    public Game(Socket[] sockets) {
        theBoard  = new Board();      
		try {
			xOut = new PrintWriter(sockets[0].getOutputStream(), true);
			xIn = new BufferedReader(new InputStreamReader(sockets[0].getInputStream()));
			oOut = new PrintWriter(sockets[1].getOutputStream(), true);
	    	oIn = new BufferedReader(new InputStreamReader(sockets[1].getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    
    /**
     * Appoint given referee to game and runs the game.
     * @param r Referee object.
     */
    public void appointReferee(Referee r) {
    	try {
            theRef = r;
            theRef.runTheGame();

            // shift responsibility of Referee and Player to Game for connecting with client server
    		Player currentPlayer = xPlayer; 
    		while ((theBoard.xWins() == false 
    				&& theBoard.oWins() == false
    				&& theBoard.isFull() == false)) {
    			
    			makeMove(currentPlayer);
        		
    			currentPlayer = currentPlayer.getOpponent();
    		}

    		xOut.println("THE GAME IS OVER: ");
    		xOut.println(theBoard.display());
    		oOut.println("THE GAME IS OVER: ");
    		oOut.println(theBoard.display());

    		if (theBoard.xWins() == true) {
    			String xName = xPlayer.getName();
    			xOut.printf("%s is the winner!\n", xName);
    			oOut.printf("%s is the winner!\n", xName);
    		} else if (theBoard.oWins() == true){
    			String oName = oPlayer.getName();
    			xOut.printf("%s is the winner!\n", oName);
    			oOut.printf("%s is the winner!\n", oName);
    		} else {
    			xOut.println("The game ended in a tie.");
    			oOut.println("The game ended in a tie.");
    		}
    		xOut.println("QUIT");
    		oOut.println("QUIT");
    		
    		xOut.close();
    		oOut.close();
    		xIn.close();
    		oIn.close();
    		
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    /**
	 * Asks given player to make a move by entering the row and column, and add the player's
	 * mark on the board. Check that the input is within the board boundary and in an empty 
	 * cell.
     * @param currentPlayer the current Player
     */
    private void makeMove(Player currentPlayer) {
    	try {
	    	BufferedReader in;
	    	PrintWriter out;
	    	if (currentPlayer == xPlayer) {
	    		in = xIn;
	    		out = xOut;
	    	} else {
	    		in = oIn;
	    		out = oOut;
	    	}
	    	
			String name = currentPlayer.getName();
			char mark = currentPlayer.getMark();
			
			out.println(name + ", it is your turn to make a move");
			out.println(theBoard.display());
			
			out.printf("%s, what row should your next %c be placed in (enter 0, 1, or 2)?\n",
					name, mark);
	 		out.println("EOF");
	 		
			String srow = in.readLine();
			while (! srow.matches("[0-2]")) {
				out.println("Invalid input, please try again.");
		 		out.println("EOF");
				srow = in.readLine();
			}
			int row = Integer.parseInt(srow);
			
			out.printf("%s, what col should your next %c be placed in (enter 0, 1, or 2)?\n",
					name, mark);
	 		out.println("EOF");
			String scol = in.readLine();
			while (! scol.matches("[0-2]")) {
				out.println("Invalid input, please try again.");
		 		out.println("EOF");
				scol = in.readLine();
			}
			int col = Integer.parseInt(scol);
			
			if (theBoard.getMark(row, col) != SPACE_CHAR) {
				makeMove(currentPlayer);
			} else {
				theBoard.addMark(row, col, mark);
			}
			
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    }
    
	@Override
	public void run() {
    	try{  	    				
    		// change return type of display() from void to String
    		xOut.println(theBoard.display());
    		oOut.println(theBoard.display());
    		
    		xOut.println("Message: WELCOME TO THE GAME.\nGet the name of the X player: ");
     		xOut.println("EOF");
			String xName = xIn.readLine();
			xOut.println("Message: Waiting for opponent to connect");
			xPlayer = new Player(xName, LETTER_X);
			
    		oOut.println("Message: WELCOME TO THE GAME.\nGet the name of the O player: ");
     		oOut.println("EOF");
    		String oName = oIn.readLine();
			oPlayer = new Player(oName, LETTER_O);
				
			// initialize Referee; appoint referee to game and runs game
			Referee theRef = new Referee();
			theRef.setoPlayer(oPlayer);
			theRef.setxPlayer(xPlayer);       
			appointReferee(theRef);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}			
	
	}

}
