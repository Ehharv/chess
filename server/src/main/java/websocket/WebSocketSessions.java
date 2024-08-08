package websocket;

import model.returnobjects.GameId;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessions {
    public final ConcurrentHashMap<GameId, Set<Session>> sessionMap = new ConcurrentHashMap<>();

    public void addSessionToGame(GameId gameId, Session session) {}

    public void removeSessionFromGame(GameId gameId, Session session) {
    }

    public void removeSessions(Session session){}

    public Set<Session> getSessionForGame(GameId gameId) {
        return null;
    }
}
