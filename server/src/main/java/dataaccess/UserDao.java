package dataaccess;

import model.UserData;

public interface UserDao {
    /**
     * Clears the database of all users
     */
    void clear();

    /**
     * Creates a new user
     *
     * @param user the user to create
     */
    void add(UserData user) throws DataAccessException;

    /**
     * retrieves a user with the give username
     *
     * @param username the username of the user to retrieve
     * @return the matching user
     */
    UserData getUser(String username);


    /**
     * Checks if the password and username match an existing person to facilitate logging in
     *
     * @param username the username entered when logging in
     * @param password the password entered when logging in
     * @return if there is a matching user with the same username and password
     */
    boolean isValidLogin(String username, String password);


    /**
     * Checks if the proposed username is available for a new user
     *
     * @param username the username entered when creating a new user
     * @return if the username is available
     */
    boolean isUsernameAvailable(String username);

}
