package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;

public class UserService {
    private UserDao userDao;
    private AuthDao authDao;

    public UserService(UserDao userDao, AuthDao authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public AuthData register(UserData user) throws DataAccessException, AlreadyTakenException, BadRequestException {
        AuthData authData;
        if(user != null && user.username() != null && user.password() != null) {
            if (userDao.isUsernameAvailable(user.username())) {
                userDao.add(user);

                authData = AuthData.generateAuthToken(user.username());
                authDao.add(authData);

                return authData;

            } else {
                throw new AlreadyTakenException("Error: Already Taken");
            }
        } else {
            throw new BadRequestException("Error: Bad Request");
        }
    }
}
