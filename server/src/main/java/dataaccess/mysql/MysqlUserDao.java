package dataaccess.mysql;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDao;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class MysqlUserDao extends MysqlDao implements UserDao  {

    public MysqlUserDao() throws DataAccessException {
        super();
    }

    public void clear() throws SQLException, DataAccessException {
        var statement = "TRUNCATE user";
        executeUpdate(statement);
    }

    public void add(UserData user) throws SQLException, DataAccessException {
        var statement = "INSERT INTO `user` (username, password, email) VALUES (?, ?, ?)";

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {

            // hash password
            String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());

            // fill in variables in the statement
            preparedStatement.setString(1, user.username());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, user.email());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public UserData getUser(String username){
        return null;
    }

    public boolean isValidLogin(String username, String password){
        return false;
    }

    public boolean isUsernameAvailable(String username){
        return true;
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