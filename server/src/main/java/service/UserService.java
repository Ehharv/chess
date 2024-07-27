package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import service.exceptions.UnauthorizedException;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;
    private final AuthDao authDao;

    public UserService(UserDao userDao, AuthDao authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }

    /**
     * Adds a new user including username, password, and email
     *
     * @param user the user to add to the users database
     * @return an auth token associated with the username
     * @throws DataAccessException
     * @throws AlreadyTakenException
     * @throws BadRequestException
     */
    public AuthData register(UserData user) throws DataAccessException, AlreadyTakenException, BadRequestException, SQLException {
        AuthData authData;
        // must have valid input to create a user
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

    /**
     * logs in a user if they use the proper username and password
     *
     * @param username
     * @param password
     * @return a new auth token associated with this username
     * @throws UnauthorizedException
     */
    public AuthData login(String username, String password) throws UnauthorizedException, DataAccessException {
        AuthData authData;
        if (username != null && password != null) {
            if (userDao.isValidLogin(username, password)) {
                authData = AuthData.generateAuthToken(username);
                authDao.add(authData);

                return authData;
            } else{
                throw new UnauthorizedException("Error: Unauthorized");
            }
        } else{
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }

    /**
     * removes the authToken association with the corresponding username
     *
     * @param authToken the auth token of a user
     * @throws UnauthorizedException
     */
    public void logout(String authToken) throws UnauthorizedException {
        // make sure this token exists then remove it
        if (authDao.getAuthByToken(authToken) != null) {
            authDao.remove(authToken);
        } else {
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }
}
