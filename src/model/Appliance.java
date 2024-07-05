package model;

import java.io.Serializable;
import javax.swing.DefaultListModel;

public class Appliance implements Serializable {
    private String name;
    private int quantity;
    private DefaultListModel<Material> materials;

    public Appliance(String name, int quantity, DefaultListModel<Material> materials) {
        this.name = name;
        this.quantity = quantity;
        this.materials = materials;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public DefaultListModel<Material> getMaterials() {
        return materials;
    }

    public double getTotalCost() {
        double cost = 0.0;
        for (int i = 0; i < materials.getSize(); i++) {
            Material material = materials.getElementAt(i);
            cost += material.getCost();
        }
        return cost * quantity;
    }

     // Method to get Material details for print preview
     public String getMaterialDetails() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < materials.getSize(); i++) {
            Material material = materials.getElementAt(i);
            sb.append(name).append(", ")
              .append(material.getName()).append(", ")
              .append(quantity).append(", ")
              .append(material.getCost()).append(", ")
              .append(getTotalCost()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return name + " (Qty: " + quantity + "), Total Cost: $" + getTotalCost();
    }
}