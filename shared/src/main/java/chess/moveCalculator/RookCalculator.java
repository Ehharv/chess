package chess.moveCalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;


public class RookCalculator implements MoveCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();

        CalcFunctions.checkUp(board, myPosition, 8, validMoves);
        CalcFunctions.checkDown(board, myPosition, 8, validMoves);
        CalcFunctions.checkLeft(board, myPosition, 8, validMoves);
        CalcFunctions.checkRight(board, myPosition, 8, validMoves);

        return validMoves;
    }
}
