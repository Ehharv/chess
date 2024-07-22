package service;

import dataaccess.AuthDao;
import dataaccess.GameDao;
import dataaccess.UserDao;

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
     * clears all data from users, authtokens, and games
     */
    public void clear() {
        userdao.clear();
        authdao.clear();
        gamedao.clear();
    }
}
