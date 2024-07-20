package dataaccess.memory;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.GameData;

import java.util.ArrayList;
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

    public List<GameData> getAllGames() throws DataAccessException{
        List<GameData> allGames = new ArrayList<>();
        allGames.addAll(games.values());
        return allGames;
    }

    public int createGame(String gameName) throws DataAccessException{
        int id = getNextAvailableId();
        // create an id and put it in the hashset
        GameData newGame = new GameData(id, null, null, gameName, new ChessGame());
        games.put(id, newGame);
        return id;
    }


    private int getNextAvailableId() {
        int id = 0;
        while (games.get(id) != null) {
            id++;
        }
        return id;
    }


}