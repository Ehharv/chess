package dataaccess.mysql;

import dataaccess.AuthDao;
import model.AuthData;

public class MysqlAuthDao implements AuthDao {

    public void clear(){
    }

    public AuthData getAuthByUsername(String username){
        return null;
    }

    public AuthData getAuthByToken(String token){
        return null;
    }

    public void add(AuthData authData){

    }

    public void remove(String authToken){

    }

    private String[] getCreateStatements() {
        return new String[]{
                """
            CREATE TABLE IF NOT EXISTS `user` (
            `authToken` VARCHAR(64) NOT NULL PRIMARY KEY,
            `username` VARCHAR(64) NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """
        };
    }


}
