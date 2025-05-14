package com.example.first_camel_case.model;

public class OracleSource implements SomeSource{
    @Override
    public boolean connect() {
        System.out.println("oracle ___-----");
        return false;
    }
}
