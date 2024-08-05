import chess.*;
import ui.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        var serverUrl = "http://localhost:8080";

        // can specify different url
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl).run();
        Ui currentUi = new PreloginUi(serverUrl, State.SIGNEDOUT);

        System.out.print(currentUi.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = currentUi.eval(line);
                System.out.print(result);

                // check if we need to change uis
                switch (currentUi.getState()){
                    case SIGNEDOUT -> currentUi = new PreloginUi(serverUrl, State.SIGNEDOUT);
                    case SIGNEDIN -> currentUi = new PostloginUi(serverUrl, State.SIGNEDIN);
                }

            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();

    }

    private static void printPrompt() {
        System.out.print("\n" + ">>> ");
    }
}