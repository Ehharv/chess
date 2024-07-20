package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDao {
    /**
     * Clears the database of all games
     */
    void clear();

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
    GameData getGame(int id) throws DataAccessException;

    /**
     * returns a list of all the games
     *
     */
    List<GameData> getAllGames() throws DataAccessException;
}
