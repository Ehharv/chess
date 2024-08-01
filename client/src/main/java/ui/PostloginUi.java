package ui;

import java.util.Arrays;

public class PostloginUi {
    private State state = State.SIGNEDIN;
    private ServerFacade server;
    private String serverUrl;

    public PostloginUi(String serverUrl) {
        this.serverUrl = serverUrl;
        server = new ServerFacade(serverUrl);
    }

    public String help(){
        return """
        create <game name> (to create a new game)
        list (to see all games on the server)
        play <game ID> <BLACK/WHITE> (to join a game as the selected color)
        observe <game ID>
        logout
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
                case "create" -> create(params);
                case "list" -> list(params);
                case "play" -> play(params);
                case "observe" -> observe(params);
                case "logout" -> logout(params);
                case "quit" -> "quit";
                default -> "help";
            };
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String create(String[] params) {
        return null;
    }

    private String list(String[] params) {
    }

    private String play(String[] params) {
        return null;
    }

    private String observe(String[] params) {
        return null;
    }

    private String logout(String[] params) {
    }
}
