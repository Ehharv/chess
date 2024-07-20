package dataaccess;

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
        int id = 0;

        // find the next available ID
        while(games.get(id) != null){
            id ++;
        }

        // create an id and put it in the hashset
        GameData newGame = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(id, newGame);
    }

    public GameData getGame(int id) throws DataAccessException{
        return games.get(id);
    }

    public List<GameData> getAllGames() throws DataAccessException{
        List<GameData> allGames = new ArrayList<>();
        allGames.addAll(games.values());
        return allGames;
    }


}
