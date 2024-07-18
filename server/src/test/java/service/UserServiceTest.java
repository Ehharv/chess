package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserDao userDao;
    private AuthDao authDao;
    private GameDao gameDao;

    @BeforeEach
    public void clearBeforeTests() throws DataAccessException {
        // init daos
        userDao = new MemoryUserDao();
        authDao = new MemoryAuthDao();
        gameDao = new MemoryGameDao();

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
}