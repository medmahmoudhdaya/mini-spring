package com.mini.dispatcher;

import com.mini.handler.ControllerMethodHandler;
import com.mini.http.HttpRequest;
import com.mini.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ControllerMethodHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ControllerMethodHandler;
    }

    @Override
    public void handle(
            HttpRequest request,
            HttpResponse response,
            Object handler
    ) throws IOException {

        ControllerMethodHandler controllerHandler =
                (ControllerMethodHandler) handler;

        try {
            Object result = controllerHandler.getMethod().invoke(
                    controllerHandler.getController()
            );

            response.send(result);

        } catch (IllegalAccessException | InvocationTargetException exception) {
            throw new IOException("Failed to invoke controller method", exception);
        }
    }
}