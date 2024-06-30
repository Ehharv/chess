package chess.moveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();

        int[] upDown = {-1,1};
        int[] leftRight = {-2, 2};
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
        int[] upDownNew = {-2,2};
        int[] leftRightNew = {-1, 1};

        for (int ud : upDownNew) {
            for (int lr : leftRightNew) {
                newRow = row + ud;
                newCol = col + lr;
                if (isOnBoard(newRow, newCol)) {
                    ChessPosition newPosition = new ChessPosition(newRow, newCol);
                    if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() != color) {
                        validMoves.add(new ChessMove(myPosition, newPosition, null));
                    }
                }
            }
        }
        return validMoves;
    }

    private Boolean isOnBoard(int row, int col){
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }
}
