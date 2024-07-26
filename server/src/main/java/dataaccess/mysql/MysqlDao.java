package dataaccess.mysql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public abstract class MysqlDao {

    protected MysqlDao() throws DataAccessException {
        configureDatabase();
    }

    protected abstract String[] getCreateStatements();

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : getCreateStatements()) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    int executeUpdate(String statement, Object... params) throws DataAccessException, SQLException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                addParams(preparedStatement, params);
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s", e.getMessage()));
        }
    }

    private void addParams(PreparedStatement preparedStatement, Object[] params) throws SQLException, DataAccessException {

            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                switch (param) {
                    case String p -> preparedStatement.setString(i + 1, p);
                    case Integer p -> preparedStatement.setInt(i + 1, p);
                    case ChessGame p -> preparedStatement.setString(i + 1, new Gson().toJson(p));
                    case null -> preparedStatement.setNull(i + 1, Types.NULL);
                    default -> throw new DataAccessException("Unexpected data type: " + param.getClass());
                }
            }


    }

}
