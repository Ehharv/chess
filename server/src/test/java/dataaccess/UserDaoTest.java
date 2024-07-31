package dataaccess;

import dataaccess.mysql.MysqlUserDao;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private static UserDao userDao;
    private final UserData userA = new UserData("usernameA", "passwordA", "emailA");
    private final UserData userB = new UserData("usernameB", "passwordB", "emailB");

    @BeforeAll
    static void setUp() throws DataAccessException {
        userDao = new MysqlUserDao();
    }

    @BeforeEach
    void clearDatabase() throws SQLException, DataAccessException {
        userDao.clear();
    }

    @Test
    void clearValid() throws SQLException, DataAccessException {
        userDao.add(userA);
        userDao.clear();
        assertNull(userDao.getUser(userA.username()));
    }

    @Test
    void addValid() throws DataAccessException, SQLException {
        userDao.add(userA);
        userDao.add(userB);

        // make sure they've both been added
        assertNotNull(userDao.getUser(userA.username()));
        assertNotNull(userDao.getUser(userB.username()));
    }

    @Test
    void addInvalid() throws SQLException, DataAccessException {
        userDao.add(userA);

        // try adding the user again
        assertThrows(DataAccessException.class, () -> userDao.add(userA));
    }

    @Test
    void getUserValid() throws SQLException, DataAccessException {
        userDao.add(userA);
        userDao.add(userB);

        // make sure they've both been added properly
        String usernameA = userDao.getUser(userA.username()).username();
        String hashedPasswordA = userDao.getUser(userA.username()).password();
        assertEquals(userA.username(), usernameA);
        assertTrue(BCrypt.checkpw(userA.password(), hashedPasswordA));

        // make sure they've both been added properly
        String usernameB = userDao.getUser(userB.username()).username();
        String hashedPasswordB = userDao.getUser(userB.username()).password();
        assertEquals(userB.username(), usernameB);
        assertTrue(BCrypt.checkpw(userB.password(), hashedPasswordB));

    }

    @Test
    void getUserInvalid() throws DataAccessException, SQLException {
        userDao.add(userB);

        // try to get the user that hasn't been added
        assertNull(userDao.getUser(userA.username()));
    }

    @Test
    void isValidLoginValid() throws SQLException, DataAccessException {
        userDao.add(userA);
        userDao.add(userB);

        // can find both inserted users
        assertTrue(userDao.isValidLogin(userA.username(), userA.password()));
        assertTrue(userDao.isValidLogin(userB.username(), userB.password()));
    }

    @Test
    void isValidLoginInvalid() throws SQLException, DataAccessException {
        userDao.add(userA);

        // see if the other user is valid
        assertFalse(userDao.isValidLogin(userB.username(), userB.password()));

        userDao.add(userB);

        // mix and match username/passswords
        assertFalse(userDao.isValidLogin(userB.username(), userA.password()));
        assertFalse(userDao.isValidLogin(userA.username(), userB.password()));
    }

    @Test
    void isUsernameAvailableValid() throws SQLException, DataAccessException {
        userDao.add(userA);

        // try the other user's username
        assertTrue(userDao.isUsernameAvailable(userB.username()));
    }

    @Test
    void isUsernameAvailableInvalid() throws SQLException, DataAccessException {
        userDao.add(userA);
        userDao.add(userB);

        //try the just added usernames
        assertFalse(userDao.isUsernameAvailable(userA.username()));
        assertFalse(userDao.isUsernameAvailable(userB.username()));

    }
}