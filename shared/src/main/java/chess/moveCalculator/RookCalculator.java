package chess.moveCalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;


public class RookCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();

        CalcFunctions.checkUp(board, myPosition, color, validMoves, row, col,8);
        CalcFunctions.checkDown(board, myPosition, color, validMoves, row, col,8);
        CalcFunctions.checkLeft(board, myPosition, color, validMoves, row, col,8);
        CalcFunctions.checkRight(board, myPosition, color, validMoves, row, col,8);

        return validMoves;
    }
}
