package server.handler.game;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import service.GameService;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import service.exceptions.ErrorMessage;
import service.exceptions.UnauthorizedException;
import spark.Request;
import spark.Response;
import spark.Route;


public class JoinGameHandler implements Route {
    private final GameService gameService;

    public JoinGameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Object handle(Request req, Response res) throws BadRequestException {
        Gson gson = new Gson();

        // parse request
        String authToken = req.headers("authorization");
        JsonObject jsonObject = JsonParser.parseString(req.body()).getAsJsonObject();
        Integer gameId;
        chess.ChessGame.TeamColor color;
        try{
            gameId = jsonObject.get("gameID").getAsInt();
            color = ChessGame.TeamColor.valueOf(jsonObject.get("playerColor").getAsString());

            if(authToken == null || authToken.isEmpty()){
                throw new UnauthorizedException("Error: Unauthorized");
            }

            gameService.joinGame(authToken, color, gameId);

            res.type("application/json");
            res.status(200);
            return gson.toJson(null);

        } catch (UnauthorizedException e) {
            res.status(401);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch(BadRequestException e){
            res.status(400);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch(AlreadyTakenException e){
            res.status(403);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        }
    }
}
