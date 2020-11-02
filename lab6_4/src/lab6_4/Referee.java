package lab6_4;

/**
 * Provide data fields and methods to runs a game of tic-tac-toe. The
 * Referee object sets the opponents of 'X' and 'O' players, and starts
 * the game by displaying the board and calling X player to start.
 * 
 * @author Tong xu
 * @version 1.0
 * @since Sep 28, 2020
 *
 */
public class Referee {
	/**
	 * Player 'X' and 'O'
	 */
	// attribute oPlayer is not used by class Referee and could have been 
	// omitted
	private Player xPlayer, oPlayer;

	/**
	 * Construct a Referee object.
	 */
	public Referee () {
	// constructor is the default constructor and could have been omitted
	}
	
	/**
	 * Set the opponents of 'X' and 'O' player, and initiate the game by 
	 * displaying the board, and calling player 'X' to start the game.
	 */
	public void runTheGame() {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
	}
	
	/**
	 * Set the given player as Player 'O'
	 * @param oPlayer Player 'O'
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	
	/**
	 * Set the given player as Player 'X'
	 * @param xPlayer Player 'X'
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}
}
