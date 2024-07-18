package dataaccess;

import model.AuthData;

public interface AuthDao {
    void clear();

    /**
     * retrives a user's authToken with the give username
     *
     * @param username the username of the authtoken to retrieve
     * @return the token
     */
     AuthData getAuth(String username);

    /**
     * Adds an authtoken
     *
     * @param authData the auth token to add
     */
    void add(AuthData authData) throws DataAccessException;


}
