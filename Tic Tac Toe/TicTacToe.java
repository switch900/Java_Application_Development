import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToe {

	private char[][] board;
	private static char currentPlayerMark;
	static int totalWinsX = 0;
	static int totalWinsO = 0;
	static int totalTies = 0;
	static ArrayList<String> results;
	static String resultString = null;

	public TicTacToe() {
		board = new char[3][3];
		initializeBoard();

	}

	/** The entry main() method */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Input data is missing. Expecting customer data.");
			System.exit(-1);
		}

		results = new ArrayList<>();
		currentPlayerMark = 'X';
		int index = 1;
		int gamesToPlay = Integer.parseInt(args[0]);
		System.out.println("Playing the best of " + gamesToPlay);
		while (index <= gamesToPlay) {
			System.out.println();
			System.out.println("=== Game " + index);
			TicTacToe game = new TicTacToe();

			while (!game.checkForWin()) {
				int min = 0;
				int max = 2;

				int randomNum1 = ThreadLocalRandom.current().nextInt(min, max + 1);
				int randomNum2 = ThreadLocalRandom.current().nextInt(min, max + 1);

		
				if (!game.placeMark(randomNum1, randomNum2)) {
					System.out.println(randomNum1 + ", " + randomNum2 + " is already taken");
				}

				else {
					System.out.println(currentPlayerMark + " played " + randomNum1 + ", " + randomNum2);
					game.placeMark(randomNum1, randomNum2);
					game.printBoard();
					game.changePlayer();
				}
				if (game.isBoardFull()) {
					game.printBoard();
					System.out.println("It was a tie!");
					resultString = "game " + index + " was a tie";
					results.add(resultString);
					totalTies++;
					break;
				}
				
				else if (game.checkForWin()) {

					if (currentPlayerMark == 'X') {
						totalWinsX++;
						resultString = "game " + index + " was won by X";
						System.out.println(resultString);
						results.add(resultString);
					} else {
						totalWinsO++;
						resultString = "game " + index + " was won by O";
						results.add(resultString);
					}
					System.out.println("Player " + currentPlayerMark + " wins!");
					
				}			
			}		
			index++;	
		}
		System.out.println("=== Results for " + gamesToPlay + " games.");
		System.out.println("X won " + totalWinsX + " times");
		System.out.println("O won " + totalWinsO + " times");
		System.out.println("there were " + totalTies + " ties");
		for (String tempResult : results) {
			System.out.println(tempResult);
		}
	}

	// Set/Reset the board back to all empty values.
	public void initializeBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = '-';
			}
		}
	}

	// Print the current board (may be replaced by GUI implementation later)
	public void printBoard() {
		System.out.println("-------------");
		for (int i = 0; i < 3; i++) {
			System.out.print("| ");
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j] + " | ");
			}
			System.out.println();
			System.out.println("-------------");
		}
	}

	// Loop through all cells of the board and if one is found to be empty (contains
	// char '-') then return false.
	// Otherwise the board is full.
	public boolean isBoardFull() {
		boolean isFull = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '-') {
					isFull = false;
				}
			}
		}
		return isFull;
	}

	// Returns true if there is a win, false otherwise.
	// This calls our other win check functions to check the entire board.
	public boolean checkForWin() {
		return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
	}

	// Loop through rows and see if any are winners.
	private boolean checkRowsForWin() {
		for (int i = 0; i < 3; i++) {
			if (checkRowCol(board[i][0], board[i][1], board[i][2]) == true) {
				return true;
			}
		}
		return false;
	}

	// Loop through columns and see if any are winners.
	private boolean checkColumnsForWin() {
		for (int i = 0; i < 3; i++) {
			if (checkRowCol(board[0][i], board[1][i], board[2][i]) == true) {
				return true;
			}
		}
		return false;
	}

	// Check the two diagonals to see if either is a win. Return true if either
	// wins.
	private boolean checkDiagonalsForWin() {
		return ((checkRowCol(board[0][0], board[1][1], board[2][2]) == true)
				|| (checkRowCol(board[0][2], board[1][1], board[2][0]) == true));
	}

	// Check to see if all three values are the same (and not empty) indicating a
	// win.
	private boolean checkRowCol(char c1, char c2, char c3) {
		return ((c1 != '-') && (c1 == c2) && (c2 == c3));
	}

	// Change player marks back and forth.
	public void changePlayer() {
		if (currentPlayerMark == 'X') {
			currentPlayerMark = 'O';
		} else {
			currentPlayerMark = 'X';
		}
	}

	// Places a mark at the cell specified by row and col with the mark of the
	// current player.
	public boolean placeMark(int row, int col) {
		if ((row >= 0) && (row < 3)) {
			if ((col >= 0) && (col < 3)) {
				if (board[row][col] == '-') {
					board[row][col] = currentPlayerMark;
					return true;
				}
			}
		}
		return false;
	}
}
