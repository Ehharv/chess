package ui;

import chess.ChessGame;
import serverdata.UserContext;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;

public class GamePlayUi extends Ui{
    public GamePlayUi(String serverUrl, State state, UserContext userContext) throws DeploymentException, IOException, URISyntaxException {
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
                default -> help();
            };
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String redraw(){
        ChessGame game = userContext.getGame();
        if(game == null){ // first time drawing
            game = new ChessGame();
        }
        PrintBoard.print(game, userContext.getColor());
        return "";
    }

    private String leave() throws IOException {
        webSocket.leaveGame();
        userContext.setGame(null);
        userContext.setColor(null);
        setState(State.SIGNEDIN);
        return "";
    }

    private String move(String[] params){
        return null;

    }

    private String resign() throws IOException {
        System.out.println("Confirm you wish to resign. Type 'Yes' to confirm");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if(input.equals("Yes")){
            webSocket.resignGame();
            return "Resigning...";
        } else{
            return "Resign canceled";
        }
    }

    private String highlight(String[] params){
        return null;
    }

}
