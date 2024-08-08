package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import websocket.commands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    private final WebSocketSessions sessions = new WebSocketSessions();
    private final Gson gson = new Gson();

    @OnWebSocketConnect
    public void onConnect(Session session) {}

    @OnWebSocketClose
    public void onClose(Session session) {}

    @OnWebSocketError
    public void onError(Throwable error) {}

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        // determine message type
        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType commandType = command.getCommandType();
        // call a method to process
        switch (commandType) {
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
            case CONNECT -> connect(message);
            case MAKE_MOVE -> makeMove(message);
        }
    }

    // call service and/or send message to clients
    private void connect(String message){

    }

    // service.method(...)
    private void makeMove(String message){}

    // sendMessage(...)
    private void leaveGame(String message){}

    // broadcastMessage(...)
    private void resignGame(String message){}

    // send a message
    private void sendMessage(String message, Session session){}

    // broadcast to all but specified
    private void broadcastMessage(int gameId, String message, Session exceptThisSession){}

}
