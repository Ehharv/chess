package chess.movecalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class CalcFunctions {

    /**
     * Called by the various check direction functions to iterate through all possible moves
     *
     * @param board the current board with all its pieces
     * @param myPosition where the piece in question is currently
     * @param max how far a piece could possibly move if unobstructed
     * @param rowIncrement pos = up, neg = down, 0 = no vertical movement
     * @param colIncrement pos = right, neg = left,  0 = no horizontal movement
     * @param validMoves collection of moves
     */
    private static void checkDirection(ChessBoard board, ChessPosition myPosition, int max, int rowIncrement, int colIncrement, Collection<ChessMove> validMoves) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();

        // max tells us how far we can possibly go
        for (int i = 1; i <= max; i++) {
            row += rowIncrement;
            col += colIncrement;

            if (row < 1 || row > 8 || col < 1 || col > 8) {
                break; // Stop if we go off the board
            }

            ChessPosition newPosition = new ChessPosition(row, col);

            if (board.getPiece(newPosition) == null) { // Empty
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color) { // Enemy
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break; // Stop moving after taking piece
            } else { // Friendly piece
                break;
            }
        }
    }

    /**
     * Function to check vertical positive moves
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkUp(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, 1, 0, validMoves);
    }

    /**
     * Function to check vertical negative moves
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkDown(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, -1, 0, validMoves);
    }

    /**
     * Function to check horizonal negative moves
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkLeft(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, 0, -1, validMoves);
    }

    /**
     * Function to check horizontal positive moves
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkRight(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, 0, 1, validMoves);
    }

    /**
     * Function to check moves diagonally left and down
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkLowerLeft(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, -1, -1, validMoves);
    }

    /**
     * Function to check moves diagonally right and down
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkLowerRight(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, -1, 1, validMoves);
    }

    /**
     * Function to check moves diagonally up and right
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkUpperRight(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, 1, 1, validMoves);
    }

    /**
     * Function to check moves diagonally left and up
     *
     * @param board
     * @param myPosition
     * @param max
     * @param validMoves
     */
    public static void checkUpperLeft(ChessBoard board, ChessPosition myPosition, int max, Collection<ChessMove> validMoves) {
        checkDirection(board, myPosition, max, 1, -1, validMoves);
    }
}
