package ui;

import ServerData.ServerFacade;
import ServerData.UserContext;

public abstract class Ui {
    protected State state = State.SIGNEDOUT;
    protected String serverUrl;
    protected ServerFacade server;
    protected UserContext userContext;

    public Ui(String serverUrl, State state, UserContext userContext){
        this.state = state;
        this.serverUrl = serverUrl;
        this.server = new ServerFacade(serverUrl);
        this.userContext = userContext;

    }

    public abstract String help();

    public abstract String eval(String input);

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
