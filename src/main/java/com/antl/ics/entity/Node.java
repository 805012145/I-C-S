package com.antl.ics.entity;

public class Node {
    public String id;

    public String name;

    public String symbolSize;

    public Double position_x;

    public Double position_y;

    public Integer value;

    public Integer symbol;

    public Integer category;

    public String field;

    public Node() {
    }

    public Node(String id, String name, String symbolSize, Double position_x, Double position_y, Integer value, Integer category, String field, int symbol) {
        this.id = id;
        this.name = name;
        this.symbolSize = symbolSize;
        this.position_x = position_x;
        this.position_y = position_y;
        this.value = value;
        this.category = category;
        this.field = field;
        this.symbol = symbol;
    }

    public Node(String name, String symbolSize, Double position_x, Double position_y, Integer value, Integer category, String field) {
        this.name = name;
        this.symbolSize = symbolSize;
        this.position_x = position_x;
        this.position_y = position_y;
        this.value = value;
        this.category = category;
        this.field = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(String symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Double getPosition_x() {
        return position_x;
    }

    public void setPosition_x(Double position_x) {
        this.position_x = position_x;
    }

    public Double getPosition_y() {
        return position_y;
    }

    public void setPosition_y(Double position_y) {
        this.position_y = position_y;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
