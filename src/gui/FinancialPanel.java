package gui;

import javax.swing.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FinancialPanel extends JPanel {
    private JTextArea financialDetailsArea;
    private JButton calculateButton, openFormButton, saveButton;

    private EmployeesPanel employeesPanel;
    private AppliancesPanel appliancesPanel;
    private ExtraCostPanel extraCostPanel;

    public FinancialPanel(MaterialsPanel materialsPanel, EmployeesPanel employeesPanel, AppliancesPanel appliancesPanel, ExtraCostPanel extraCostPanel) {
        this.employeesPanel = employeesPanel;
        this.appliancesPanel = appliancesPanel;
        this.extraCostPanel = extraCostPanel;

        setLayout(new BorderLayout());

        financialDetailsArea = new JTextArea();
        financialDetailsArea.setEditable(false);
        add(new JScrollPane(financialDetailsArea), BorderLayout.CENTER);

        createButtonPanel();
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        calculateButton = new JButton("Calculate");
        openFormButton = new JButton("Open Form");
        saveButton = new JButton("Save Data");
        buttonPanel.add(new JLabel("To Display the Profit and Loss:"));

        buttonPanel.add(calculateButton);
        buttonPanel.add(openFormButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.NORTH);

        calculateButton.addActionListener(new CalculateButtonListener());
        openFormButton.addActionListener(new OpenFormButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculateFinancials();
        }
    }

    private class OpenFormButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            openForm();
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveData();
        }
    }

    private void calculateFinancials() {
        double totalCost = calculateTotalCost();
        double revenue = totalCost * 1.8;
        double profit = revenue - totalCost;

        financialDetailsArea.setText("");
        financialDetailsArea.append("Project Financial Details:\n");
        financialDetailsArea.append("Total Cost: $" + totalCost + "\n");
        financialDetailsArea.append("Revenue: $" + revenue + "\n");
        financialDetailsArea.append("Profit: $" + profit + "\n");
        financialDetailsArea.append("Date: " + new Date() + "\n");
    }

    private double calculateTotalCost() {
        double totalCost = 0.0;
        for (int i = 0; i < employeesPanel.getEmployeeListModel().getSize(); i++) {
            totalCost += employeesPanel.getEmployeeListModel().getElementAt(i).getSalary();
        }
        for (int i = 0; i < appliancesPanel.getApplianceListModel().getSize(); i++) {
            totalCost += appliancesPanel.getApplianceListModel().getElementAt(i).getTotalCost();
        }
        for (int i = 0; i < extraCostPanel.getExtraCostListModel().getSize(); i++) {
            totalCost += extraCostPanel.getExtraCostListModel().getElementAt(i).getCost();
        }
        return totalCost;
    }

    private void openForm() {
        // Implementation to open a Word file containing project details
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AppProject.dat"))) {
            oos.writeObject(employeesPanel.getEmployeeListModel());
            oos.writeObject(appliancesPanel.getApplianceListModel());
            oos.writeObject(extraCostPanel.getExtraCostListModel());
            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Save Data", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save data: " + ex.getMessage(), "Save Data Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
