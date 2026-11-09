package com.mini;

import com.mini.dispatcher.Dispatcher;
import com.mini.dispatcher.HandlerMapping;
import com.mini.dispatcher.RequestHandlerAdapter;
import com.mini.handler.RequestHandler;
import com.mini.server.HttpServer;

import java.util.List;

public class MiniApplication {

    public static void main(String[] args) throws Exception {

        HandlerMapping handlerMapping = new HandlerMapping();

        handlerMapping.register(
                "GET",
                "/users",
                (RequestHandler) (request, response) ->
                        response.send("Users endpoint")
        );

        handlerMapping.register(
                "GET",
                "/products",
                (RequestHandler) (request, response) ->
                        response.send("Products endpoint")
        );

        handlerMapping.register(
                "POST",
                "/users",
                (RequestHandler) (request, response) ->
                        response.send("User created")
        );

        Dispatcher dispatcher = new Dispatcher(
                handlerMapping,
                List.of(new RequestHandlerAdapter())
        );

        HttpServer server = new HttpServer(8088, dispatcher);

        server.start();
    }
}