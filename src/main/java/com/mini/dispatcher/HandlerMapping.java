package com.mini.dispatcher;

import com.mini.http.HttpRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HandlerMapping {

    private final Map<String, Object> handlers = new HashMap<>();

    public void register(String method, String path, Object handler) {
        handlers.put(createKey(method, path), handler);
    }

    public Object getHandler(HttpRequest request) {

        String key = createKey(
                request.getMethod(),
                request.getPath()
        );

        return handlers.get(key);
    }

    private String createKey(String method, String path) {
        return method.toUpperCase(Locale.ROOT) + " " + path;
    }
}