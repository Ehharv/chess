package dataaccess.mysql;

import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.UserData;

import java.sql.SQLException;

public class MysqlUserDao extends MysqlDao implements UserDao  {

    public MysqlUserDao() throws DataAccessException {
        super();
    }

    public void clear() throws SQLException, DataAccessException {
        var statement = "TRUNCATE user";
        executeUpdate(statement);
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

    @Override
    protected String[] getCreateStatements() {
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
