package server.handler.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.GameData;
import server.handler.testing.GameId;
import service.GameService;
import service.exceptions.BadRequestException;
import service.exceptions.ErrorMessage;
import service.exceptions.UnauthorizedException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class CreateGameHandler implements Route {
    private final GameService gameService;

    public CreateGameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Object handle(Request req, Response res) {
        Gson gson = new Gson();

        String authToken = req.headers("authorization");
        JsonObject jsonObject = JsonParser.parseString(req.body()).getAsJsonObject();
        String gameName;

        try{
            gameName = jsonObject.get("gameName").getAsString();

            // check for valid input
            if(authToken == null || authToken.isEmpty()){
                throw new UnauthorizedException("Error: Unauthorized");
            }
            if(gameName == null || gameName.isEmpty()){
                throw new BadRequestException("Error: Invalid game name");
            }


            int gameId = gameService.addGame(authToken, gameName);

            res.type("application/json");
            res.status(200);
            return gson.toJson(new GameId(gameId));

        } catch (UnauthorizedException e) {
            res.status(401);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch(BadRequestException e){
            res.status(400);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        }
    }
}
