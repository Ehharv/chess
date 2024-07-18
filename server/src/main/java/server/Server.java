package server;

import dataaccess.*;
import server.handler.ClearHandler;
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

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", new ClearHandler(clearService));
        Spark.post("/user", new RegisterHandler(userService));

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
