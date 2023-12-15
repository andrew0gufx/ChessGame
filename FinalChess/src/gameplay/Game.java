package gameplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import gui.Board;
import gui.Gui;
import pieces.Alliance;
import pieces.Piece;
/**
 * This class builds a chess game using a GUI that allows you to select different game modes,
 * assign default values to the board size, and determine whether a player is playing black or white.
 * The ability to determine if the game is over by checking is in check,
 * and the ability to update the Status Panel by turn when the player finishes moving pieces.
 * @author GuFX
 *
 */
public class Game implements ActionListener {

	private Player whitePlayer;
	private Player blackPlayer;
	private ArrayList<Piece> allPieces;
	private Gui gui;

	private int newRow;
	private int newCol;

	private static Mode mode;
	public static Alliance turn;
	private static boolean isGameOver = false;
	private Board board;
	public int numOfMoves; // keeps track of the number of moves (in order to undo)

	// Game constructor takes a GUI, game mode, size of board and if players are
	// computers or humans

	public Game(Gui gui, Mode mode, int boardSize, boolean isWhiteComp, boolean isBlackComp) {

		Game.mode = mode;
		numOfMoves = 0;
		isGameOver = false;

		gui.setGame(this);
		this.gui = gui;

		// gets board from GUI
		board = gui.getBoard();
		board.setGame(this);

		if (mode == Mode.NORMAL) {

			whitePlayer = new Player(this, board, Alliance.WHITE, isWhiteComp);
			blackPlayer = new Player(this, board, Alliance.BLACK, isBlackComp);
		}

//		else if (mode == Mode.MEDIUM) {}
//		else if (mode == Mode.HARD) {}
//		else if (mode = Mode.NIGHTMARE) {}

		turn = Alliance.WHITE;

		// adds the pieces to the board
		for (Piece piece : whitePlayer.getPieces()) {
			piece.setMyPlayer(whitePlayer);
			piece.setEnemyPlayer(blackPlayer);
			board.getChessboard()[piece.getRow()][piece.getCol()].setPiece(piece);

		}
		for (Piece piece : blackPlayer.getPieces()) {
			piece.setMyPlayer(blackPlayer);
			piece.setEnemyPlayer(whitePlayer);
			board.getChessboard()[piece.getRow()][piece.getCol()].setPiece(piece);
		}

		// update labels without checking for check and checkmate
		gui.updateLabels(false);

		// creates a copy of all pieces
		allPieces = (ArrayList<Piece>) whitePlayer.getPieces().clone();
		allPieces.addAll(blackPlayer.getPieces());

		// if white is a computer, do first move
		if (whitePlayer.isComputer()) {
			whitePlayer.computerMove();
		}
	}

	/**
	 * get all the piece
	 * @return all piece in arraylist
	 */
	public ArrayList<Piece> getAllPieces() {
		return allPieces;
	}

	/**
	 * get the white piece player
	 * @return white player
	 */
	public Player getWhitePlayer() {
		return whitePlayer;
	}

	/**
	 * get the black piece player
	 * @return black player
	 */
	public Player getBlackPlayer() {
		return blackPlayer;
	}

	/**
	 * check the game is over or not
	 * @return game over or not
	 */
	public boolean isGameOver() {
		return isGameOver;
	}

	/**
	 * check the check mate status
	 * @return check mate or not
	 */
	public boolean isCheckMate() {
		// if not in testMode, this checks whether
		// the active player has any valid moves
		// otherwise ---> checkmate!

		if (mode != Mode.TEST) {
			if (turn == Alliance.WHITE) {
				for (Piece piece : whitePlayer.getPieces()) {
					// if piece is not off the board (i.e. captured)
					if (piece.getRow() != Board.BOARD_SIZE + 1) {
						if (!piece.calculateLegalMoves().isEmpty()) {

							return false;
						}
					}
				}
				// if no valid moves:
				isGameOver = true;
				return true;

			} else {
				for (Piece piece : blackPlayer.getPieces()) {
					if (piece.getRow() != Board.BOARD_SIZE + 1) {
						if (!piece.calculateLegalMoves().isEmpty()) {
							return false;
						}
					}
				}

				isGameOver = true;
				return true;

			}
		}
		return false;

	}

	/**
	 * change the turn when player finish one move
	 */
	public static void changeTurn() {
		if (turn == Alliance.WHITE) {
			turn = Alliance.BLACK;
		} else {
			turn = Alliance.WHITE;
		}
	}

	public Board getBoard() {
		return board;
	}


	/**
	 * this method is to check whether to update the labels or not
	 * @return true update, false haven't update
	 */
	public boolean inCheck() {
		if (mode != Mode.TEST) {
			if (turn == Alliance.WHITE) {
				for (Piece piece : blackPlayer.getPieces()) {
					if (piece.getRow() != Board.BOARD_SIZE + 1) {
						if (piece.calculateLegalMoves()
								.contains(board.getChessboard()[whitePlayer.getMyKing().getRow()][whitePlayer
										.getMyKing().getCol()])) {
							return true;
						}
					}
				}
				return false;
			} else {
				for (Piece piece : whitePlayer.getPieces()) {
					if (piece.getRow() != Board.BOARD_SIZE + 1) {
						if (piece.calculateLegalMoves()
								.contains(board.getChessboard()[blackPlayer.getMyKing().getRow()][blackPlayer
										.getMyKing().getCol()])) {
							return true;
						}
					}
				}
				return false;
			}
		}
		return false; // i.e. if in test mode
	}

	/**
	 * update the position
	 */
	public void updatePositions() {
		for (Piece piece : allPieces) {
			piece.updatePosition();
		}

	}

	/**
	 *undo function
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (numOfMoves != 0) {

			board.clearBoard();

			for (Piece piece : allPieces) {

				newRow = piece.getPreviousPositions().get(numOfMoves - 1).getRow();
				newCol = piece.getPreviousPositions().get(numOfMoves - 1).getCol();
				if (newRow != Board.BOARD_SIZE + 1) {
					board.getChessboard()[newRow][newCol].setPiece(piece);
					piece.setRow(newRow);
					piece.setCol(newCol);

					piece.getPreviousPositions().remove(numOfMoves - 1);

				}
			}

			numOfMoves--;
			changeTurn();
			gui.updateLabels(true);

		}
	}

	/**
	 * get the type of the mode
	 * @return type of the mode
	 */
	public Mode getMode() {
		return mode;
	}

}
