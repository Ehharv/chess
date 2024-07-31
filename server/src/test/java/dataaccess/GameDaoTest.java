package dataaccess;

import chess.ChessGame;
import dataaccess.mysql.MysqlGameDao;
import model.GameData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.exceptions.BadRequestException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GameDaoTest {
    private static GameDao gameDao;
    private final GameData gameA = new GameData(1, "whiteUsernameA", "blackUsernameA", "game1", new ChessGame());
    private final GameData gameB = new GameData(2, "whiteUsernameB", "blackUsernameB", "game2", new ChessGame());
    private final GameData badGame = new GameData(0, null, null, null, null);

    @BeforeAll
    static void setup() throws DataAccessException {
        gameDao = new MysqlGameDao();
    }

    @BeforeEach
    void clearDatabase() throws SQLException, DataAccessException {
        gameDao.clear();
    }

    @Test
    void clear() throws DataAccessException, SQLException {
        gameDao.add(gameA);
        gameDao.add(gameB);

        gameDao.clear();

        // they don't exist anymore
        assertNull(gameDao.getGame(gameA.gameID()));
        assertNull(gameDao.getGame(gameB.gameID()));
    }

    @Test
    void addValid() throws DataAccessException {
        gameDao.add(gameA);
        gameDao.add(gameB);

        // added
        assertNotNull(gameDao.getGame(gameA.gameID()));
        assertNotNull(gameDao.getGame(gameB.gameID()));
    }

    @Test
    void addInvalid() throws DataAccessException {

        // add a game missing values
        assertThrows(DataAccessException. class, () -> gameDao.add(badGame));

    }

    @Test
    void getGameValid() throws DataAccessException {
        gameDao.add(gameA);
        gameDao.add(gameB);

        // check they added correctly
        assertEquals(gameA, gameDao.getGame(gameA.gameID()));
        assertEquals(gameB, gameDao.getGame(gameB.gameID()));
    }

    @Test
    void getGameInvalid() throws DataAccessException {
        gameDao.add(gameA);

        // check for the unadded game
        assertNull(gameDao.getGame(gameB.gameID()));

        gameDao.add(gameB);
        // check against the other game
        assertNotEquals(gameB, gameDao.getGame(gameA.gameID()));
    }

    @Test
    void getAllGamesValid() throws DataAccessException {
        gameDao.add(gameA);
        // one game
        assertEquals(1, gameDao.getAllGames().length);

        gameDao.add(gameB);
        // 2 games
        assertEquals(2, gameDao.getAllGames().length);

        var allGames = gameDao.getAllGames();

        // check that the values are all correct
        assertEquals(gameA, allGames[0]);
        assertEquals(gameB, allGames[1]);

    }

    @Test
    void getAllGamesInvalid() throws DataAccessException {
        // no games
        assertEquals(0, gameDao.getAllGames().length);
    }

    @Test
    void createGameValid() throws DataAccessException {
        gameDao.createGame(gameA.gameName());
        // check that something was created
        assertNotNull(gameDao.getGame(gameA.gameID()));
        // check that the name is correct
        assertEquals(gameA.gameName(), gameDao.getGame(gameA.gameID()).gameName());
    }

    @Test
    void createGameInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> gameDao.createGame(null));
    }

    @Test
    void joinGameValid() throws DataAccessException, BadRequestException {
        int gameId = gameDao.createGame(gameA.gameName());
        gameDao.joinGame(ChessGame.TeamColor.WHITE, gameA.whiteUsername(), gameId);

        GameData gameData = gameDao.getGame(gameId);
        // check that it updated username and stuff
        assertEquals(gameA.gameName(), gameData.gameName());
        assertEquals(gameA.whiteUsername(), gameData.whiteUsername());
        assertNull(gameData.blackUsername());
    }

    @Test
    void joinGameInvalid() throws DataAccessException {
        int gameId = gameDao.createGame(gameA.gameName());
        // join game as an invalid color
        assertThrows(BadRequestException.class, () -> gameDao.joinGame(null, null, gameId));
    }
}
