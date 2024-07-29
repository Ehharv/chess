package dataaccess;

import model.AuthData;

import java.sql.SQLException;

public interface AuthDao {
    void clear() throws SQLException, DataAccessException;

    /**
     * retrieves a user's auth data with the given username
     *
     * @param username of the data to retrieve
     * @return the authdata
     */
     AuthData getAuthByUsername(String username) throws DataAccessException;

    /**
     * retrieves a user's auth data with the given token
     *
     * @param token of the data to retrieve
     * @return the authdata
     */
    AuthData getAuthByToken(String token) throws DataAccessException;

    /**
     * Adds an auth token
     *
     * @param authData the auth token to add
     */
    void add(AuthData authData) throws DataAccessException;

    /**
     * Removes an auth token when logging out
     *
     * @param authToken the auth token to find and remove
     */
    void remove(String authToken) throws DataAccessException;
}
