package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.UserDao;

import java.sql.SQLException;

public class ClearService {
    private final UserDao userdao;
    private final AuthDao authdao;
    private final GameDao gamedao;

    public ClearService(UserDao userdao, AuthDao authdao, GameDao gamedao) {
        this.userdao = userdao;
        this.authdao = authdao;
        this.gamedao = gamedao;
    }

    /**
     * clears all data from users, auth tokens, and games
     */
    public void clear() throws SQLException, DataAccessException {
        userdao.clear();
        authdao.clear();
        gamedao.clear();
    }
}
