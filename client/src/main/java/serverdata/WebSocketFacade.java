package serverdata;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class WebSocketFacade extends Endpoint implements MessageHandler.Whole<String> {
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }

    @Override
    public void onMessage(String message) {

    }

    public void connect(){}

    public void makeMove(){}

    public void leaveGame(){}

    public void resignGame(){}

    private void sendMessage(){}


}
