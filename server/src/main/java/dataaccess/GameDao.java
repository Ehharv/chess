package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.sql.SQLException;

public interface GameDao {
    /**
     * Clears the database of all games
     */
    void clear() throws SQLException, DataAccessException;

    /**
     * Creates a new game
     *
     * @param game the game to create
     */
    void add(GameData game) throws DataAccessException;

    /**
     * finds a game by its id
     *
     * @param id the game to find and return
     */
    GameData getGame(int id);

    /**
     * returns a list of all the games
     *
     */
    GameData[] getAllGames();

    /**
     * makes a new game and returns the id
     *
     * @param gameName name of the game to create
     */
    int createGame(String gameName) throws DataAccessException;

    /**
     * adds a player to the game
     *
     * @param color
     * @param username
     * @param gameId
     */
    void joinGame(ChessGame.TeamColor color, String username, int gameId);
}

