package dataaccess.mysql;

import dataaccess.UserDao;
import model.UserData;

public class MysqlUserDao implements UserDao {

    public void clear(){

    }

    public void add(UserData user){

    }

    public UserData getUser(String username){
        return null;
    }

    public boolean isValidLogin(String username, String password){
        return false;
    }

    public boolean isUsernameAvailable(String username){
        return false;
    }

    private String[] getCreateStatements() {
        return new String[]{
            """
            CREATE TABLE IF NOT EXISTS `user` (
            `username` VARCHAR(64) NOT NULL PRIMARY KEY,
            `password` VARCHAR(64) NOT NULL,
            `email` VARCHAR(64) NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """
        };
    }



}
