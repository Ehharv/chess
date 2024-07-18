package dataaccess;

import model.AuthData;

public interface AuthDao {
    void clear();

    /**
     * retrives a user's authdata with the given username
     *
     * @param username of the data to retrieve
     * @return the authdata
     */
     AuthData getAuthByUsername(String username);

    /**
     * retrives a user's authdata with the given token
     *
     * @param token of the data to retrieve
     * @return the authdata
     */
    AuthData getAuthByToken(String token);

    /**
     * Adds an authtoken
     *
     * @param authData the auth token to add
     */
    void add(AuthData authData) throws DataAccessException;


}
