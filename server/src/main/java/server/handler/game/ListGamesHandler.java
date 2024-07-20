package server.handler.game;

import com.google.gson.Gson;
import model.GameData;
import service.GameService;
import service.exceptions.UnauthorizedException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;

public class ListGamesHandler implements Route {
    private final GameService gameService;

    public ListGamesHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Object handle(Request req, Response res) {
        Gson gson = new Gson();

        String authToken = req.headers("authorization");

        try{
            if(authToken == null || authToken.isEmpty()){
                throw new UnauthorizedException("Error: Unauthorized");
            }
            List<GameData> allGames = new ArrayList<>();
            allGames = gameService.listGames(authToken);
            res.type("application/json");
            res.status(200);
            return gson.toJson(allGames);

        } catch (UnauthorizedException e) {
                res.status(401);
                return gson.toJson(e.getMessage());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(e.getMessage());
        }
    }
}
