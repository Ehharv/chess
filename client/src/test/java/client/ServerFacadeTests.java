package client;

import model.AuthData;
import model.UserData;
import model.returnobjects.AuthTokenResponse;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private static String username = "username";
    private static final String password = "password";
    private static final String email = "email.com";
    private String authToken;
    private static final UserData user = new UserData(username, password, email);


    @BeforeAll
    public static void init() throws Exception {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
        serverFacade.register(user);
    }

    @BeforeEach
    public void loginUser() throws Exception {
        authToken = serverFacade.login(user).authToken();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerValid() throws Exception {
        UserData newUser = new UserData("username2", password, email);
        AuthTokenResponse authToken2 = serverFacade.register(newUser); // register unique user
        assertNotNull(authToken2);
    }

    @Test
    public void registerInvalid() {

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
