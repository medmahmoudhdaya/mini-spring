package com.mini;

import com.mini.dispatcher.Dispatcher;
import com.mini.dispatcher.HandlerMapping;
import com.mini.dispatcher.RequestHandlerAdapter;
import com.mini.dispatcher.ControllerMethodHandlerAdapter;
import com.mini.scanner.ControllerRegistrar;
import com.mini.server.HttpServer;

import java.util.List;

public class MiniApplication {

    public static void main(String[] args) throws Exception {

        HandlerMapping handlerMapping = new HandlerMapping();

        // Automatically discover and register controllers
        ControllerRegistrar registrar = new ControllerRegistrar();

        registrar.registerControllers(
                "com.mini.controller",
                handlerMapping
        );

        Dispatcher dispatcher = new Dispatcher(
                handlerMapping,
                List.of(
                        new RequestHandlerAdapter(),
                        new ControllerMethodHandlerAdapter()
                )
        );

        HttpServer server = new HttpServer(8088, dispatcher);

        server.start();
    }
}