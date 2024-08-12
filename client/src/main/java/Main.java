import ui.*;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args) throws DeploymentException, IOException, URISyntaxException {

        var serverUrl = "http://localhost:8080";

        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl).run();

    }

}