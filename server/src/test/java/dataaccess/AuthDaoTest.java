package dataaccess;

import dataaccess.mysql.MysqlAuthDao;
import model.AuthData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthDaoTest {
    private static AuthDao authDao;
    private final AuthData authDataA = new AuthData("tokenA", "userA");
    private final AuthData authDataB = new AuthData("tokenB", "userB");



    @BeforeAll
    static void setUp() throws SQLException, DataAccessException {
        authDao = new MysqlAuthDao();
    }

    @BeforeEach
    void clearDatabase() throws SQLException, DataAccessException {
        authDao.clear();
    }

    @Test
    void clearValid() throws SQLException, DataAccessException {
        authDao.add(authDataA);
        authDao.clear();
        assertNull(authDao.getAuthByToken(authDataA.authToken()));
    }

    @Test
    void getAuthByUsernameValid() throws DataAccessException {
        authDao.add(authDataA);
        // check against the same authData
        assertEquals(authDataA, authDao.getAuthByUsername(authDataA.username()));
    }

    @Test
    void getAuthByUsernameInvalid() throws DataAccessException {
        authDao.add(authDataA);

        // look for auth data that hasn't been added yet
        assertNull(authDao.getAuthByUsername(authDataB.username()));

        authDao.add(authDataB);
        // check against the other user's authData
        assertNotEquals(authDataB, authDao.getAuthByUsername(authDataA.username()));

    }

    @Test
    void getAuthByTokenValid() throws DataAccessException {
        authDao.add(authDataA);
        // check against the same authData
        assertEquals(authDataA, authDao.getAuthByToken(authDataA.authToken()));
    }

    @Test
    void getAuthByTokenInvalid() throws DataAccessException {
        authDao.add(authDataA);

        // look for auth data that hasn't been added yet
        assertNull(authDao.getAuthByToken(authDataB.authToken()));

        authDao.add(authDataB);
        // check against the other user's authData
        assertNotEquals(authDataB, authDao.getAuthByToken(authDataA.authToken()));

    }

    @Test
    void addValid() throws DataAccessException {
        authDao.add(authDataA);
        authDao.add(authDataB);

        // make sure they both have been added
        assertEquals(authDataA, authDao.getAuthByToken(authDataA.authToken()));
        assertEquals(authDataB, authDao.getAuthByToken(authDataB.authToken()));
        assertEquals(authDataA, authDao.getAuthByUsername(authDataA.username()));
        assertEquals(authDataB, authDao.getAuthByUsername(authDataB.username()));

    }

    @Test
    void addInvalid() throws DataAccessException {
        authDao.add(authDataA);
        // add a duplicate authData
        assertThrows(DataAccessException.class, () -> authDao.add(authDataA));

    }

    @Test
    void removeValid() throws DataAccessException {
        authDao.add(authDataA);
        authDao.remove(authDataA.authToken());

        assertNull(authDao.getAuthByToken(authDataA.authToken()));


    }

    @Test
    void removeInvalid() throws DataAccessException {
        authDao.add(authDataA);
        authDao.remove(authDataB.authToken());
        assertNotNull(authDao.getAuthByToken(authDataA.authToken()));

    }

}