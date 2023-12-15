package pieces;

import java.util.ArrayList;

import gui.Board;
import gui.Tile;

public class Queen extends Piece {

	/**Constructor
	 * @param alliance
	 * @param board
	 * @param row
	 * @param col
	 */
	public Queen(Alliance alliance, Board board, int row, int col) {
		super(alliance, board, row, col);

	}

	@Override
	public ArrayList<Tile> calculateLegalMoves() {
		moves = new ArrayList<>();
		if (row != board.BOARD_SIZE + 1) {
			for (int i : new int[] { 1, -1 }) {
				for (int j : new int[] { 1, -1 }) {
					for (int k = 1; k < board.BOARD_SIZE; k++)
						if (withinRange(row + i * k, col + j * k)) {

							if (!isPieceAt(row + i * k, col + j * k)) {
								addMove(row + i * k, col + j * k);
							}
							else if (getAllianceAt(row + i * k, col + j * k) != alliance) {
								addMove(row + i * k, col + j * k);
								break;
							}
							else {
								break;
							}

						}
				}

			}

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
