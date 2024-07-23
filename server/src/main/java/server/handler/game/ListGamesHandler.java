package server.handler.game;

import com.google.gson.Gson;
import model.GameData;
import model.returnObjects.GameList;
import service.GameService;
import service.exceptions.ErrorMessage;
import service.exceptions.UnauthorizedException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ListGamesHandler implements Route {
    private final GameService gameService;

    public ListGamesHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Object handle(Request req, Response res) {
        Gson gson = new Gson();

        String authToken;

        try{
            authToken = req.headers("authorization");

            // Check authorization
            if(authToken == null || authToken.isEmpty()){
                throw new UnauthorizedException("Error: Unauthorized");
            }

            GameData[] allGames = gameService.listGames(authToken);

            res.type("application/json");
            res.status(200);
            return gson.toJson(new GameList(allGames));

        } catch (UnauthorizedException e) {
                res.status(401);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        }
    }
}
