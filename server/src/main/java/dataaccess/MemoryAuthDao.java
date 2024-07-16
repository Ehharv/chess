package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDao implements AuthDao{
    final private HashMap<Integer, AuthData> auth = new HashMap<>();

    public void clear(){
        auth.clear();
    }
}
