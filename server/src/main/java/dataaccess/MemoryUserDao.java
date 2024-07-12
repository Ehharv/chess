package dataaccess;

import model.UserData;

public class MemoryUserDao implements UserDao {
    public void clear(){

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
