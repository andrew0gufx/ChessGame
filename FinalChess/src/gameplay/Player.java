package gameplay;

import java.util.ArrayList;
import java.util.Random;

import gui.Board;
import gui.Tile;
import pieces.Alliance;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * This class has four different constructors, normal, Medium, difficult,
 * and Nightmare modes (mode can only be selected if the opponent is AI).
 * The ability to pick up and place pieces to gain an alliance (black or white).
 * The ability to determine if the player is a computer and control the computer's actions based on the player's actions.
 * @author GuFX
 *
 */
public class Player {

	private Alliance alliance;
	private ArrayList<Piece> pieces = new ArrayList<>();
	// to simplify code later
	private int rowIndex;
//	private Random randomGenerator = new Random();
//	private int randMove;
	private King myKing;
	private Game game;

	private boolean isComputer;

	public King getMyKing() {
		return myKing;
	}

	/**
	 * constructor for normal chess
	 * @param game
	 * @param board
	 * @param all alliance
	 * @param isComp check if it is computer
	 */
	public Player(Game game, Board board, Alliance all, boolean isComp) {
		this.game = game;
		this.alliance = all;
		this.isComputer = isComp;

		if (alliance == Alliance.WHITE) {
			rowIndex = 7;
		} else {
			rowIndex = 0;
		}

		myKing = new King(alliance, board, rowIndex, 4);

		// adds King and Queen
		pieces.add(myKing);
		pieces.add(new Queen(alliance, board, rowIndex, 3));

		// adds rooks, knights, bishops
		for (int i = 0; i < 2; i++) {
			pieces.add(new Bishop(alliance, board, rowIndex, 2 + 3 * i));
			pieces.add(new Knight(alliance, board, rowIndex, 1 + 5 * i));
			pieces.add(new Rook(alliance, board, rowIndex, 7 * i));
		}

		// adds pawns
		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn(alliance, board, (int) Math.round((5.0 / 7.0) * rowIndex + 1), i));
		}

	}

	/**
	 * this is the constructor for medium mode chess
	 * @param game
	 * @param board
	 * @param all
	 * @param boardSize
	 * @param isComp
	 */
	public Player(Game game, Board board, Alliance all, int boardSize, boolean isComp) {
//		this.game = game;
//
//		this.alliance = all;
//		this.isComputer = isComp;
//
//		if (alliance == Alliance.WHITE) {
//			rowIndex = boardSize - 1;
//		} else {
//			rowIndex = 0;
//		}
//
//		myKing = new King(alliance, board, rowIndex, boardSize / 2 - 1);
//
//		// adds King and Queen
//		pieces.add(myKing);
//		pieces.add(new Queen(alliance, board, rowIndex, boardSize / 2));
//
	}


	/**
	 * this is the constructor for hard mode chess
	 * ONLY TO GENERATE BLACK PIECES!
	 * @param game
	 * @param whitePlayerPieces
	 * @param board
	 * @param isComp
	 */
	public Player(Game game, ArrayList<Piece> whitePlayerPieces, Board board, boolean isComp) {
//		this.game = game;
//
//		this.isComputer = isComp;
//		this.alliance = Alliance.BLACK;
//		for (Piece piece : whitePlayerPieces) {
//			if (piece instanceof Pawn) {
//				this.pieces.add(new Pawn(alliance, board, (Board.BOARD_SIZE - 1 - piece.getRow()), piece.getCol()));
//			}
//			if (piece instanceof Rook) {
//				this.pieces.add(new Rook(alliance, board, (Board.BOARD_SIZE - 1 - piece.getRow()), piece.getCol()));
//			}
//		}

	}

	/**
	 * this is the constructor for nightmare mode chess
	 * @param game
	 * @param all
	 * @param board
	 * @param isComp
	 */
	//public Player(Game game, Alliance all, Board board, boolean isComp) {}

	/**
	 * get the piece
	 * @return piece list
	 */
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	/**
	 * set the piece
	 * @param pieces
	 */
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}

	/**
	 * get the alliance
	 * @return
	 */
	public Alliance getAlliance() {
		// TODO Auto-generated method stub
		return this.alliance;
	}

	/**
	 * check if it's computer
	 * @return true->computer, false->human
	 */
	public boolean isComputer() {
		// TODO Auto-generated method stub
		return isComputer;
	}

	/**
	 * set the computer's move
	 */
	public void computerMove() {
//		if (isComputer == true) {
//			while (true) {
//				randMove = randomGenerator.nextInt(pieces.size());
//				if (!pieces.get(randMove).calculateLegalMoves().isEmpty()
//						&& pieces.get(randMove).getRow() != Board.BOARD_SIZE + 1) {
//					Piece piece = pieces.get(randMove);
//					// click on the piece
//					game.getBoard().getChessboard()[piece.getRow()][piece.getCol()].doClick();
//					ArrayList<Tile> moves = piece.calculateLegalMoves();
//				}
//			}
//		}

	}

}
