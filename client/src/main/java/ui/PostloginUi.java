package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.returnobjects.GameId;
import model.returnobjects.GameList;
import model.returnobjects.JoinGameRequest;

import java.util.Arrays;

public class PostloginUi extends Ui{

    public PostloginUi(String serverUrl, State state, UserContext userContext) {
        super(serverUrl, state, userContext);

    }

    public String help(){
        return """
        Available commands:
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
                case "quit" -> "exiting...";
                default -> help();
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
        GameList gameList = server.listGames();
        StringBuilder formattedList = new StringBuilder();

        for (GameData game : gameList.games()) {
            formattedList.append("Game: ")
                    .append(game.gameID())
                    .append(" ")
                    .append(game.gameName())
                    .append(" - White: ")
                    .append(game.whiteUsername())
                    .append(", Black: ")
                    .append(game.blackUsername())
                    .append("\n");
        }

        return formattedList.toString();
    }

    private String play(String[] params) throws Exception {
        if(params.length != 2){
            throw new Exception("Expected <game ID> <BLACK/WHITE>");
        } else {
            int id = Integer.parseInt(params[0]);
            ChessGame.TeamColor color = ChessGame.TeamColor.valueOf(params[1].toUpperCase());
            JoinGameRequest joinParams = new JoinGameRequest(color, id);
            server.joinGame(joinParams);
            setState(State.INGAME);
            return "Joined Game";
        }
    }

    private String observe(String[] params) throws Exception {
        if(params.length != 1){
            throw new Exception("Expected <game ID>");
        } else {
            int id = Integer.parseInt(params[0]);
            // add functionality later
            setState(State.INGAME);

            // print board
            PrintBoard printer = new PrintBoard();
            printer.print(new ChessGame(), ChessGame.TeamColor.WHITE);
            printer.print(new ChessGame(), ChessGame.TeamColor.BLACK);


            return "Watching game: (not implemented)";
        }
    }

    private String logout(String[] params) throws Exception {
        server.logout();
        setState(State.SIGNEDOUT);
        return "Signed out";
    }
}
