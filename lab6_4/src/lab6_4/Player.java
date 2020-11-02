package lab6_4;

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
	 * Return the name of this player.
	 * @return Player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the marking of this player.
	 * @return Player's marking
	 */
	public char getMark() {
		return mark;
	}
	
	/**
	 * Return the opponent.
	 * @return opponent
	 */
	public Player getOpponent() {
		return opponent;
	}
	
	/**
	 * Set given player as the opponent for this Player
	 * @param player the opponent of this player
	 */
	public void setOpponent(Player player) {
		opponent = player;
	}


}
