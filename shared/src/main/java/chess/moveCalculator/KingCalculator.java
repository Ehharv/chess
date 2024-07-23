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

        CalcFunctions.checkLowerLeft(board, myPosition, 1, validMoves);
        CalcFunctions.checkLowerRight(board, myPosition, 1, validMoves);
        CalcFunctions.checkUpperRight(board, myPosition, 1, validMoves);
        CalcFunctions.checkUpperLeft(board, myPosition, 1, validMoves);

        CalcFunctions.checkUp(board, myPosition, 1, validMoves);
        CalcFunctions.checkDown(board, myPosition, 1, validMoves);
        CalcFunctions.checkLeft(board, myPosition, 1, validMoves);
        CalcFunctions.checkRight(board, myPosition, 1, validMoves);
        return validMoves;
    }
}