package chess.moveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class CalcFunctions {
    public static void checkUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, Collection<ChessMove> validMoves, int row, int col, int max){
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
            if(--max == 0){
                break;
            }
        }
    }

    public static void checkDown(ChessBoard board,ChessPosition myPosition,ChessGame.TeamColor color,Collection<ChessMove> validMoves,int row,int col, int max){
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
            if(--max == 0){
                break;
            }
        }
    }

    public static void checkLeft(ChessBoard board,ChessPosition myPosition,ChessGame.TeamColor color,Collection<ChessMove> validMoves,int row,int col, int max){
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
            if(--max == 0){
                break;
            }
        }
    }

    public static void checkRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, Collection<ChessMove> validMoves, int row, int col, int max){
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
            if(--max == 0){
                break;
            }
        }
    }

    public static void checkLowerLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col, int max){

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
            if(--max == 0){
                break;
            }
        }
    }

    public static void checkLowerRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col, int max){

        for (int i = row - 1, j = col + 1; i >= 1 && j <= 8; i--, j++) { // move down and right diagonally
            ChessPosition newPosition = new ChessPosition(i, j);
            if (board.getPiece(newPosition) == null) { // no pice in the way, keep moving
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color) { // piece of opposite color, move there
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            } else { // piece of same color
                break;
            }
            if(--max == 0){
                break;
            }
        }
    }

    public static void checkUpperRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col, int max){

        for (int i = row + 1, j = col + 1; i <= 8 && j <= 8; i++, j++) { // move up and right diagonally
            ChessPosition newPosition = new ChessPosition(i, j);
            if (board.getPiece(newPosition) == null) { // no pice in the way, keep moving
                validMoves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != color) { // piece of opposite color, move there
                validMoves.add(new ChessMove(myPosition, newPosition, null));
                break;
            } else { // piece of same color
                break;
            }
            if(--max == 0){
                break;
            }
        }
    }

    public static void checkUpperLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,Collection<ChessMove> validMoves, int row, int col, int max){

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
            if(--max == 0){
                break;
            }
        }
    }
}
