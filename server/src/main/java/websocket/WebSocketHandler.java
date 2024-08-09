package websocket;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.mysql.MysqlAuthDao;
import dataaccess.mysql.MysqlGameDao;
import model.AuthData;
import model.GameData;
import model.returnobjects.GameId;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import service.exceptions.BadRequestException;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import javax.management.Notification;
import java.io.IOException;
import java.sql.SQLException;

@WebSocket
public class WebSocketHandler {
    private final WebSocketSessions sessions = new WebSocketSessions();
    private final Gson gson = new Gson();
    GameDao gameDao = new MysqlGameDao();
    AuthDao authDao = new MysqlAuthDao();

    public WebSocketHandler() throws DataAccessException, SQLException {
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {}

    @OnWebSocketClose
    public void onClose(Session session) {}

    @OnWebSocketError
    public void onError(Throwable error) {}

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws DataAccessException, IOException, BadRequestException {
        // determine message type
        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType commandType = command.getCommandType();
        // get things to pass to methods
        int gameId = command.getGameID();
        GameData game = gameDao.getGame(gameId);
        String authToken = command.getAuthToken();
        String username = authDao.getAuthByToken(authToken).username();

        // call a method to process
        switch (commandType) {
            case LEAVE -> leaveGame(message, session, game, username);
            case RESIGN -> resignGame(message, game, username);
            case CONNECT -> connect(message, session, gameId, authToken);
            case MAKE_MOVE -> makeMove(message);
        }
    }

    // call service and send message to clients
    private void connect(String message, Session session, int gameId, String authToken) throws DataAccessException, IOException {
        sessions.addSessionToGame(gameId, session);
        ServerMessage.ServerMessageType loadGame = ServerMessage.ServerMessageType.LOAD_GAME;
        sessions.sendMessage(gson.toJson(loadGame), session);

        GameData game = gameDao.getGame(gameId);
        String username = authDao.getAuthByToken(authToken).username();

        if(username.equals(game.whiteUsername())) {
            message = username + "is playing as white";
        } else if(username.equals(game.blackUsername())) {
            message = username + "is playing as black";
        } else {
            message = username + "is observing";
        }

        sessions.broadcastMessage(gameId, gson.toJson(message), session);

    }

    private void makeMove(String message){}

    private void leaveGame(String message, Session session, GameData game, String username) throws DataAccessException, BadRequestException, IOException {
        if(game.blackUsername().equals(username)) {
            game = new GameData(game.gameID(), game.whiteUsername(), null, game.gameName(), game.game());
        } else if(game.whiteUsername().equals(username)) {
            game = new GameData(game.gameID(), null, game.blackUsername(), game.gameName(), game.game());
        }
        gameDao.updateGame(game);
        sessions.removeSessionFromGame(game.gameID(), session);

        message = username + "has left";

        sessions.broadcastMessage(game.gameID(), gson.toJson(message), session);
    }

    private void resignGame(String message, GameData game, String username) throws BadRequestException, DataAccessException, IOException {
        game.game().setOver(true);
        gameDao.updateGame(game);

        message = username + "resigned";
        sessions.broadcastMessage(game.gameID(), gson.toJson(message), null);
    }


}
