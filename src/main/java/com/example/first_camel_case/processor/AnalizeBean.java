package com.example.first_camel_case.processor;

public class AnalizeBean {

    public boolean isDigit(String body) {
        return body != null && body.matches("\\d+");
    }

    public boolean isWord(String body) {
        return body.matches("([A-Za-z]+( [A-Za-z]+)+)");
    }
}
