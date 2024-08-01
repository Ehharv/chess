package ui;

import model.UserData;
import model.returnobjects.AuthTokenResponse;

import java.util.Arrays;

public class PreloginUi {
    private State state = State.SIGNEDOUT;
    private ServerFacade server;
    private String serverUrl;

    public PreloginUi(String serverUrl) {
        this.serverUrl = serverUrl;
        server = new ServerFacade(serverUrl);
    }

    public String help(){
        return """
        register <username> <password> <email> (to create a new account)
        login <username> <password> (to an existing account)
        quit (to exit)
        help (list available commands)
        """;
    }

    public String eval(String input){
        try{
            // make input lowercase
            var formatedIn = input.toLowerCase().split(" ");
            // if there isnt input, change it to help
            var cmd = (formatedIn.length > 0) ? formatedIn[0] : "help";
            // make an array of the args
            var params = Arrays.copyOfRange(formatedIn, 1, formatedIn.length);

            return switch (cmd){
                case "register" -> register(params);
                case "login" -> login(params);
                case "quit" -> "quit";
                default -> "help";
            };
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String register(String[] params) throws Exception {
        if(params.length != 3) {
            throw new Exception("Expected: <username> <password> <email>");
        } else{
            UserData user = new UserData(params[0], params[1], params[2]);
            AuthTokenResponse authToken = server.register(user);
            state = State.SIGNEDIN;
            return authToken.toString();
        }
    }

    private String login(String[] params) throws Exception {
        if(params.length != 2) {
            throw new Exception("Expected: <username> <password>");
        } else {
            UserData user = new UserData(params[0], params[1], null);
            AuthTokenResponse authToken = server.login(user);
            state = State.SIGNEDIN;
            return authToken.toString();
        }
    }


}
