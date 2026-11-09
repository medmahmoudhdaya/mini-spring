
package com.mini.server;

import com.mini.http.HttpRequest;
import com.mini.http.HttpRequestParser;
import com.mini.http.HttpResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final int port;

    public HttpServer(int port) {
        this.port = port;
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

                        System.out.println("Method: " + request.getMethod());
                        System.out.println("Path: " + request.getPath());
                        System.out.println("Body: " + request.getBody());

                        HttpResponse response = new HttpResponse(
                                clientSocket.getOutputStream()
                        );

                        response.send(
                                "You requested: " + request.getPath()
                        );

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
