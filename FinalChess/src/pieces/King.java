package pieces;

import java.util.ArrayList;

import gui.Board;
import gui.Tile;

public class King extends Piece {

	/**constructor
	 * @param alliance
	 * @param board
	 * @param row
	 * @param col
	 */
	public King(Alliance alliance, Board board, int row, int col) {
		super(alliance, board, row, col);
	}

	@Override
	public ArrayList<Tile> calculateLegalMoves() {
		moves = new ArrayList<>();

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {

				if (withinRange(row + i, col + j)) {
					if (i != 0 || j != 0) {
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

		return moves;
	}

}
