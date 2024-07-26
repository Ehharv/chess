package dataaccess.mysql;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.GameData;

import java.sql.SQLException;

public class MysqlGameDao extends MysqlDao implements GameDao {

    public MysqlGameDao() throws DataAccessException {
        super();
    }

    public void clear() throws SQLException, DataAccessException {
        var statement = "TRUNCATE game";
        executeUpdate(statement);
    }
    public void add(GameData game){

    }


    public GameData getGame(int id){
        return null;
    }

    public GameData[] getAllGames(){
        return null;
    }

    public int createGame(String gameName){
        return 0;
    }

    public void joinGame(ChessGame.TeamColor color, String username, int gameId){

    }

    @Override
    protected String[] getCreateStatements() {
        return new String[]{
                """
            CREATE TABLE IF NOT EXISTS `game` (
            `gameID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            `gameName` VARCHAR(64) NOT NULL,
            `whiteUsername` VARCHAR(64),
            `blackUsername` VARCHAR(64),
            `game` LONGTEXT NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """
        };
    }


}
