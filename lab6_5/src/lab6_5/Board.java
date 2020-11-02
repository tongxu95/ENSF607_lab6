package lab6_5;

import java.util.Arrays;

/**
 * Provide data fields and methods to create and display a 3x3 
 * tic-tac-toe board and check the winner.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since Sep 28, 2020
 *
 */
public class Board implements Constants {
	/**
	 * 3x3 tic-tac-toe board
	 */
	private char theBoard[][];
	
	/**
	 * Number of moves (maximum is 9)
	 */
	private int markCount;

	/**
	 * Construct a 3x3 tic-tac-toe board and initialize the board 
	 * with spaces.
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Get the marking for a given cell on the board.
	 * @param row the row on the board (0, 1, 2)
	 * @param col the column on the board (0, 1, 2)
	 * @return the marking for the given cell
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * Check if the board is full (players have made 9 moves 
	 * together) 
	 * @return true if board is full and false otherwise
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Check if 'X' wins the game.
	 * @return true if 'X' wins and false otherwise
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Check if 'O' wins the game.
	 * @return true if 'O' wins and false otherwise
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Display the tic-tac-toe board. 
	 */
	public String display() {
		StringBuilder boardState = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			boardState.append("ROW " + i + ":");
			for (int j = 0; j < 3; j++) {
				boardState.append(getMark(i,j) + ",");
			}
			boardState.append("\n");
		}
		return boardState.toString();

	}

	/**
	 * Add marking to a given cell in the tic-tac-toe board.
	 * @param row the row on the board (0, 1, 2)
	 * @param col the column on the board (0, 1, 2)
	 * @param mark marking ('O' or 'X')
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * Clear board of markings.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * Check if given mark wins the game.
	 * @param mark marking ('O' or 'X')
	 * @return 1 if given mark wins the game, and 0 otherwise
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

}
