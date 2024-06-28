package chess.moveCalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class BishopCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        CalcFunctions.checkLowerLeft(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkLowerRight(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkUpperRight(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkUpperLeft(board, myPosition, color, validMoves, row, col);

        return validMoves;
    }
}
