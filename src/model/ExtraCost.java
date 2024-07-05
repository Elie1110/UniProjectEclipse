package model;

import java.io.Serializable;

public class ExtraCost implements Serializable {
    private String name;
    private double cost;

    public ExtraCost(String name, double cost) {
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
        return name + " ($" + cost + ")";
    }
}
