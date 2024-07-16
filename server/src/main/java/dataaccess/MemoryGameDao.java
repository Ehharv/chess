package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDao implements GameDao {
    final private HashMap<Integer, GameData> games = new HashMap<>();

    public void clear(){
        games.clear();
    }
}
