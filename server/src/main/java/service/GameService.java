package service;

import dataaccess.AuthDao;
import dataaccess.GameDao;
import dataaccess.UserDao;

public class GameService {

    private UserDao userDao;
    private AuthDao authDao;
    private GameDao gameDao;

    GameService(UserDao userDao, AuthDao authDao, GameDao gameDao) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }


}
