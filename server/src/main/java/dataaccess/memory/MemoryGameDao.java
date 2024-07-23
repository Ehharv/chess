package dataaccess.memory;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryGameDao implements GameDao {
    final private HashMap<Integer, GameData> games = new HashMap<>();

    public void clear(){
        games.clear();
    }

    public void add(GameData game){
        int id = getNextAvailableId();
        // create an id and put it in the hashset
        GameData newGame = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(id, newGame);
    }

    public GameData getGame(int id) throws DataAccessException {
        return games.get(id);
    }

    public GameData[] getAllGames() throws DataAccessException{
        Collection<GameData> gameCollection = games.values();
        GameData[] allGames = new GameData[0];
        allGames = gameCollection.toArray(allGames);
        return allGames;
    }

    public int createGame(String gameName) throws DataAccessException{
        int id = getNextAvailableId();
        // create an id and put it in the hashset
        GameData newGame = new GameData(id, null, null, gameName, new ChessGame());
        games.put(id, newGame);
        return id;
    }

    public void joinGame(ChessGame.TeamColor color, String username, int gameId){
        GameData game = games.get(gameId);
        GameData updatedGame;

        //replace the previous game with updated username
        if(color == ChessGame.TeamColor.BLACK){
            updatedGame = new GameData(gameId, game.whiteUsername(), username, game.gameName(), game.game());
        } else {
            updatedGame = new GameData(gameId, username, game.blackUsername(), game.gameName(), game.game());
        }

        games.put(gameId, updatedGame); // replace the old game
    }



    private int getNextAvailableId() {
        int id = 1; // api tests dictate that the first must be 1 (why isn't it zero index?)
        while (games.get(id) != null) {
            id++;
        }
        return id;
    }


}
