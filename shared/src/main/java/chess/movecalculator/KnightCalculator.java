package chess.movecalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();

        // knights move left/right two and up/down one
        int[] upDown = {-1,1};
        int[] leftRight = {-2, 2};
        knightMoves(board, myPosition, validMoves, row, col, color, upDown, leftRight);

        // knights can move left/right one and up/down two too
        int[] upDownNew = {-2,2};
        int[] leftRightNew = {-1, 1};
        knightMoves(board, myPosition, validMoves, row, col, color, upDownNew, leftRightNew);

        return validMoves;
    }

    /**
     * checks movement in the L shape customary to the knight piece
     *
     * @param board
     * @param myPosition
     * @param validMoves
     * @param row
     * @param col
     * @param color
     * @param upDown how far we are checking up and down movement (either +-1 or +-2)
     * @param leftRight how far we are checking horizontal movement (either +-1 or +-2)
     */
    private void knightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, int row, int col, ChessGame.TeamColor color, int[] upDown, int[] leftRight) {
        int newRow;
        int newCol;
        for (int ud : upDown) {
            for (int lr : leftRight) {
                newRow = row + ud;
                newCol = col + lr;
                if (isOnBoard(newRow, newCol)){
                    ChessPosition newPosition = new ChessPosition(newRow, newCol);
                    if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != color) {
                        validMoves.add(new ChessMove(myPosition, newPosition, null));
                    }
                }
            }
        }
    }

    private Boolean isOnBoard(int row, int col){
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }
}
