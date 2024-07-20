package server.handler.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.DataAccessException;
import model.AuthData;
import service.UserService;
import service.exceptions.UnauthorizedException;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    private final UserService userService;

    public LoginHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object handle(Request req, Response res) throws DataAccessException {
        // get the username and password from json
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(req.body()).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        try {
            AuthData authToken = userService.login(username, password);

            res.type("application/json");
            res.status(200);
            return gson.toJson(authToken);
        } catch(UnauthorizedException e){
            res.status(401);
            return gson.toJson(e.getMessage());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(e.getMessage());
        }
    }
}
