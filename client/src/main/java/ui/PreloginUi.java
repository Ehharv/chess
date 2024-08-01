package ui;

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
            var formatedIn = input.toLowerCase().split(" ");
            var cmd = (formatedIn.length > 0) ? formatedIn[0] : "help";
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



}
