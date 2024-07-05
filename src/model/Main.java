package model;

import javax.swing.*;
import java.awt.*;

import gui.AppliancesPanel;
import gui.EmployeesPanel;
import gui.ExtraCostPanel;
import gui.FinancialPanel;
import gui.MaterialsPanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Home Appliances Project");
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
        });
    }
}
