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
        if(user != null && user.username() != null && user.password() != null) {
            if (userDao.isUsernameAvailable(user.username())) {
                userDao.add(user);

                authData = AuthData.generateAuthToken(user.username());
                authDao.add(authData);

                return authData;

            } else {
                throw new DataAccessException("Username is not available");
            }
        } else {
            throw new DataAccessException("Username is null");
        }
    }
}
