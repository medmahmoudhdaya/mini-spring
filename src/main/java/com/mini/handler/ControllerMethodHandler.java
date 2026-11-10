package com.mini.handler;

import java.lang.reflect.Method;

public class ControllerMethodHandler {

    private final Object controller;
    private final Method method;

    public ControllerMethodHandler(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}