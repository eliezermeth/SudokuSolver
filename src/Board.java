import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Holds a sudoku board.
 *
 * @author Eliezer Meth
 * @version 1
 * Start Date: 2022-05-15
 * Last Modified: 2022-05-16
 */
public class Board
{
    private int[][] board;
    private int size; // n x n
	// primary use when boxes are not squares //TODO make it that can accept non-square sudoku
	private int boxSizeRows;
	private int boxSizeCols;
	
	private boolean validBoard = true; // flipped when a number is inserted into the wrong place; considers game invalid if done once
	private boolean won = false;
	private boolean lost = false;
	
	private int openSquares = 0; // counts how many open squares are remaining in board

    /**
     * Creates class by taking in string of numbers of the board and changing it to an array.
     * @param inputBoard string of board.
     */
    public Board(String inputBoard)
    {
        double sqrt = Math.sqrt(inputBoard.length());
		int[] temp = new int[inputBoard.length()];

        // error check
        if (sqrt != (int) sqrt || inputBoard.length() == 0)
            throw new InputMismatchException("Invalid length of string for board");
		
        try { //TODO change to allow greater than 9x9 boards
			for (int i = 0; i < inputBoard.length(); i++)
			{
				int num = Integer.parseInt(Character.toString(inputBoard.charAt(i)));
				if (num == 0) openSquares++; // increment the number of open squares
				temp[i] = num;
			}
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Invalid characters in numeric board");
        }
		
		// create board
		size = (int) sqrt;
		board = new int[size][size];
		boxSizeRows = (int) Math.sqrt(size);
		boxSizeCols = (int) Math.sqrt(size);
		for (int i = 0; i < size; i++)
		{
			board[i] = Arrays.copyOfRange(temp, i * size, (i + 1) * size); // create arrays by length of size
		}
    }
	
	/**
	 * Insert a number at a specified index.  Does not permit number to be placed over existing number, but allows numbers to be placed incorrectly.
	 * @param row row to add it.
	 * @param col column to add it.
	 * @param numberToAdd number to add to the board.
	 * @return true if number is added successfully.
	 */
	public boolean add(int row, int col, int numberToAdd)
	{
		// test between 1 and size
		if (numberToAdd <= 0 || numberToAdd > size)
			return false;
		// test that cell is not occupied
		if (board[row][col] != 0)
			return false;
		
		// test row, column, and box to see if board will still be valid when added
		if (!testRowInsert(row, numberToAdd) || !testColumnInsert(col, numberToAdd) ||
				!testBoxInsert(row / boxSizeRows, col / boxSizeCols, numberToAdd)) // if failed
		{
			validBoard = false;
		}

		// add to board
		board[row][col] = numberToAdd;
		openSquares--;

		isGameOver();

		return true;
	}
	
	/**
	 * Test if a number can be inserted in a specific row and still have a valid board.
	 * @param row row to add it.
	 * @param num number to add to the board.
	 * @return true if board is valid after adding.
	 */
	private boolean testRowInsert(int row, int num)
	{
		for (int i = 0; i < size; i++)
			if (board[row][i] == num)
				return false;
		
		return true;
	}

	/**
	 * Test if a number can be inserted in a specific column and still have a valid board.
	 * @param column column to add it.
	 * @param num number to add to the board.
	 * @return true if board is valid after adding.
	 */
	private boolean testColumnInsert(int column, int num)
	{
		for (int i = 0; i < size; i++)
			if (board[i][column] == num)
				return false;
			
		return true;
	}

	/**
	 * Test if a number can be inserted in a specific box and still have a valid board.
	 * @param boxRow row of box to add it.
	 * @param boxColumn column of box to add it.
	 * @param num number to add to the board.
	 * @return true if board is valid after adding.
	 */
	private boolean testBoxInsert(int boxRow, int boxColumn, int num)
	{
		for (int i = boxRow * boxSizeRows; i < (boxRow + 1) * boxSizeRows; i++)
			for (int j = boxColumn * boxSizeCols; j < (boxColumn + 1) * boxSizeCols; j++)
				if (board[i][j] == num)
					return false;
				
		return true;
	}

	/**
	 * Check if game is over.  Weak check in that an invalid board causes game loss, while it should only be when no valid moves remain.
	 * @return true if game is over.
	 */
	public boolean isGameOver()
	{
		if (openSquares == 0 && validBoard)
		{
			won = true;
			return true;
		}
		else if (!validBoard) // should be changed so that only says game over if no allowed moves remaining
		{
			lost = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Return if game is won.
	 * @return true if won.
	 */
	public boolean isWon()
	{
		return won;
	}

	/**
	 * Return if game is lost.
	 * @return true if lost.
	 */
	public boolean isLost()
	{
		return lost;
	}

	/*
	private boolean tally()
	{
		int[] tally = new int[10]; // 0 for empty, then numbers into index
		
		for (int i = 0; i < size; i++)
		{
			//tally[board[row][i]
		}
		return true;
	}
	 */
}
