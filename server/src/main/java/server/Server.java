package server;

import dataaccess.*;
import server.handler.ClearHandler;
import server.handler.LoginHandler;
import server.handler.LogoutHandler;
import server.handler.RegisterHandler;
import service.UserService;
import spark.*;
import service.ClearService;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Initialize DAOs
        UserDao userDao = new MemoryUserDao();
        AuthDao authDao = new MemoryAuthDao();
        GameDao gameDao = new MemoryGameDao();

        ClearService clearService = new ClearService(userDao, authDao, gameDao);
        UserService userService = new UserService(userDao, authDao);

        // filter for logout
        Spark.before("/session", (request, response) -> {
            if (request.requestMethod().equals("DELETE")) {
                String authToken = request.headers("authorization");
                if (isAuthorized(authToken, authDao)) {
                    Spark.halt(401,  "{\"message\":\"Error: unauthorized\"}");
                }
            }
        });

        // filter for game stuff
        Spark.before("/game", (request, response) -> {
            String authToken = request.headers("authorization");
            if (isAuthorized(authToken, authDao)) {
                Spark.halt(401,  "{\"message\":\"Error: unauthorized\"}");
            }
        });

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", new ClearHandler(clearService));
        Spark.post("/user", new RegisterHandler(userService));
        Spark.post("/session", new LoginHandler(userService));
        Spark.delete("/session", new LogoutHandler(userService));

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private static boolean isAuthorized(String authToken, AuthDao authDao) {
        return authToken == null || authToken.isEmpty() || authDao.getAuthByToken(authToken) == null;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
