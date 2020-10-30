package lab6_4;

import java.io.*;

/**
 * Read from the user the name of 'X' player, 'O' player, and appoint a referee to
 * start the game.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since Sep 28, 2020
 * 
 */
public class Game implements Constants {
	/**
	 * 3x3 tic-tac-toe board
	 */
	private Board theBoard;
	
	/**
	 * referee for the game
	 */
	private Referee theRef;
	
	/**
	 * Construct a Game with a tic-tae-toe Board.
	 */
    public Game( ) {
        theBoard  = new Board();
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
	
	public static void main(String[] args) throws IOException {
		Referee theRef;
		Player xPlayer, oPlayer;
		BufferedReader stdin;
		Game theGame = new Game();
		stdin = new BufferedReader(new InputStreamReader(System.in));
		
		// get the name of Player 'X' from user and initialize Player 'X'
		System.out.print("\nPlease enter the name of the \'X\' player: ");
		String name= stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}

		xPlayer = new Player(name, LETTER_X);
		xPlayer.setBoard(theGame.theBoard);
		
		// get the name of Player 'O' from user and initialize Player 'O'
		System.out.print("\nPlease enter the name of the \'O\' player: ");
		name = stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}
		
		oPlayer = new Player(name, LETTER_O);
		oPlayer.setBoard(theGame.theBoard);
		
		// initialize Referee; appoint referee to game and runs game
		theRef = new Referee();
		theRef.setBoard(theGame.theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
        
		theGame.appointReferee(theRef);
	}
}
