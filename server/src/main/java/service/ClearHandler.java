package service;

import service.ClearService;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

public class ClearHandler implements Route {
    private final ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    @Override
    public Object handle(Request req, Response res) {
        try {
            clearService.clear();
            res.type("application/json");
            res.status(200);
        } catch (Exception e) {
            res.type("application/json");
            res.status(500);
        }
        return "";

    }

}
