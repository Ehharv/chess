package chess.moveCalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalculator implements MoveCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
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
               if(newRow == promotionRow){
                   validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                   validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                   validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                   validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
               } else{
                   validMoves.add(new ChessMove(myPosition, newPosition, null));
               }

                // first move can only go two if the first space is also empty
                if(row == startRow){
                    int doubleRow = row + 2*forward;
                    ChessPosition firstMove = new ChessPosition(doubleRow, col);
                    if(board.getPiece(firstMove)
                            == null){
                        validMoves.add(new ChessMove(myPosition, firstMove, null));
                    }
                }
            }
            // capture
            int[] directions = {-1,1}; // check left and right diagonal
            for(int diagonal : directions){
                int newCol = col + diagonal;
                if((newCol >= 1) && (newCol <= 8)) { // in board bounds
                    ChessPosition diagonalPosition = new ChessPosition(newRow, newCol);
                    if((board.getPiece(diagonalPosition) != null) && // piece in diagonal spot
                            (board.getPiece(diagonalPosition).getTeamColor() != color)){ // enemy in diagonal
                        if(newRow == promotionRow){ // capture and promotion
                            validMoves.add(new ChessMove(myPosition, diagonalPosition, ChessPiece.PieceType.QUEEN));
                            validMoves.add(new ChessMove(myPosition, diagonalPosition, ChessPiece.PieceType.BISHOP));
                            validMoves.add(new ChessMove(myPosition, diagonalPosition, ChessPiece.PieceType.KNIGHT));
                            validMoves.add(new ChessMove(myPosition, diagonalPosition, ChessPiece.PieceType.ROOK));
                        } else{
                            validMoves.add(new ChessMove(myPosition, diagonalPosition, null)); // not promotion
                        }

                    }

                }
            }
        }





       /* if(color == ChessGame.TeamColor.BLACK) {
            if(row == 7){
                CalcFunctions.checkDown(board, myPosition, color, validMoves, row, col, 2); // move 2 for first time
            }else if(row == 2){ // promotion
                int newRow = --row;
                ChessPosition diagonalPositionL = new ChessPosition(newRow , --col);
                ChessPosition diagonalPositionR = new ChessPosition(newRow, ++col);
                ChessPosition newPosition = new ChessPosition(newRow, col);

                if(board.getPiece(newPosition) == null) {
                    validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
                }
            }
             // black pawns move down
            for(int i = row - 1; i >= 1; i--) {
                ChessPosition newPosition = new ChessPosition(i, col);
                ChessPosition diagonalPositionL = new ChessPosition(i , --col);
                ChessPosition diagonalPositionR = new ChessPosition(i, ++col);
                if(board.getPiece(newPosition) == null){ // empty space
                    validMoves.add(new ChessMove(myPosition, newPosition, null));

                } else if (board.getPiece(diagonalPositionL).getTeamColor() != color){ // enemy to the left
                    validMoves.add(new ChessMove(myPosition, diagonalPositionL, null));
                } else if(board.getPiece(diagonalPositionR).getTeamColor() != color){ // enemy to the right
                    validMoves.add(new ChessMove(myPosition, diagonalPositionR, null));
                }
                break; // can only move once


            }
        }else{ // white pawn moves
            if(row == 2){
                CalcFunctions.checkUp(board, myPosition, color, validMoves, row, col, 2); // move 2 for first time
            } else if(row == 7){ //promotion
                int newRow = ++row;
                ChessPosition newPosition = new ChessPosition(newRow, col);
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                validMoves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
            }
            CalcFunctions.checkUp(board, myPosition, color, validMoves, row, col, 1); // white pawns move up
        }
*/
        return validMoves;
    }
}
