package com.mini.server;

import com.mini.dispatcher.Dispatcher;
import com.mini.http.HttpRequest;
import com.mini.http.HttpRequestParser;
import com.mini.http.HttpResponse;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final int port;
    private final Dispatcher dispatcher;

    public HttpServer(int port, Dispatcher dispatcher) {
        this.port = port;
        this.dispatcher = dispatcher;
    }

    public void start() throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server started on port " + port);

            while (true) {

                try (Socket clientSocket = serverSocket.accept()) {

                    try {
                        HttpRequestParser parser = new HttpRequestParser();

                        HttpRequest request = parser.parse(
                                clientSocket.getInputStream()
                        );

                        HttpResponse response = new HttpResponse(
                                clientSocket.getOutputStream()
                        );

                        dispatcher.dispatch(request, response);

                    }catch (EOFException exception) {
                        // Client disconnected without sending a request.
                    } catch (IOException exception) {
                        System.err.println(
                                "Failed to process client request: "
                                        + exception.getMessage()
                        );
                    }
                }
            }
        }
    }
}