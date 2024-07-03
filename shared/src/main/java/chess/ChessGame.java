package chess;

import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.KING;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private TeamColor TeamTurn;

    public ChessGame() {
        this.board = new ChessBoard();
        this.TeamTurn = WHITE; // white goes first
    }

    public ChessGame(ChessGame game){
        this.board = game.board;
        this.TeamTurn = game.TeamTurn;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return TeamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {

        this.TeamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private ChessPosition findKing(TeamColor color, ChessBoard localBoard){
        ChessPiece king = new ChessPiece(color, KING); // this is the king you're looking for
        for(int row =  1; row <= 8; row++){
            for(int col =  1; col <= 8; col++){
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = localBoard.getPiece(position);
                if(piece != null && piece.equals(king)){
                    return position;
                }

            }
        }
        return null; // these aren't the droids you're looking for (shouldn't reach here though)
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessGame.TeamColor color = board.getPiece(startPosition).getTeamColor();
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> allMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        // make sure your king isn't put in check by a move
        for(ChessMove move : allMoves){
            // copy the current board, so we can modify it and check for check
            ChessBoard boardCopy = copyBoard();

            ChessPosition end = move.getEndPosition();
            ChessPosition start = move.getStartPosition();
            boardCopy.addPiece(end,piece); // put the piece in the proposed position
            boardCopy.addPiece(start, null); // remove the piece from its old location
            if(!theoreticalIsInCheck(color, boardCopy)){
                validMoves.add(move); // if it doesn't put your king in check, it's a valid move
            }
        }

       return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
       Collection<ChessMove> validMoves = validMoves(move.getStartPosition()); // calculate valid moves
        ChessPiece piece = board.getPiece(move.getStartPosition());
        System.out.println("TEST\n");
        if(validMoves.isEmpty()){ // make sure there are some valid moves
           throw new InvalidMoveException();
       } else if(!validMoves.contains(move)){ // see if our valid list has the move we're testing
           throw new InvalidMoveException();
        } else if(isInCheck(getTeamTurn())){
            System.out.println("IN CHECK!");
            ChessBoard boardCopy = copyBoard();
            ChessPosition end = move.getEndPosition();
            ChessPosition start = move.getStartPosition();
            boardCopy.addPiece(end, piece);
            boardCopy.addPiece(start, null);
            if(!theoreticalIsInCheck(getTeamTurn(), boardCopy)){
                throw new InvalidMoveException();
            }


        }else {
           board.addPiece(move.getEndPosition(), piece);
           board.addPiece(move.getStartPosition(), null);
       }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPosition = findKing(teamColor, board);
        // itteratae through the whole board
        for(int row =  1; row <= 8; row++){
            for(int col =  1; col <= 8; col++){
                ChessPosition position = new ChessPosition(row, col); // current square
                ChessPiece piece = board.getPiece(position); // what piece is at the square
                if(piece != null && piece.getTeamColor() != teamColor) { // enemy piece
                    for (ChessMove move : piece.pieceMoves(board, position)) { // iterate through all the posible moves
                        if (move.getEndPosition().equals(kingPosition)) { // check if the end position will be where the king is
                            return true;
                        }
                    }
                }

            }
        }
        return false; // no pieces can capture the king in their current location
    }

    private boolean theoreticalIsInCheck(TeamColor teamColor, ChessBoard localBoard) {
        ChessPosition kingPosition = findKing(teamColor, localBoard);
        // itteratae through the whole board
        for(int row =  1; row <= 8; row++){
            for(int col =  1; col <= 8; col++){
                ChessPosition position = new ChessPosition(row, col); // current square
                ChessPiece piece = localBoard.getPiece(position); // what piece is at the square
                if(piece != null && piece.getTeamColor() != teamColor) { // enemy piece
                    Collection<ChessMove> allMoves = piece.pieceMoves(localBoard, position);
                    for (ChessMove move : allMoves) { // iterate through all the posible moves
                        if (move.getEndPosition().equals(kingPosition)) { // check if the end position will be where the king is
                            return true;
                        }
                    }
                }

            }
        }
        return false; // no pieces can capture the king
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * returns a copy of the board
     */
    public ChessBoard copyBoard() {
        ChessBoard boardCopy = new ChessBoard();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    boardCopy.addPiece(position, piece);
                }
            }
        }
        return boardCopy;
    }
}
