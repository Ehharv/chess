package dataaccess.mysql;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import model.AuthData;

import java.sql.SQLException;

public class MysqlAuthDao extends MysqlDao implements AuthDao {

    public MysqlAuthDao() throws DataAccessException, SQLException {
        super();

    }

    public void clear() throws SQLException, DataAccessException {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
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

    @Override
    protected String[] getCreateStatements() {
        return new String[]{
                """
            CREATE TABLE IF NOT EXISTS `auth` (
            `authToken` VARCHAR(64) NOT NULL PRIMARY KEY,
            `username` VARCHAR(64) NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """
        };
    }


}
