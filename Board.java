//Anthony Xiang
//112166670

package finalprojGUI;

public class Board {

	Tile[][] arr;

	// Initializes the Board
	public Board() {
		arr = new Tile[16][16];

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				arr[i][j] = new Tile("x");
			}
		}
	}

	// Get the hex string of the number
	public static String getHexStr(int num) {
		return Integer.toHexString(num).toUpperCase();
	}

	// Get the number of the hex string
	public static int getHexNum(String s) {
		return Integer.parseInt(s.toLowerCase(), 16);
	}

	// Tests if a specific Tile NOT YET PLACED is legal - by row, column, grid
	public boolean isLegal(int r, int c, Tile t) {
		int rowStartUnit = (r / 4) * 4;
		int colStartUnit = (c / 4) * 4;

		for (int i = 0; i < 16; i++)
			if (t.getLetter().equals(arr[i][c].getLetter())) 
				return false;

		for (int j = 0; j < 16; j++)
			if (t.getLetter().equals(arr[r][j].getLetter()))
				return false;

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (t.getLetter().equals(arr[rowStartUnit + i][colStartUnit + j].getLetter()))
					return false;

		return true;
	}

	// Tests if there are duplicate numbers on the current board
	public boolean isDuplicate() {
		for (int i = 0;i<16;i++) {
			for (int j =0;j<16;j++) {
				if(!arr[i][j].letter.equals("x")) {
					String s = arr[i][j].letter;
					arr[i][j].setLetter("x");

					if (!isLegal(i,j,new Tile(s))) {
						arr[i][j].setLetter(s);
						return true;
					}
					else
						arr[i][j].setLetter(s);
				}
			}
		}
		return false;
	}

	// Test if current board is legal to be solved
	public boolean isBoardLegal() {
		for (int i = 0;i<16;i++) {
			for (int j =0;j<16;j++) {
				if ((arr[i][j].letter.charAt(0)) != 'x')
					if ((int) (arr[i][j].letter.charAt(0)) < 48 || (int) (arr[i][j].letter.charAt(0)) > 70
							|| (((int) (arr[i][j].letter.charAt(0)) < 65 && (int) (arr[i][j].letter.charAt(0)) > 57)))
						return false;

				if (arr[i][j].letter.length() != 1)
					return false;

				if (isDuplicate())
					return false;
			}
		}
		return true;
	}

	// Backtracking algorithm to fill the board
	public boolean solve(int r, int c) {
		if (r == 16) {
			r = 0;

			if (++c == 16)
				return true;
		}

		if (!arr[r][c].getLetter().equals("x")) {
			return solve(r + 1, c); 		// Go to the next if it's filled
		}

		for (int a = 0; a <= 15; ++a) {
			if (isLegal(r, c, new Tile(getHexStr(a)))) {

				arr[r][c].setLetter(getHexStr(a));
				if (solve(r + 1, c))  		// Move to the next number
					return true;
			}
		}

		arr[r][c].setLetter("x");
		return false;
	}
}
