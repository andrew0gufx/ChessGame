package pieces;

import java.util.ArrayList;

import gameplay.Player;
import gui.Board;
import gui.Tile;

/**
 * This class is to set up the pieces, assign color, position
 * Check the legal moves, player or enemy player
 * @author GuFX
 *
 */
public abstract class Piece {

	protected Alliance alliance;
	protected int row;
	protected int col;
	protected Board board;
	protected ArrayList<Tile> moves;
	public static boolean isKingInCheckRunning = false;
	protected Player myPlayer;
	protected Player enemyPlayer;

	protected ArrayList<Tile> positionHistory;

	/**
	 * construct the piece with default color, position
	 * @param alliance color
	 * @param board chess board
	 * @param row row
	 * @param col column
	 */
	public Piece(Alliance alliance, Board board, int row, int col) {
		positionHistory = new ArrayList<>();
		this.alliance = alliance;
		this.board = board;
		this.row = row;
		this.col = col;
		updatePosition();
	}

	/**
	 * get the previous position
	 * @return last position
	 */
	public ArrayList<Tile> getPreviousPositions() {
		return positionHistory;
	}

	/**
	 * set the row
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;

	}

	/**
	 * set the column
	 * @param col
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * get the row
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * get the column
	 * @return
	 */
	public int getCol() {
		return col;
	}

	/**
	 * set the previous position
	 * @param previousPositions previous position
	 */
	public void setPreviousPositions(ArrayList<Tile> previousPositions) {
		this.positionHistory = previousPositions;
	}

	/**
	 * get the user's player
	 * @return own player
	 */
	public Player getMyPlayer() {
		return myPlayer;
	}

	/**
	 * set the own player
	 * @param myPlayer
	 */
	public void setMyPlayer(Player myPlayer) {

		this.myPlayer = myPlayer;
	}

	/**
	 * get enemy's player
	 * @return
	 */
	public Player getEnemyPlayer() {
		return enemyPlayer;
	}

	/**
	 * set enemy's player
	 * @param enemyPlayer
	 */
	public void setEnemyPlayer(Player enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}

	/**
	 * calculate the legal moves
	 * @return the number of the legal moves
	 */
	public abstract ArrayList<Tile> calculateLegalMoves();

	/**
	 * get the board
	 * @return chess board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * get the tile's position
	 * @param x row
	 * @param y column
	 * @return position of the tile
	 */
	public Tile getTileAt(int x, int y) {
		return this.getBoard().getChessboard()[x][y];
	}

	/**
	 * get the alliance(black or white)
	 * @return alliance
	 */
	public Alliance getAlliance() {
		return alliance;
	}

	/**
	 * check the position of the piece
	 * @param row
	 * @param col
	 * @return true if piece is at that position, false then not
	 */
	public boolean isPieceAt(int row, int col) {
		return this.board.getChessboard()[row][col].isPiece();
	}

	/**
	 * get the position of the piece
	 * @param row
	 * @param col
	 * @return position of the piece
	 */
	public Piece getPieceAt(int row, int col) {
		return this.board.getChessboard()[row][col].getPiece();
	}

	/**
	 * get the color of the piece at specific position
	 * @param row
	 * @param col
	 * @return position and color of the piece
	 */
	public Alliance getAllianceAt(int row, int col) {
		return this.board.getChessboard()[row][col].getPiece().getAlliance();
	}

	// checks if a coordinate is on the board (used for calculating possible moves)
	protected boolean withinRange(int x, int y) {
		if (x >= 0 && x < board.BOARD_SIZE && y >= 0 && y < board.BOARD_SIZE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * set up the rook's move
	 * @param x
	 * @param y
	 */
	protected void rookMoves(int x, int y) {

		for (int k = 1; k < board.BOARD_SIZE; k++) {

			if (withinRange(row + k * x, col + k * y)) {

				if (!isPieceAt(row + k * x, col + k * y)) {
					addMove(row + k * x, col + k * y);
				} else if (getAllianceAt(row + k * x, col + k * y) != alliance) {
					addMove(row + k * x, col + k * y);
					break;
				} else {
					break;
				}

			}
		}

	}

	/**
	 * check the status is in check or not
	 * @param proposedRow
	 * @param proposedCol
	 * @return true - in check, false - not in check
	 */
	public boolean isKingInCheck(int proposedRow, int proposedCol) {
		if (myPlayer != null) {

			// isKingInCheckRunning is to avoid an infinite sequence of pieces running this
			// method!
			isKingInCheckRunning = true;

			int currentRow = this.row;
			int currentCol = this.col;

			Piece piece = null;
			if (isPieceAt(proposedRow, proposedCol)) {
				piece = getPieceAt(proposedRow, proposedCol);

				piece.setRow(board.BOARD_SIZE + 1);
				piece.setCol(board.BOARD_SIZE + 1);

			}

			// move piece to proposed position
			this.row = proposedRow;
			this.col = proposedCol;
			board.getChessboard()[proposedRow][proposedCol].setPiece(this);
			board.getChessboard()[currentRow][currentCol].clearPiece();

			// for every enemy piece
			for (Piece enemyPiece : enemyPlayer.getPieces()) {

				// if they could take the King
				if (enemyPiece.getRow() != board.BOARD_SIZE + 1) {
					if (enemyPiece.calculateLegalMoves().contains(
							board.getChessboard()[myPlayer.getMyKing().getRow()][myPlayer.getMyKing().getCol()])) {

						// change the piece back
						this.row = currentRow;
						this.col = currentCol;
						getBoard().getChessboard()[this.row][this.col].setPiece(this);
						getBoard().getChessboard()[proposedRow][proposedCol].clearPiece();
						if (piece != null) {
							getBoard().getChessboard()[proposedRow][proposedCol].setPiece(piece);
							piece.setRow(proposedRow);
							piece.setCol(proposedRow);
						}
						isKingInCheckRunning = false;

						return true;

					}
				}

			}
			// change the piece back
			this.row = currentRow;
			this.col = currentCol;
			getBoard().getChessboard()[this.row][this.col].setPiece(this);
			getBoard().getChessboard()[proposedRow][proposedCol].clearPiece();
			if (piece != null) {
				getBoard().getChessboard()[proposedRow][proposedCol].setPiece(piece);
			}
			isKingInCheckRunning = false;

			return false;
		}
		return false;
	}

	/**
	 * add move if is not in check
	 * @param x
	 * @param y
	 */
	public void addMove(int x, int y) {
		if (withinRange(x, y)) {
			if (!isKingInCheckRunning) {
				if (!isKingInCheck(x, y)) {
					moves.add(getTileAt(x, y));
				}
			}
			else {
				moves.add(getTileAt(x, y));
			}
		}
	}

	/**
	 * update the position, change the tile
	 */
	public void updatePosition() {
		if (row != board.BOARD_SIZE + 1) {
			positionHistory.add(board.getChessboard()[row][col]);
		}
		else {
			positionHistory.add(new Tile(row, col));
		}
	}

}
