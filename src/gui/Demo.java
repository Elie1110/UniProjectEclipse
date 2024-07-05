package gui;

import javax.swing.*;
import model.Appliance;
import model.Employee;
import model.ExtraCost;
import model.Material;
import java.awt.BorderLayout;

public class Demo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Home Appliances Project - Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JTabbedPane tabbedPane = new JTabbedPane();

            MaterialsPanel materialsPanel = new MaterialsPanel();
            EmployeesPanel employeesPanel = new EmployeesPanel();
            AppliancesPanel appliancesPanel = new AppliancesPanel(materialsPanel.getMaterialListModel());
            ExtraCostPanel extraCostPanel = new ExtraCostPanel();
            FinancialPanel financialPanel = new FinancialPanel(materialsPanel, employeesPanel, appliancesPanel, extraCostPanel);

            tabbedPane.addTab("Materials", materialsPanel);
            tabbedPane.addTab("Employees", employeesPanel);
            tabbedPane.addTab("Appliances", appliancesPanel);
            tabbedPane.addTab("Extra Cost", extraCostPanel);
            tabbedPane.addTab("Financial", financialPanel);

            frame.add(tabbedPane, BorderLayout.CENTER);
            frame.setVisible(true);

            // Adding random demo data
            addRandomData(materialsPanel, employeesPanel, appliancesPanel, extraCostPanel);
        });
    }

    private static void addRandomData(MaterialsPanel materialsPanel, EmployeesPanel employeesPanel, AppliancesPanel appliancesPanel, ExtraCostPanel extraCostPanel) {
        // Adding materials
        materialsPanel.getMaterialListModel().addElement(new Material("Wood", 10.0));
        materialsPanel.getMaterialListModel().addElement(new Material("Metal", 12.0));
        materialsPanel.getMaterialListModel().addElement(new Material("Plastic", 8.0));

        // Adding employees
        employeesPanel.getEmployeeListModel().addElement(new Employee("John", "HR", 40, 400));
        employeesPanel.getEmployeeListModel().addElement(new Employee("Jane", "IT", 35, 280));
        employeesPanel.getEmployeeListModel().addElement(new Employee("Doe", "Cleaners", 30, 120));

        // Adding appliances
        DefaultListModel<Material> selectedMaterials = new DefaultListModel<>();
        selectedMaterials.addElement(materialsPanel.getMaterialListModel().getElementAt(0));
        appliancesPanel.getApplianceListModel().addElement(new Appliance("Table", 5, selectedMaterials));

        selectedMaterials = new DefaultListModel<>();
        selectedMaterials.addElement(materialsPanel.getMaterialListModel().getElementAt(1));
        appliancesPanel.getApplianceListModel().addElement(new Appliance("Chair", 10, selectedMaterials));

        // Adding extra costs
        extraCostPanel.getExtraCostListModel().addElement(new ExtraCost("Shipping", 50));
        extraCostPanel.getExtraCostListModel().addElement(new ExtraCost("Packaging", 30));
    }
}
