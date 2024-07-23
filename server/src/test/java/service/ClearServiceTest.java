package service;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDao;
import dataaccess.memory.MemoryUserDao;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {


    @Test
    void testClearService() throws DataAccessException {
        // init daos
        UserDao userDao = new MemoryUserDao();
        AuthDao authDao = new MemoryAuthDao();
        GameDao gameDao = new MemoryGameDao();

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