package com.antl.ics.entity;

import java.util.Objects;

public class PieChart {
    private int value;
    private int name;

    public PieChart() {
    }

    public PieChart(int value, int name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PieChart{" +
                "value=" + value +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieChart pieChart = (PieChart) o;
        return value == pieChart.value && name == pieChart.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }
}
