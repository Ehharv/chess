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
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import javax.management.Notification;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public void onMessage(Session session, String message) throws DataAccessException, IOException {
        // determine message type
        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType commandType = command.getCommandType();
        // get things to pass to methods
        int gameId = command.getGameID();
        String authToken = command.getAuthToken();

        // call a method to process
        switch (commandType) {
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
            case CONNECT -> connect(message, session, gameId, authToken);
            case MAKE_MOVE -> makeMove(message);
        }
    }

    // call service and/or send message to clients
    private void connect(String message, Session session, int gameId, String authToken) throws DataAccessException, IOException {
        sessions.addSessionToGame(gameId, session);
        ServerMessage.ServerMessageType loadGame = ServerMessage.ServerMessageType.LOAD_GAME;
        sessions.sendMessage(gson.toJson(loadGame), session);

        GameData game = gameDao.getGame(gameId);
        String username = authDao.getAuthByToken(authToken).username();
        String broadcast;

        if(username.equals(game.whiteUsername())) {
            broadcast = username + "is playing as white";
        } else if(username.equals(game.blackUsername())) {
            broadcast = username + "is playing as black";
        } else {
            broadcast = username + "is observing";
        }

        sessions.broadcastMessage(gameId, gson.toJson(broadcast), session);

    }

    // service.method(...)
    private void makeMove(String message){}

    // sendMessage(...)
    private void leaveGame(String message){}

    // broadcastMessage(...)
    private void resignGame(String message){}


}
