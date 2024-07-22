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
    private String authToken;
    private GameService gameService;


    @BeforeEach
    public void clearBeforeTests() throws DataAccessException, BadRequestException, AlreadyTakenException {
        // init daos
        userDao = new MemoryUserDao();
        authDao = new MemoryAuthDao();
        gameDao = new MemoryGameDao();

        new ClearService(userDao, authDao, gameDao).clear();


        // create a valid user
        UserData user = new UserData("username", "password", "email@email.com");
        authToken = new UserService(userDao, authDao).register(user).authToken();

        gameService = new GameService(authDao, gameDao);

    }

    @Test
    public void testValidListGames() throws BadRequestException, DataAccessException, AlreadyTakenException, UnauthorizedException {
        // given a valid authtoken of a registered user
        List<GameData> expectedEmpty = new ArrayList<>();
        assertEquals(expectedEmpty, gameService.listGames(authToken));
    }

    @Test
    public void testInvalidListGames(){
        // no valid auth token provided
        assertThrows(UnauthorizedException.class, () -> {gameService.listGames("invalidAuthToken");});
    }

    @Test
    public void testValidAddGame() throws UnauthorizedException, BadRequestException, DataAccessException {
        int newGameID = gameService.addGame(authToken, "valid game name");
        int expectedGameID = 0;

        assertEquals(expectedGameID, newGameID); // becomes the 0th game
        assertEquals(1, gameService.listGames(authToken).size()); // adds to the list of games (size 1 now)
    }

    @Test
    public void testInvalidAddGame(){
        // no game name given
        assertThrows(BadRequestException.class, () -> {gameService.addGame(authToken, null);});
    }

}