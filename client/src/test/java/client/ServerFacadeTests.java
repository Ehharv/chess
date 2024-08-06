package client;

import model.AuthData;
import model.UserData;
import model.returnobjects.AuthTokenResponse;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private static String username = "username";
    private static final String password = "password";
    private static final String email = "email.com";
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
    public void loginUser() throws Exception {
        String newUsername = username + new Random().nextInt();;
        UserData user = new UserData(newUsername, password, email);
        authToken = serverFacade.register(user).authToken();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerValid() throws Exception {
        String newUsername = username + new Random().nextInt();;
        UserData newUser = new UserData(newUsername, password, email);
        AuthTokenResponse authToken2 = serverFacade.register(newUser); // register unique user
        assertNotNull(authToken2);
    }

    @Test
    public void registerInvalid() {
        assertThrows(Exception.class, () -> serverFacade.register(user)); // register the same user

    }

    @Test
    public void loginValid() {}

    @Test
    public void loginInvalid() {}

    @Test
    public void logoutValid() {}

    @Test
    public void logoutInvalid() {}

    @Test
    public void createGameValid() {}

    @Test
    public void createGameInvalid() {}

    @Test
    public void listGamesValid() {}

    @Test
    public void listGamesInvalid() {}

    @Test
    public void joinGameValid() {}

    @Test
    public void joinGameInvalid() {}




}
