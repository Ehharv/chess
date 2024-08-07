package chess.movecalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class BishopCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<>();

        CalcFunctions.checkLowerLeft(board, myPosition, 8, validMoves);
        CalcFunctions.checkLowerRight(board, myPosition, 8, validMoves);
        CalcFunctions.checkUpperRight(board, myPosition, 8, validMoves);
        CalcFunctions.checkUpperLeft(board, myPosition, 8, validMoves);

        return validMoves;
    }
}
