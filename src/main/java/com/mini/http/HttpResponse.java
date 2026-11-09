package com.mini.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private final OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void send(String body) throws IOException {

        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

        String headers =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain; charset=UTF-8\r\n" +
                        "Content-Length: " + bodyBytes.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";

        outputStream.write(headers.getBytes(StandardCharsets.UTF_8));
        outputStream.write(bodyBytes);
        outputStream.flush();
    }
}