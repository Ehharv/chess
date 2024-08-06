package client;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import model.returnobjects.AuthTokenResponse;
import model.returnobjects.GameId;
import model.returnobjects.GameList;
import model.returnobjects.JoinGameRequest;
import org.junit.jupiter.api.*;
import server.Server;
import ServerData.ServerFacade;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private static String username = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email.com";
    private String authToken;
    UserData user;

    @BeforeAll
    public static void init() throws Exception {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void registerUser() throws Exception {
        String newUsername = username + new Random().nextInt(); // make a new user each time we run tests
        user = new UserData(newUsername, PASSWORD, EMAIL);
        authToken = serverFacade.register(user).authToken();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerValid() throws Exception {
        String newUsername = username + new Random().nextInt();;
        UserData newUser = new UserData(newUsername, PASSWORD, EMAIL);
        AuthTokenResponse authToken2 = serverFacade.register(newUser); // register unique user
        assertNotNull(authToken2);
    }

    @Test
    public void registerInvalid() {
        assertThrows(Exception.class, () -> serverFacade.register(user)); // register the same user
    }

    @Test
    public void loginValid() throws Exception {
        AuthTokenResponse auth = serverFacade.login(user);
        assertNotNull(auth);
        assertNotEquals(authToken, auth.authToken()); // always get a new auth token
    }

    @Test
    public void loginInvalid() {
        UserData fakeUser = new UserData("FakeUser", PASSWORD, EMAIL);
        assertThrows(Exception.class, () -> serverFacade.login(fakeUser)); // can't login a user that hasn't been made
    }

    @Test
    public void logoutValid() throws Exception {
        serverFacade.logout();
        assertThrows(Exception.class, () -> serverFacade.listGames()); // can't list games
        // can't make a game
        assertThrows(Exception.class, () -> serverFacade.createGame(new GameData(1, null, null, "nullGame", new ChessGame())));

    }

    @Test
    public void logoutInvalid() throws Exception {
        serverFacade.logout();
        assertThrows(Exception.class, () -> serverFacade.logout()); // can't logout again

    }

    @Test
    public void createGameValid() throws Exception {
        GameData gameData = new GameData(42, null, null, "newGame!", new ChessGame());
        // good values for all necesary fields of new game
        GameId gameId = serverFacade.createGame(gameData);
        assertNotNull(gameId);
    }

    @Test
    public void createGameInvalid() {
        GameData gameData = new GameData(42, null, null, null, null);
        assertThrows(Exception.class, () -> serverFacade.createGame(gameData)); // null stuff
    }

    @Test
    public void listGamesValid() throws Exception {
        GameData gameData = new GameData(42, null, null, "newGame!", new ChessGame());
        serverFacade.createGame(gameData); // add a game

        GameList games = serverFacade.listGames();
        assertNotNull(games);
        assertNotEquals(0, games.games().length); // should have at least one game

    }

    @Test
    public void listGamesInvalid() throws Exception {
        serverFacade.logout();
        assertThrows(Exception.class, () -> serverFacade.listGames()); // can't list games without being logged in
    }

    @Test
    public void joinGameValid() throws Exception {
        GameData gameData = new GameData(42, null, null, "newGame!", new ChessGame());
        int id = serverFacade.createGame(gameData).gameID(); // add a game

        JoinGameRequest req = new JoinGameRequest(ChessGame.TeamColor.WHITE, id);
        assertDoesNotThrow(() -> serverFacade.joinGame(req)); // successfully join a new game
    }

    @Test
    public void joinGameInvalid() throws Exception {
        GameData gameData = new GameData(42, null, null, "newGame!", new ChessGame());
        int id = serverFacade.createGame(gameData).gameID(); // add a game

        JoinGameRequest req = new JoinGameRequest(ChessGame.TeamColor.WHITE, id);
        serverFacade.joinGame(req); // successfully join a new game

        assertThrows(Exception.class, () -> serverFacade.joinGame(req)); // try joining again as the same color
    }




}
