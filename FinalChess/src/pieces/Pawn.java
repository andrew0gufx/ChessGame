package pieces;

import java.util.ArrayList;

import gui.Board;
import gui.Tile;

public class Pawn extends Piece {

	/**constructor
	 * @param alliance
	 * @param board
	 * @param row
	 * @param col
	 */
	public Pawn(Alliance alliance, Board board, int row, int col) {
		super(alliance, board, row, col);

	}

	@Override
	public ArrayList<Tile> calculateLegalMoves() {
		if (row != board.BOARD_SIZE + 1) {
			moves = new ArrayList<>();

			if (this.alliance == Alliance.WHITE) {
				if (withinRange(row - 1, col) && !isPieceAt(row - 1, col)) {
					addMove(row - 1, col);
				}
				if (row == Board.BOARD_SIZE - 2 && !isPieceAt(row - 2, col)) {
					addMove(row - 2, col);
				}

				for (int i : new int[] { -1, 1 }) {
					if (withinRange(row - 1, col + i) && isPieceAt(row - 1, col + i)
							&& getAllianceAt(row - 1, col + i) != this.alliance) {
						addMove(row - 1, col + i);
					}
				}
			}

			else {
				if (withinRange(row + 1, col) && !isPieceAt(row + 1, col)) {
					addMove(row + 1, col);
				}
				if (row == 1 && !isPieceAt(row + 2, col)) {
					addMove(row + 2, col);
				}

				for (int i : new int[] { -1, 1 }) {
					if (withinRange(row + 1, col + i) && isPieceAt(row + 1, col + i)
							&& getAllianceAt(row + 1, col + i) != this.alliance) {
						addMove(row + 1, col + i);
					}
				}
			}
		}
		return moves;

	}
}
