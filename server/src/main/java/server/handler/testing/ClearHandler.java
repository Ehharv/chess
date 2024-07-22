package server.handler.testing;

import com.google.gson.Gson;
import service.ClearService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    private final ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    @Override
    public Object handle(Request req, Response res) {
        Gson gson = new Gson();

        try {
            clearService.clear();

            res.type("application/json");
            res.status(200);
            return gson.toJson("");

        } catch (Exception e) {
            res.status(500);
            return gson.toJson(e.getMessage());
        }
    }

}
