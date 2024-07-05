package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.DefaultListModel;

import model.Appliance;
import model.Employee;
import model.ExtraCost;
import model.Material;

public class SaveData {
    public static void saveProjectData(DefaultListModel<Material> materials, DefaultListModel<Employee> employees, DefaultListModel<Appliance> appliances, DefaultListModel<ExtraCost> extraCosts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\Users\\USER\\Desktop\\java project form\\AppProject.dat"))) {
            oos.writeObject(materials.toArray());
            oos.writeObject(employees.toArray());
            oos.writeObject(appliances.toArray());
            oos.writeObject(extraCosts.toArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProjectData(DefaultListModel<Material> materials, DefaultListModel<Employee> employees, DefaultListModel<Appliance> appliances, DefaultListModel<ExtraCost> extraCosts) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\Users\\USER\\Desktop\\java project form\\AppProject.dat"))) {
            materials.clear();
            employees.clear();
            appliances.clear();
            extraCosts.clear();

            for (Material material : (Material[]) ois.readObject()) {
                materials.addElement(material);
            }
            for (Employee employee : (Employee[]) ois.readObject()) {
                employees.addElement(employee);
            }
            for (Appliance appliance : (Appliance[]) ois.readObject()) {
                appliances.addElement(appliance);
            }
            for (ExtraCost extraCost : (ExtraCost[]) ois.readObject()) {
                extraCosts.addElement(extraCost);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
