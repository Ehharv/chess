package chess.moveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        CalcFunctions.checkUp(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkDown(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkLeft(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkRight(board, myPosition, color, validMoves, row, col);

        CalcFunctions.checkLowerLeft(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkLowerRight(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkUpperRight(board, myPosition, color, validMoves, row, col);
        CalcFunctions.checkUpperLeft(board, myPosition, color, validMoves, row, col);



        return validMoves;
    }

}
