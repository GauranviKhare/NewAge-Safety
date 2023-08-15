package com.example.newagesafety;

public class DataProvider
{
    String name;
    String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    DataProvider(String name, String number)
    {
        this.name=name;
        this.number=number;
    }
}
