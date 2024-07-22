package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.UserDao;
import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDao;
import dataaccess.memory.MemoryUserDao;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import service.exceptions.UnauthorizedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    private UserDao userDao;
    private AuthDao authDao;
    private GameDao gameDao;

    @BeforeEach
    public void clearBeforeTests() throws DataAccessException {
        // init daos
        userDao = new MemoryUserDao();
        authDao = new MemoryAuthDao();
        gameDao = new MemoryGameDao();

        new ClearService(userDao, authDao, gameDao).clear();

    }

    @Test
    public void testValidListGames() throws BadRequestException, DataAccessException, AlreadyTakenException, UnauthorizedException {
        UserData user = new UserData("username", "password", "email@email.com");
        String authToken = new UserService(userDao, authDao).register(user).authToken();

        // given a valid authtoken of a registered user
        List<GameData> expectedEmpty = new ArrayList<>();
        assertEquals(expectedEmpty, new GameService(authDao, gameDao).listGames(authToken));
    }

    @Test
    public void testInvalidListGames(){
        // no valid auth token provided
        assertThrows(UnauthorizedException.class, () -> {new GameService(authDao, gameDao).listGames("invalidAuthToken");});
    }

}