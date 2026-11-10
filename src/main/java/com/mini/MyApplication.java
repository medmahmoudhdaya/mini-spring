package com.mini;

import com.mini.annotation.MiniBootApplication;

@MiniBootApplication
public class MyApplication {

    public static void main(String[] args) {
        try {
            MiniApplication.run(MyApplication.class, args);
        } catch (Exception exception) {
            throw new RuntimeException("Application startup failed", exception);
        }
    }
}