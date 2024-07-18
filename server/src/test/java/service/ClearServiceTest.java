package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
    private UserDao userDao;
    private AuthDao authDao;
    private GameDao gameDao;


    @Test
    void testClearService() throws DataAccessException {
        // init daos
        userDao = new MemoryUserDao();
        authDao = new MemoryAuthDao();
        gameDao = new MemoryGameDao();

        // make data to insert
        UserData user = new UserData("user1", "password", "email");
        AuthData auth = new AuthData("auth", user.username());
        GameData game = new GameData(1, user.username(), "user2", "game name", new ChessGame());

        // put it in the database
        userDao.add(user);
        authDao.add(auth);
        gameDao.add(game);

        // call clear
        ClearService clearer =  new ClearService(userDao, authDao, gameDao);
        clearer.clear();

        // check if the data still exists
        assertNull(userDao.getUser(user.username()));
        assertNull(authDao.getAuthByUsername(user.username()));
        assertNull(gameDao.getGame(game.gameID()));

    }
}