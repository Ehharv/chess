package ui;

import serverdata.ServerFacade;
import serverdata.UserContext;

import java.util.Scanner;

public class Repl {
    String serverUrl;
    private final ServerFacade serverFacade;
    private final Scanner scanner;

    public Repl(String serverUrl) {
        this.serverUrl = serverUrl;
        this.serverFacade = new ServerFacade(serverUrl);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to Chess. Type 'help' for a list of commands.");

        UserContext userContext = UserContext.getInstance();
        Ui currentUi = new PreloginUi(serverUrl, State.SIGNEDOUT, userContext);
        boolean firstTimeLogin = true;

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {

                result = currentUi.eval(line);
                System.out.print(result);

                if(line.equals("quit")) {
                    break;
                }
                // check if we need to change uis
                switch (currentUi.getState()){
                    case SIGNEDOUT -> currentUi = new PreloginUi(serverUrl, State.SIGNEDOUT, userContext);
                    case SIGNEDIN -> {
                        currentUi = new PostloginUi(serverUrl, State.SIGNEDIN, userContext);
                        if(firstTimeLogin){
                            serverFacade.fillMap();
                            firstTimeLogin = false;
                        }
                    }
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