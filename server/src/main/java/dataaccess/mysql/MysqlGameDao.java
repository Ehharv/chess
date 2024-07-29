package dataaccess.mysql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDao;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlGameDao extends MysqlDao implements GameDao {

    public MysqlGameDao() throws DataAccessException {
        super();
    }

    public void clear() throws SQLException, DataAccessException {
        var statement = "TRUNCATE game";
        executeUpdate(statement);
    }
    public void add(GameData game) throws DataAccessException {
        var statement = "INSERT INTO `game` (gameName, whiteUsername, blackUsername, game) VALUES (?, ?, ?, ?)";

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {

            // fill in variables in the statement
            // we skip gameID since it auto increments
            preparedStatement.setString(1, game.gameName());
            preparedStatement.setString(2, game.whiteUsername());
            preparedStatement.setString(3, game.blackUsername());
            // serialize the game object
            Gson gson = new Gson();
            preparedStatement.setString(4, gson.toJson(game.game()));

            preparedStatement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public GameData getGame(int id){
        return null;
    }

    public GameData[] getAllGames(){
        return null;
    }

    public int createGame(String gameName) throws DataAccessException {
        var statement = "INSERT INTO `game` (gameName, game) VALUES (?, ?)";

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {

            // fill in variables in the statement
            preparedStatement.setString(1, gameName);
            // serialize the new game object
            Gson gson = new Gson();
            ChessGame game = new ChessGame();
            preparedStatement.setString(2, gson.toJson(game));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new DataAccessException("no ID obtained.");
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void joinGame(ChessGame.TeamColor color, String username, int gameId){

    }

    @Override
    protected String[] getCreateStatements() {
        return new String[]{
                """
            CREATE TABLE IF NOT EXISTS `game` (
            `gameId` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            `gameName` VARCHAR(64) NOT NULL,
            `whiteUsername` VARCHAR(64),
            `blackUsername` VARCHAR(64),
            `game` LONGTEXT NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """
        };
    }


}
