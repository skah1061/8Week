package com.example.sparta.oauthproject.exception;

public class ProblemToken extends RuntimeException {
    public ProblemToken(String msg) {
        super(msg);
    }
}
