package chess.movecalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalculator implements MoveCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int forward;
        int startRow;
        int promotionRow;
        if(color == ChessGame.TeamColor.WHITE){
            forward = 1;
            startRow = 2;
            promotionRow = 8;
        } else { // color is black
            forward = -1;
            startRow = 7;
            promotionRow = 1;
        }

        int newRow = row+forward;
        // reg forward
        if((newRow >= 1) && (newRow <= 8)){
            ChessPosition newPosition = new ChessPosition(newRow, col);
            if(board.getPiece(newPosition) == null){ // empty space
                addPromoOrRegMove(myPosition, newRow, promotionRow, validMoves, newPosition);
                addFirstMove(board, myPosition, row, startRow, forward, col, validMoves);
            }
            // capture
            int[] directions = {-1,1}; // check left and right diagonal
            for(int diagonal : directions){
                int newCol = col + diagonal;
                if((newCol >= 1) && (newCol <= 8)) { // in board bounds
                    ChessPosition diagonalPosition = new ChessPosition(newRow, newCol);
                    addCaptureMove(board, myPosition, diagonalPosition, color, newRow, promotionRow, validMoves);

                }
            }
        }
        return validMoves;
    }

    private static void addPromoOrRegMove(ChessPosition myPosition, int newRow, int promotionRow,
                                          Collection<ChessMove> validMoves, ChessPosition newPosition) {
        if(newRow == promotionRow){
            addPromotedMove(myPosition, validMoves, newPosition);
        } else{
            validMoves.add(new ChessMove(myPosition, newPosition, null));
        }
    }

    private static void addCaptureMove(ChessBoard board, ChessPosition myPosition, ChessPosition diagonalPosition,
                                       ChessGame.TeamColor color, int newRow, int promotionRow,
                                       Collection<ChessMove> validMoves) {
        if((board.getPiece(diagonalPosition) != null) && // piece in diagonal spot
                (board.getPiece(diagonalPosition).getTeamColor() != color)){ // enemy in diagonal
            addPromoOrRegMove(myPosition, newRow, promotionRow, validMoves, diagonalPosition);

        }
    }

    private static void addFirstMove(ChessBoard board, ChessPosition myPosition, int row, int startRow,
                                     int forward, int col, Collection<ChessMove> validMoves) {
        // first move can only go two if the first space is also empty
        if(row == startRow){
            int doubleRow = row + 2* forward;
            ChessPosition firstMove = new ChessPosition(doubleRow, col);
            if(board.getPiece(firstMove) == null){
                validMoves.add(new ChessMove(myPosition, firstMove, null));
            }
        }
    }

    private static void addPromotedMove(ChessPosition myPosition, Collection<ChessMove> validMoves,
                                        ChessPosition newPosition) {
        validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
        validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
        validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
        validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
    }
}
