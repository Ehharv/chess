package ui;

import chess.ChessGame;
import model.GameData;
import model.returnobjects.GameId;
import model.returnobjects.JoinGameRequest;

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
                case "list" -> list();
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

    private String create(String[] params) throws Exception {
        if(params.length != 1){
            throw new Exception("Expected <game name>");
        } else{
            GameData game = new GameData(0, null, null, params[0], new ChessGame());
            GameId id = server.createGame(game);
            return "created game: " + id.toString();
        }
    }

    private String list() throws Exception {
        return server.listGames().toString();
    }

    private String play(String[] params) throws Exception {
        if(params.length != 2){
            throw new Exception("Expected <game ID> <BLACK/WHITE>");
        } else {
            int id = Integer.parseInt(params[0]);
            ChessGame.TeamColor color = ChessGame.TeamColor.valueOf(params[1].toUpperCase());
            JoinGameRequest joinParams = new JoinGameRequest(id, color);
            server.joinGame(joinParams);
            state = State.INGAME;
            return "Joined Game";
        }
    }

    private String observe(String[] params) throws Exception {
        if(params.length != 1){
            throw new Exception("Expected <game ID>");
        } else {
            int id = Integer.parseInt(params[0]);
            // add functionality later
            state = State.INGAME;
            return "Watching game: (not implemented";
        }
    }

    private String logout(String[] params) throws Exception {
        server.logout();
        state = State.SIGNEDOUT;
        return "Signed out";
    }
}
