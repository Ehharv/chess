package chess.moveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KingCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        CalcFunctions.checkLowerLeft(board, myPosition, color, validMoves, row, col,1);
        CalcFunctions.checkLowerRight(board, myPosition, color, validMoves, row, col,1);
        CalcFunctions.checkUpperRight(board, myPosition, color, validMoves, row, col,1);
        CalcFunctions.checkUpperLeft(board, myPosition, color, validMoves, row, col,1);

        CalcFunctions.checkUp(board, myPosition, color, validMoves, row, col, 1);
        CalcFunctions.checkDown(board, myPosition, color, validMoves, row, col,1);
        CalcFunctions.checkLeft(board, myPosition, color, validMoves, row, col,1);
        CalcFunctions.checkRight(board, myPosition, color, validMoves, row, col,1);
        return validMoves;
    }
}