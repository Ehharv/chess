package service;

import chess.ChessGame;
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
    private UserData user;


    @BeforeEach
    public void clearBeforeTests() throws DataAccessException, BadRequestException, AlreadyTakenException {
        // init daos
        userDao = new MemoryUserDao();
        authDao = new MemoryAuthDao();
        gameDao = new MemoryGameDao();

        new ClearService(userDao, authDao, gameDao).clear();


        // create a valid user
        user = new UserData("username", "password", "email@email.com");
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
        int expectedGameID = 1;

        assertEquals(expectedGameID, newGameID); // becomes the 1st game
        assertEquals(1, gameService.listGames(authToken).size()); // adds to the list of games (size 1 now)
    }

    @Test
    public void testInvalidAddGame(){
        // no game name given
        assertThrows(BadRequestException.class, () -> {gameService.addGame(authToken, null);});
    }

    @Test
    public void testValidJoinGame() throws UnauthorizedException, BadRequestException, DataAccessException, AlreadyTakenException {
        int newGameID = gameService.addGame(authToken, "valid game name");
        chess.ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;

        gameService.joinGame(authToken, color, newGameID); // join game 1 as white with good authToken

        GameData game = gameDao.getGame(newGameID);
        assertEquals(user.username(), game.whiteUsername()); // white matched our username
        assertNull(game.blackUsername()); // no black username assigned
    }

    @Test
    public void testInvalidJoinGame() throws UnauthorizedException, BadRequestException, DataAccessException, AlreadyTakenException {
        int newGameID = gameService.addGame(authToken, "valid game name");
        chess.ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;

        gameService.joinGame(authToken, color, newGameID); // join game 1 as white with good authToken
        // try to game 1 as white again
        assertThrows(AlreadyTakenException.class, () ->{gameService.joinGame(authToken, color, newGameID);});
        // try to join a noexistent game 2
        assertThrows(BadRequestException.class, () -> {gameService.joinGame(authToken, color, 2);});
    }

}