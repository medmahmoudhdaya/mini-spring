package com.mini.dispatcher;

import com.mini.handler.RequestHandler;
import com.mini.http.HttpRequest;
import com.mini.http.HttpResponse;

import java.io.IOException;

public class RequestHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof RequestHandler;
    }

    @Override
    public void handle(
            HttpRequest request,
            HttpResponse response,
            Object handler
    ) throws IOException {

        RequestHandler requestHandler = (RequestHandler) handler;

        requestHandler.handle(request, response);
    }
}