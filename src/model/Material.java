package model;

import java.io.Serializable;

public class Material implements Serializable {
    private String name;
    private double cost;

    public Material(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " ($" + cost + "/unit)";
    }
}
