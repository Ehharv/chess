package dataaccess;

import model.GameData;

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

    GameData getGame(int id) throws DataAccessException;
}
