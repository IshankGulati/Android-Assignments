package com.carwale.aepl.assignment5;

/**
 * Created by aepl on 28/6/16.
 */
public class City implements java.io.Serializable{
    String name;
    int id;
    int count;

    public City(String name, int id, int count){
        this.name = name;
        this.id = id;
        this.count = count;
    }
}
