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

        // up
        for(int i = row + 1; i <= 8; i++) {
            ChessPosition newPosition = new ChessPosition(i, col);
            if(board.getPiece(newPosition) == null){ // empty space
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color){ // enemy piece in the way
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break; // stop moving after taking piece
            } else { // friendly piece in the way
                break;
            }
        }


        // down
        for(int i = row - 1; i >= 1; i--) {
            ChessPosition newPosition = new ChessPosition(i, col);
            if(board.getPiece(newPosition) == null){ // empty space
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color){ // enemy piece in the way
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break; // stop moving after taking piece
            } else { // friendly piece in the way
                break;
            }
        }

        // left
        for(int i = col - 1; i >= 1; i--) {
            ChessPosition newPosition = new ChessPosition(row, i);
            if(board.getPiece(newPosition) == null){ // empty space
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color){ // enemy piece in the way
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break; // stop moving after taking piece
            } else { // friendly piece in the way
                break;
            }
        }

        // right
        for(int i = col + 1; i <= 8; i++) {
            ChessPosition newPosition = new ChessPosition(row, i);
            if(board.getPiece(newPosition) == null){ // empty space
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color){ // enemy piece in the way
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break; // stop moving after taking piece
            } else { // friendly piece in the way
                break;
            }
        }

        return validMoves;
    }
}
