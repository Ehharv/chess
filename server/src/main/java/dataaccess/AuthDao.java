package dataaccess;

import model.AuthData;

public interface AuthDao {
    void clear();

    /**
     * retrieves a user's auth data with the given username
     *
     * @param username of the data to retrieve
     * @return the authdata
     */
     AuthData getAuthByUsername(String username);

    /**
     * retrieves a user's auth data with the given token
     *
     * @param token of the data to retrieve
     * @return the authdata
     */
    AuthData getAuthByToken(String token);

    /**
     * Adds an auth token
     *
     * @param authData the auth token to add
     */
    void add(AuthData authData);

    /**
     * Removes an auth token when logging out
     *
     * @param authToken the auth token to find and remove
     */
    void remove(String authToken);
}
