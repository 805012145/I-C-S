package com.antl.ics.entity;

public class Host extends Node{
    private String switch_id;

    private String ip;

    public Host() {
    }

    public Host(String name, String symbolSize, Double x, Double y,
                Integer value, Integer category, String field, String switch_id, String ip) {
        super(name, symbolSize, x, y, value, category, field);
        this.switch_id = switch_id;
        this.ip = ip;
    }

    public String getSwitch_id() {
        return switch_id;
    }

    public void setSwitch_id(String switch_id) {
        this.switch_id = switch_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
