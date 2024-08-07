package server;

import com.google.gson.Gson;
import dataaccess.*;
import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDao;
import dataaccess.memory.MemoryUserDao;
import dataaccess.mysql.MysqlAuthDao;
import dataaccess.mysql.MysqlGameDao;
import dataaccess.mysql.MysqlUserDao;
import server.handler.game.CreateGameHandler;
import server.handler.game.JoinGameHandler;
import server.handler.game.ListGamesHandler;
import server.handler.testing.ClearHandler;
import server.handler.user.LoginHandler;
import server.handler.user.LogoutHandler;
import server.handler.user.RegisterHandler;
import service.GameService;
import service.UserService;
import service.exceptions.ErrorMessage;
import spark.*;
import service.ClearService;

import java.sql.SQLException;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserDao userDao;
        AuthDao authDao;
        GameDao gameDao;

        try {
            userDao = new MysqlUserDao();
            authDao = new MysqlAuthDao();
            gameDao = new MysqlGameDao();
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }

        ClearService clearService = new ClearService(userDao, authDao, gameDao);
        UserService userService = new UserService(userDao, authDao);
        GameService gameService = new GameService(authDao, gameDao);

        Gson gson = new Gson();
        // filter for logout
        Spark.before("/session", (request, response) -> {
            if (request.requestMethod().equals("DELETE")) {
                String authToken = request.headers("authorization");
                if (isUnauthorized(authToken, authDao)) {
                    Spark.halt(401,  gson.toJson(new ErrorMessage("Error: Unauthorized")));
                }
            }
        });

        // filter for game stuff
        Spark.before("/game", (request, response) -> {
            String authToken = request.headers("authorization");
            if (isUnauthorized(authToken, authDao)) {
                Spark.halt(401,  gson.toJson(new ErrorMessage("Error: Unauthorized")));
            }
        });

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", new ClearHandler(clearService));
        Spark.post("/user", new RegisterHandler(userService));
        Spark.post("/session", new LoginHandler(userService));
        Spark.delete("/session", new LogoutHandler(userService));
        Spark.get("/game", new ListGamesHandler(gameService));
        Spark.post("/game", new CreateGameHandler(gameService));
        Spark.put("/game", new JoinGameHandler(gameService));

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private static boolean isUnauthorized(String authToken, AuthDao authDao) throws DataAccessException {
        return authToken == null || authToken.isEmpty() || authDao.getAuthByToken(authToken) == null;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
