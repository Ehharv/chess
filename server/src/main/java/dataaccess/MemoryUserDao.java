package dataaccess;

import model.UserData;

import java.util.HashMap;

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

    public boolean verifyUser(String username, String password){
        return false;
    }

    public boolean doesUsernameExist(String username){
        return false;
    }


}
