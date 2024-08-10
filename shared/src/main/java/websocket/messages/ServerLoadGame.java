package websocket.messages;

import model.returnobjects.GameId;

public class ServerLoadGame extends ServerMessage {
    private int game;
    public ServerLoadGame(ServerMessageType type, int game) {
        super(type);
        this.game = game;

    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

}
