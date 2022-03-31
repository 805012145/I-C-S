package com.antl.ics.entity;

public class Switch extends Node{
    public Switch() {
    }

    public Switch(String name, String symbolSize, Double x, Double y,
                  Integer value, Integer category, String field) {
        super(name, symbolSize, x, y, value, category, field);
    }
}
