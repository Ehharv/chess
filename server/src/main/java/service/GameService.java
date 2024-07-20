package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.UserDao;
import model.GameData;
import service.exceptions.BadRequestException;
import service.exceptions.UnauthorizedException;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    private UserDao userDao;
    private AuthDao authDao;
    private GameDao gameDao;

    public GameService(UserDao userDao, AuthDao authDao, GameDao gameDao) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public List<GameData> listGames(String authToken) throws DataAccessException, UnauthorizedException {
        if(authDao.getAuthByToken(authToken) !=  null){
            return gameDao.getAllGames();
        } else {
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }

    public int addGame(String authToken, String gameName) throws DataAccessException, UnauthorizedException, BadRequestException {
        if(authDao.getAuthByToken(authToken) !=  null) {
            if(gameName != null) {
                return gameDao.createGame(gameName);
            } else{
                throw new BadRequestException("Error: Invalid game name");
            }
        } else {
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }

}
