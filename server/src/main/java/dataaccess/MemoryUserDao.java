package dataaccess;

import model.User;

public class MemoryUserDao implements UserDao {
    public void clear(){

    }

    public void createUser(User user){

    }
    public User getUser(String username){
        return null;
    }

    public boolean verifyUser(String username, String password){
        return false;
    }

    public boolean doesUsernameExist(String username){
        return false;
    }


}
