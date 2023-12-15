package pieces;

import java.util.ArrayList;

import gui.Board;
import gui.Tile;

public class Knight extends Piece {

	/**constructor
	 * @param alliance
	 * @param board
	 * @param row
	 * @param col
	 */
	public Knight(Alliance alliance, Board board, int row, int col) {
		super(alliance, board, row, col);

	}

	@Override
	public ArrayList<Tile> calculateLegalMoves() {
		moves = new ArrayList<>();
		if (row != board.BOARD_SIZE + 1) {
			for (int i = -2; i < 3; i++) {
				for (int j = -2; j < 3; j++) {
					if (Math.abs(i) + Math.abs(j) == 3) {
						if (withinRange(row + i, col + j)) {
							if (!isPieceAt(row + i, col + j)) {
								addMove(row + i, col + j);
							}
							else if (getAllianceAt(row + i, col + j) != alliance) {
								addMove(row + i, col + j);
							}
						}
					}
				}
			}
		}

		return moves;
	}

}
