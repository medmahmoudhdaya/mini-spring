package com.mini.dispatcher;

import com.mini.http.HttpRequest;
import com.mini.http.HttpResponse;

import java.io.IOException;

public interface HandlerAdapter {

    boolean supports(Object handler);

    void handle(
            HttpRequest request,
            HttpResponse response,
            Object handler
    ) throws IOException;
}