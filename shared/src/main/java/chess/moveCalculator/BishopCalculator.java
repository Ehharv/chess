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

        System.out.println("row: " + row + ", col: " + col);

        checkLowerLeft(board, myPosition, color, validMoves, row, col);
        checkLowerRight(board, myPosition, color, validMoves, row, col);
        checkUpperRight(board, myPosition, color, validMoves, row, col);
        checkUpperLeft(board, myPosition, color, validMoves, row, col);

        System.out.println(validMoves.toString());

        return validMoves;
    }

    private void checkLowerLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col){

        for (int i = row -1, j = col-1; i >= 1 && j >= 1; i--, j--) { // move down and left diagnoally
            ChessPosition newPosition = new ChessPosition(i, j);
            if (board.getPiece(newPosition) == null) { // no pice in the way, keep moving
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color) { // piece of opposite color, move there
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            } else { // piece of same color
                break;
            }
        }
    }


    private void checkLowerRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col){

        for (int i = row - 1, j = col + 1; i >= 1 && j <= 8; i--, j++) { // move down and right diagonally
            ChessPosition newPosition = new ChessPosition(i, j);
            if (board.getPiece(newPosition) == null) { // no pice in the way, keep moving
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color) { // piece of opposite color, move there
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            } else { // piece of same color
                break;
            }        }
    }

    private void checkUpperRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col){

        for (int i = row + 1, j = col + 1; i <= 8 && j <= 8; i++, j++) { // move up and right diagonally
            ChessPosition newPosition = new ChessPosition(i, j);
            if (board.getPiece(newPosition) == null) { // no pice in the way, keep moving
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color) { // piece of opposite color, move there
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            } else { // piece of same color
                break;
            }        }
    }

    private void checkUpperLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col){

        for (int i = row + 1, j = col - 1; i <= 8 && j >= 1; i++, j--) { // move up and left diagonally
            ChessPosition newPosition = new ChessPosition(i, j);
            if (board.getPiece(newPosition) == null) { // no pice in the way, keep moving
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color) { // piece of opposite color, move there
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            } else { // piece of same color
                break;
            }
        }
    }

/*    private boolean addValidMove(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, Collection<ChessMove> validMoves, int i, int j) {
        ChessPosition newPosition = new ChessPosition(i, j);
        if (board.getPiece(newPosition) == null) { // no pice in the way, keep moving
            validMoves.add(new ChessMove(myPosition, newPosition, null));
        } else if (board.getPiece(newPosition).getTeamColor() != color) { // pice of opposite color, move there
            validMoves.add(new ChessMove(myPosition, newPosition, null));
            return true; // changed this line from true
        } else { // piece of same color
            return true;
        }
        return false;
    }*/
}
