package serverdata;

import com.google.gson.Gson;
import ui.Repl;
import websocket.messages.ServerMessage;
import websocket.messages.ServerNotification;

import javax.management.Notification;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {
    private Session session;
    private Repl gameHandler;

    public WebSocketFacade(String url, Repl gameHandler) throws DeploymentException, IOException, URISyntaxException {
        url = url.replace("http", "ws");
        URI socketURI = new URI(url + "/ws");
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

    public void connect(){}

    public void makeMove(){}

    public void leaveGame(){}

    public void resignGame(){}

    private void sendMessage(){}


}
