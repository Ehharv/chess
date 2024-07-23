package chess.movecalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();

        CalcFunctions.checkLowerLeft(board, myPosition, 8, validMoves);
        CalcFunctions.checkLowerRight(board, myPosition, 8, validMoves);
        CalcFunctions.checkUpperRight(board, myPosition, 8, validMoves);
        CalcFunctions.checkUpperLeft(board, myPosition, 8, validMoves);

        CalcFunctions.checkUp(board, myPosition, 8, validMoves);
        CalcFunctions.checkDown(board, myPosition, 8, validMoves);
        CalcFunctions.checkLeft(board, myPosition, 8, validMoves);
        CalcFunctions.checkRight(board, myPosition, 8, validMoves);

        return validMoves;
    }

}
