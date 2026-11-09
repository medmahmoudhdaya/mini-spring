package com.mini.handler;

import com.mini.http.HttpRequest;
import com.mini.http.HttpResponse;

import java.io.IOException;

public interface RequestHandler {

    void handle(HttpRequest request, HttpResponse response)
            throws IOException;
}