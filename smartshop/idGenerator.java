package com.example.erikk.smartshop;

/**
 * Created by Erikk on 07/04/2016.
 */
public class idGenerator {
    private int id;
    private static idGenerator ourInstance = new idGenerator();

    public static idGenerator getInstance() {
        return ourInstance;
    }
    private idGenerator() {
        this.id = 0;
    }
    public int getId(){
        return this.id += 1;
    }
}
