package ui;

import chess.ChessGame;
import serverdata.UserContext;

import java.util.Arrays;

public class GamePlayUi extends Ui{
    public GamePlayUi(String serverUrl, State state, UserContext userContext) {
        super(serverUrl, state, userContext);
    }

    public String help(){
        return """
        Available commands:
        redraw (to reload the board)
        leave (to leave the game)
        move <move> (to make a move)
        resign (to forfeit the game)
        highlight <piece> (to show legal moves)
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
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "move" -> move(params);
                case "resign" -> resign();
                case "highlight" -> highlight(params);
                case "logout" -> logout(params);
                case "quit" -> "exiting...";
                default -> help();
            };
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String redraw(){
        ChessGame game = userContext.getGame();
        if(game == null){
            game = new ChessGame();
        }
        PrintBoard.print(game, userContext.getColor());
        return "";
    }

    private String leave(){
        return null;

    }

    private String move(String[] params){
        return null;

    }

    private String resign(){
        return null;
    }

    private String highlight(String[] params){
        return null;
    }

    private String logout(String[] params){
        return null;
    }
}
