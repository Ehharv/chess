package serverdata;

import chess.ChessGame;

public class UserContext {
    private static UserContext instance;
    private String authToken;
    private ChessGame game;
    private ChessGame.TeamColor color;

    private UserContext() {}

    public static synchronized UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }

    public ChessGame.TeamColor getColor(){
        return this.color;
    }

    public void setColor(ChessGame.TeamColor color){
        this.color = color;
    }

    public ChessGame getGame(){
        return this.game;
    }

    public void setGame(ChessGame game){
        this.game = game;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
