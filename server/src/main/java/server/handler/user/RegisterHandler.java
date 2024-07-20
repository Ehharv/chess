package server.handler.user;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserService;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    private final UserService userService;

    public RegisterHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object handle(Request req, Response res) throws DataAccessException {
        // get the user data from json
        Gson gson = new Gson();
        UserData user = gson.fromJson(req.body(), UserData.class);
        try {
            AuthData authToken = userService.register(user);

            res.type("application/json");
            res.status(200);
            return gson.toJson(authToken);
        } catch(BadRequestException e){
            res.status(400);
            return gson.toJson(e.getMessage());
        } catch(AlreadyTakenException e){
            res.status(403);
            return gson.toJson(e.getMessage());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("Something went wrong");
        }
    }
}
