package model;

import java.util.Objects;
import java.util.UUID;

public record AuthData(String authToken, String username) {

    /**
     * generates auth tokens for new and returning users given a username
     *
     * @param username the username of the user for which to make an auth token
     * @return the auth data (authToken and username)
     */
    public static AuthData generateAuthToken(String username){
        return new AuthData(UUID.randomUUID().toString(), username);
    }

}