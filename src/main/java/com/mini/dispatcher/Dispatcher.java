package com.mini.dispatcher;

import com.mini.http.HttpRequest;
import com.mini.http.HttpResponse;

import java.io.IOException;
import java.util.List;

public class Dispatcher {

    private final HandlerMapping handlerMapping;
    private final List<HandlerAdapter> handlerAdapters;

    public Dispatcher(
            HandlerMapping handlerMapping,
            List<HandlerAdapter> handlerAdapters
    ) {
        this.handlerMapping = handlerMapping;
        this.handlerAdapters = handlerAdapters;
    }

    public void dispatch(HttpRequest request, HttpResponse response)
            throws IOException {

        Object handler = handlerMapping.getHandler(request);

        if (handler == null) {
            response.setStatus(404);
            response.send("404 - Route not found");
            return;
        }

        for (HandlerAdapter adapter : handlerAdapters) {

            if (adapter.supports(handler)) {
                adapter.handle(request, response, handler);
                return;
            }
        }

        throw new IllegalStateException(
                "No HandlerAdapter found for: " + handler.getClass()
        );
    }
}