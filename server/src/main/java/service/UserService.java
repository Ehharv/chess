package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;

public class UserService {
    private UserDao userDao;
    private AuthDao authDao;

    public UserService(UserDao userDao, AuthDao authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public AuthData register(UserData user) throws DataAccessException {
        AuthData authData;
        if (userDao.isUsernameAvailable(user.username())) {
            userDao.add(user);

            authData = AuthData.generateAuthToken(user.username());
            authDao.add(authData);

            return authData;

        } else {
            throw new RuntimeException("Username is not available");
        }
    }
}
