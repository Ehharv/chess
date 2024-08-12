package serverdata;

import com.google.gson.Gson;
import ui.Repl;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;
import websocket.messages.ServerNotification;

import javax.management.Notification;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {
    Session session;
    Repl gameHandler;

    public WebSocketFacade(String url, Repl gameHandler) throws DeploymentException, IOException, URISyntaxException {
        url = url.replace("http", "ws");
        URI socketURI = new URI("ws://localhost:8080/ws");
        this.gameHandler = gameHandler;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, socketURI);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    @Override
    public void onMessage(String message) {
        ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
        gameHandler.notify(notification);
    }

    public void connect() throws IOException {
        UserGameCommand command = new UserGameCommand(
                UserGameCommand.CommandType.CONNECT,
                UserContext.getInstance().getAuthToken(),
                UserContext.getInstance().getGameId()
        );

        sendMessage(command);
    }

    public void makeMove() throws IOException {
        UserGameCommand command = new UserGameCommand(
                UserGameCommand.CommandType.MAKE_MOVE,
                UserContext.getInstance().getAuthToken(),
                UserContext.getInstance().getGameId()
        );

        sendMessage(command);
    }

    public void leaveGame() throws IOException {
        UserGameCommand command = new UserGameCommand(
                UserGameCommand.CommandType.LEAVE,
                UserContext.getInstance().getAuthToken(),
                UserContext.getInstance().getGameId()
        );

        sendMessage(command);
    }

    public void resignGame() throws IOException {
        UserGameCommand command = new UserGameCommand(
                UserGameCommand.CommandType.RESIGN,
                UserContext.getInstance().getAuthToken(),
                UserContext.getInstance().getGameId()
        );

        sendMessage(command);
    }

    private void sendMessage(UserGameCommand command) throws IOException {
        session.getBasicRemote().sendText(new Gson().toJson(command));
    }


}
