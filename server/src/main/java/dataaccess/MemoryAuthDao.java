package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDao implements AuthDao{
    final private HashMap<String, AuthData> auth = new HashMap<>();

    public void clear(){
        auth.clear();
    }

    public AuthData getAuthByUsername(String username){
        return auth.get(username);
    }

    public AuthData getAuthByToken(String token){
        return auth.get(token);
    }

    public void add(AuthData authData) throws DataAccessException{
        auth.put(authData.authToken(), authData);
    }


}
