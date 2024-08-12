package ui;

import serverdata.ServerFacade;
import serverdata.UserContext;
import serverdata.WebSocketFacade;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class Ui {
    protected State state = State.SIGNEDOUT;
    protected String serverUrl;
    protected ServerFacade server;
    protected UserContext userContext;
    protected WebSocketFacade webSocket;

    public Ui(String serverUrl, State state, UserContext userContext) throws DeploymentException, IOException, URISyntaxException {
        this.state = state;
        this.serverUrl = serverUrl;
        this.server = new ServerFacade(serverUrl);
        this.userContext = userContext;
        Repl repl = new Repl(serverUrl);
        this.webSocket = new WebSocketFacade(serverUrl, repl);
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
