package model;

import java.util.Objects;

public class AuthToken {
    private final String authToken;
    private final String username;

    AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "authToken='" + authToken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken) && Objects.equals(username, authToken1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, username);
    }
}
