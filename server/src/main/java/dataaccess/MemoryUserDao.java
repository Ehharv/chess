package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDao implements UserDao {
    final private HashMap<String, UserData> users = new HashMap<>(); // key is username

    public void clear(){
        users.clear();
    }

    public void add(UserData user) throws DataAccessException {
        UserData newUser = new UserData(user.username(), user.password(), user.email());
        if(getUser(newUser.username()) == null){
            users.put(newUser.username(), newUser);
        } else{
            throw new DataAccessException("already taken");
        }


    }

    public UserData getUser(String username){
        return users.get(username);
    }

    public boolean isValidLogin(String username, String password){
        UserData user =  getUser(username);
        if (user == null) {
            return false;
        }
        return Objects.equals(user.password(), password);
    }

    public boolean isUsernameAvailable(String username){
        return !users.containsKey(username);
    }


}
