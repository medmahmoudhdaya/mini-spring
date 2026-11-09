package com.mini;

import com.mini.server.HttpServer;

public class MiniApplication {

    public static void main(String[] args) throws Exception {

        HttpServer server = new HttpServer(8088);

        server.start();
    }
}