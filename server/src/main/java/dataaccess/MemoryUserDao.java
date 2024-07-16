package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDao implements UserDao {
    final private HashMap<String, UserData> users = new HashMap<>(); // key is username

    public void clear(){
        users.clear();
    }

    public void createUser(UserData user){

    }

    public UserData getUser(String username){
        return null;
    }

    public boolean verifyUser(String username, String password){
        return false;
    }

    public boolean doesUsernameExist(String username){
        return false;
    }


}
