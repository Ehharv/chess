package service;

import dataaccess.*;
import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDao;
import dataaccess.memory.MemoryUserDao;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import service.exceptions.UnauthorizedException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserDao userDao;
    private AuthDao authDao;

    @BeforeEach
    public void clearBeforeTests() {
        // init daos
        userDao = new MemoryUserDao();
        authDao = new MemoryAuthDao();
        GameDao gameDao = new MemoryGameDao();

        new ClearService(userDao, authDao, gameDao).clear();

    }

    @Test
    void testValidRegistration() throws DataAccessException, BadRequestException, AlreadyTakenException {

        UserData user = new UserData("username", "password", "email@email.com");
        AuthData registeredUser = new UserService(userDao, authDao).register(user);

        // Check if the info registered matches given info
        assertNotNull(registeredUser.authToken());
        assertEquals(user.username(), registeredUser.username());

        // Check if it got put into the database
        assertFalse(userDao.isUsernameAvailable(user.username()));
        assertEquals(authDao.getAuthByToken(registeredUser.authToken()).username(), user.username());
    }

    @Test
    void testInvalidRegistration() throws DataAccessException, BadRequestException, AlreadyTakenException {
        UserData user = new UserData("username", "password", "email@email.com");
        new UserService(userDao, authDao).register(user);

        assertThrows(AlreadyTakenException.class, () -> new UserService(userDao, authDao).register(user));
    }

    @Test
    void testValidLogin() throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {
        UserData user = new UserData("username", "password", "email@email.com");
        new UserService(userDao, authDao).register(user);

        AuthData authData = new UserService(userDao, authDao).login(user.username(), user.password());

        assertNotNull(authData.authToken());
        assertEquals(authData.username(), user.username());
    }

    @Test
    void testInvalidLogin() {
        UserData user = new UserData("username", "password", "email@email.com");

        // login a user without registering
         assertThrows(UnauthorizedException.class, () -> new UserService(userDao, authDao).login(user.username(), user.password()));
    }

    @Test
    void testValidLogout() throws BadRequestException, DataAccessException, AlreadyTakenException, UnauthorizedException {
        UserData user = new UserData("username", "password", "email@email.com");
        AuthData authData = new UserService(userDao, authDao).register(user);

        new UserService(userDao, authDao).logout(authData.authToken());

        assertNull(authDao.getAuthByToken(authData.authToken()));

    }

    @Test
    void testInvalidLogout() {
        assertThrows(UnauthorizedException.class, () -> new UserService(userDao, authDao).logout("authToken"));
    }
}