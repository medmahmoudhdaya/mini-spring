
package com.mini.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

    private final OutputStream outputStream;

    private int statusCode = 200;

    private final Map<String, String> headers = new LinkedHashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setContentType(String contentType) {
        setHeader("Content-Type", contentType);
    }

    public <T> void send(T body) throws IOException {

        byte[] bodyBytes = String.valueOf(body).getBytes(StandardCharsets.UTF_8);

        StringBuilder responseHeaders = new StringBuilder();

        // Status line
        responseHeaders.append("HTTP/1.1 ")
                .append(statusCode)
                .append(" ")
                .append(getReasonPhrase(statusCode))
                .append("\r\n");

        // Default Content-Type
        if (!headers.containsKey("Content-Type")) {
            headers.put("Content-Type", "text/plain; charset=UTF-8");
        }

        // Custom headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            responseHeaders.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }

        // Automatically generated headers
        responseHeaders.append("Content-Length: ")
                .append(bodyBytes.length)
                .append("\r\n");

        responseHeaders.append("Connection: close\r\n");

        // End of headers
        responseHeaders.append("\r\n");

        outputStream.write(
                responseHeaders.toString().getBytes(StandardCharsets.UTF_8)
        );

        // Response body
        outputStream.write(bodyBytes);

        outputStream.flush();
    }

    private String getReasonPhrase(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 201 -> "Created";
            case 204 -> "No Content";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Unknown";
        };
    }
}
