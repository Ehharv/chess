package dataaccess.mysql;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;
import org.mindrot.jbcrypt.BCrypt;

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

    public void add(AuthData authData) throws DataAccessException {
        var statement = "INSERT INTO `auth` (authToken, username) VALUES (?, ?)";

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {

            // fill in variables in the statement
            preparedStatement.setString(1, authData.authToken());
            preparedStatement.setString(2, authData.username());

            preparedStatement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void remove(String authToken) throws DataAccessException {
        var statement = "DELETE FROM `auth` WHERE auth=?";

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.setString(1, authToken);

            preparedStatement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
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
