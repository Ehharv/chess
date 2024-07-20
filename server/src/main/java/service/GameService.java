package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.UserDao;
import model.GameData;
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
        List<GameData> allGames = new ArrayList<>();
        if(authDao.getAuthByToken(authToken) !=  null){
            return allGames = gameDao.getAllGames();
        } else {
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }

}
