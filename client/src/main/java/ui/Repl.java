package ui;

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

    }

}