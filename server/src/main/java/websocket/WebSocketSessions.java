package websocket;

import model.returnobjects.GameId;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessions {
    public final ConcurrentHashMap<Integer, Set<Session>> sessionMap = new ConcurrentHashMap<>();

    public void addSessionToGame(int gameId, Session session) {
        if(! sessionMap.containsKey(gameId)) {
            sessionMap.put(gameId, new HashSet<>());
            sessionMap.get(gameId).add(session);
        }
    }

    public void removeSessionFromGame(GameId gameId, Session session) {
    }

    public void removeSessions(Session session){}

    public Set<Session> getSessionForGame(GameId gameId) {
        return null;
    }

    // send a message
    public void sendMessage(String message, Session session) throws IOException {
        if(session.isOpen()){
            session.getRemote().sendString(message);
        }
    }

    // broadcast to all but specified
    public void broadcastMessage(int gameId, String message, Session exceptThisSession) throws IOException {
        for(Session session : sessionMap.get(gameId) ){
            if(session != exceptThisSession){
                sendMessage(message, session);
            }
        }
    }

}
