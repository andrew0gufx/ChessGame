package pieces;

import java.util.ArrayList;

import gui.Board;
import gui.Tile;

public class Rook extends Piece {

	/**Constructor
	 * @param alliance
	 * @param board
	 * @param row
	 * @param col
	 */
	public Rook(Alliance alliance, Board board, int row, int col) {
		super(alliance, board, row, col);
	}

	@Override
	public ArrayList<Tile> calculateLegalMoves() {
		moves = new ArrayList<>();
		if (row != board.BOARD_SIZE + 1) {
			for (int i : new int[] { 1, -1 }) {

				// rows
				rookMoves(i, 0);

				// columns

				rookMoves(0, i);

			}
		}
		return moves;
	}

}
