import chess.*;
import ui.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var serverUrl = "http://localhost:8080";

        // can specify different url
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl).run();

    }

}