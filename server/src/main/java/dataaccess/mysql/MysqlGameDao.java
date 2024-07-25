package dataaccess.mysql;

import chess.ChessGame;
import dataaccess.GameDao;
import model.GameData;

public class MysqlGameDao implements GameDao {

    public void clear(){

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

    private String[] getCreateStatements() {
        return new String[]{
                """
            CREATE TABLE IF NOT EXISTS `user` (
            `gameID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            'gameName' VARCHAR(64),
            `whiteUsername` VARCHAR(64),
            `blackUsername` VARCHAR(64),
            'game' LONGTEXT NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """
        };
    }


}
