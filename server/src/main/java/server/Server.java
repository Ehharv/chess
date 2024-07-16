package server;

import dataaccess.*;
import spark.*;
import service.ClearService;
import service.handler.ClearHandler;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Initialize DAOs
        UserDao userDao = new MemoryUserDao();
        AuthDao authDao = new MemoryAuthDao();
        GameDao gameDao = new MemoryGameDao();

        ClearService clearService = new ClearService(userDao, authDao, gameDao);

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", new ClearHandler(clearService));

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
