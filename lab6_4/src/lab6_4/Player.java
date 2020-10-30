package lab6_4;

import java.io.*;

/**
 * Store information about the player including name, opponent, marking ('X' or 'O') 
 * and the board. Also have method for player to 
 * make a move and check the status of the game.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since Sep 28, 2020
 *
 */
public class Player implements Constants{
	/**
	 * Name of the player
	 */
	private String name;
	
	/**
	 * The board on which the game is played
	 */
	private Board board;
	
	/**
	 * The opponent of the player
	 */
	private Player opponent;
	
	/**
	 * Marking of the player ('X' or 'O')
	 */
	private char mark;
	
	/**
	 * Construct a Player object with given name and mark
	 * @param name Player's name
	 * @param mark Mark of a Player ('X' or 'O')
	 */
	public Player (String name, char mark) {
		this.name = name;
		this.mark = mark;
	}
	
	/**
	 * Check if the game has ended, if so, print the winner of the game, otherwise
	 * allows the player to make a move.
	 */
	public void play() {
		if (board.xWins() == false 
				&& board.oWins() == false
				&& board.isFull() == false) {
			makeMove();
			board.display();
			opponent.play();
		} else {
			System.out.print("THE GAME IS OVER: ");
			if (board.xWins() == true) {
				if (mark == LETTER_X) {
					System.out.printf("%s is the winner!\n", getName());
				} else {
					System.out.printf("%s is the winner!\n", opponent.getName());
				}				
			} else if (board.oWins() == true){
				if (mark == LETTER_O) {
					System.out.printf("%s is the winner!\n", getName());
				} else {
					System.out.printf("%s is the winner!\n", opponent.getName());
				}	
			} else {
				System.out.println("The game ended in a tie.");
			}
		}
	}
	
	/**
	 * Asks the player (user) to make a move by entering the row and column, and add the player's
	 * mark on the board. Check that the input is within the board boundary and in an empty cell.
	 */
	public void makeMove() {
	// this method is a helper method for the method play() 
    // and should be made private
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			System.out.printf("\n%s, what row should your next %c be placed in (enter 0, 1, or 2)? ",
					name, mark);
			int row = Integer.parseInt(input.readLine());
			while (row < 0 || row > 2) {
				System.out.print("Invalid input, please try again. ");
				row = Integer.parseInt(input.readLine());
			}
			System.out.printf("\n%s, what column should your next %c be placed in (enter 0, 1, or 2)? ",
					name, mark);
			int col = Integer.parseInt(input.readLine());
			while (col < 0 || col > 2) {
				System.out.print("Invalid input, please try again. ");
				col = Integer.parseInt(input.readLine());
			}
			if (board.getMark(row, col) != SPACE_CHAR) {
				System.out.println("Invalid move, please try again. ");
				makeMove();
			} else {
				board.addMark(row, col, mark);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Return the name of this player.
	 * @return Player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set given player as the opponent for this Player
	 * @param player the opponent of this player
	 */
	public void setOpponent(Player player) {
		opponent = player;
	}
	
	/**
	 * Set the given board as the board for the game
	 * @param board a 3x3 tic-tac-toe board used for the game
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
}
