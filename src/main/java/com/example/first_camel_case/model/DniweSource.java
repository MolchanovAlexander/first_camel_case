package com.example.first_camel_case.model;

public class DniweSource implements SomeSource{
    private int times;

    @Override
    public boolean connect() {
        System.out.println("dniwe connected");
        return false;
    }
}
