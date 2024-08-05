package ui;

public abstract class Ui {
    protected State state = State.SIGNEDOUT;
    protected String serverUrl;
    protected ServerFacade server;
    protected String authToken;

    public Ui(String serverUrl, State state){
        this.state = state;
        this.serverUrl = serverUrl;
        this.server = new ServerFacade(serverUrl);
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
