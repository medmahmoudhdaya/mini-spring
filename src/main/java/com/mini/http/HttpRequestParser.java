
package com.mini.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class HttpRequestParser {

    public HttpRequest parse(InputStream inputStream) throws IOException {

        BufferedInputStream input = new BufferedInputStream(inputStream);

        // 1. Read the request line
        String requestLine = readLine(input);

        if (requestLine == null || requestLine.isBlank()) {
            throw new EOFException("Client closed connection without sending a request");
        }

        String[] requestParts = requestLine.split(" ", 3);

        if (requestParts.length != 3) {
            throw new IOException("Malformed HTTP request line");
        }

        String method = requestParts[0];
        String path = requestParts[1];
        String version = requestParts[2];

        // 2. Read headers
        Map<String, String> headers =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        String line;

        while ((line = readLine(input)) != null && !line.isEmpty()) {

            int separatorIndex = line.indexOf(':');

            if (separatorIndex <= 0) {
                throw new IOException("Malformed HTTP header");
            }

            String name = line.substring(0, separatorIndex).trim();
            String value = line.substring(separatorIndex + 1).trim();

            headers.put(name, value);
        }

        // 3. Read body using Content-Length
        int contentLength = 0;

        if (headers.containsKey("Content-Length")) {
            try {
                contentLength = Integer.parseInt(
                        headers.get("Content-Length")
                );
            } catch (NumberFormatException exception) {
                throw new IOException("Invalid Content-Length");
            }
        }

        if (contentLength < 0) {
            throw new IOException("Invalid Content-Length");
        }

        byte[] bodyBytes = input.readNBytes(contentLength);

        if (bodyBytes.length != contentLength) {
            throw new EOFException("Incomplete HTTP request body");
        }

        String body = new String(bodyBytes, StandardCharsets.UTF_8);

        // 4. Construct HttpRequest
        return new HttpRequest(
                method,
                path,
                version,
                headers,
                body
        );
    }

    private String readLine(InputStream input) throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int currentByte;

        while ((currentByte = input.read()) != -1) {

            if (currentByte == '\n') {
                break;
            }

            if (currentByte != '\r') {
                buffer.write(currentByte);
            }
        }

        if (currentByte == -1 && buffer.size() == 0) {
            return null;
        }

        return buffer.toString(StandardCharsets.ISO_8859_1);
    }
}
