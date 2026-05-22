package org.example.mybeans;

import java.io.Serializable;

public class Data implements Serializable {
    private String date = null;
    private double x = 0;
    private double y = 0;

    public Data() { }

    public Data(String data) {
        this.date = data;
    }

    public Data(double x, double y, String date) {
        this.x = x;
        this.y = y;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Data copy() {
        return new Data(x, y, date);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f) %s", x, y, date);
    }
}
