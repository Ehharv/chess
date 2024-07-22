package server.handler.user;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.UserService;
import service.exceptions.UnauthorizedException;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    private final UserService userService;

    public LogoutHandler(UserService userService) {

        this.userService = userService;
    }

    @Override
    public Object handle(Request req, Response res) throws DataAccessException {
        Gson gson = new Gson();

        String authToken = req.headers("authorization");

        try {
            // check authorization
            if (authToken == null || authToken.isEmpty()) {
                throw new UnauthorizedException("Error: unauthorized");
            }

            userService.logout(authToken);

            res.type("application/json");
            res.status(200);
            return gson.toJson(null);

        } catch(UnauthorizedException e ) {
            res.status(401);
            return gson.toJson(e.getMessage());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(e.getMessage());
        }
    }
}
