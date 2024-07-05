package gui;

import javax.swing.*;

import model.Employee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class EmployeesPanel extends JPanel {
    private DefaultListModel<Employee> employeeListModel;
    private JList<Employee> employeeList;
    private JTextField nameField, hoursField;
    private JComboBox<String> departmentComboBox;
    private Map<String, Double> salaryPerHourMap;

    public EmployeesPanel() {
        setLayout(new BorderLayout());

        employeeListModel = new DefaultListModel<>();
        employeeList = new JList<>(employeeListModel);
        add(new JScrollPane(employeeList), BorderLayout.SOUTH);

        initializeSalaryPerHourMap();
        createInputPanel();
        createButtonPanel();
    }

    private void initializeSalaryPerHourMap() {
        salaryPerHourMap = new HashMap<>();
        salaryPerHourMap.put("HR", 10.0);
        salaryPerHourMap.put("Cleaners", 4.0);
        salaryPerHourMap.put("IT", 8.0);
        salaryPerHourMap.put("Production", 7.0);
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 3, 50,20));

        nameField = new JTextField();
        hoursField = new JTextField();
        departmentComboBox = new JComboBox<>(salaryPerHourMap.keySet().toArray(new String[0]));

        inputPanel.add(new JLabel("Employee Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Hours Worked:"));
        inputPanel.add(hoursField);
        inputPanel.add(new JLabel("Department:"));
        inputPanel.add(departmentComboBox);
        inputPanel.add(new JLabel("To add a new employee, enter their name, hours worked, and department, then click \"Add New Employee\"."));
        inputPanel.add(new JLabel("The employee's salary will be computed and shown in the list below."));
        add(inputPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add New Employee");
        JButton deleteButton = new JButton("Delete Employee");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.NORTH);

        addButton.addActionListener(new AddButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(EmployeesPanel.this, "Employee name cannot be empty.");
                return;
            }

            int hoursWorked;
            try {
                hoursWorked = Integer.parseInt(hoursField.getText());
                if (hoursWorked < 0) {
                    JOptionPane.showMessageDialog(EmployeesPanel.this, "Hours worked must be positive.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(EmployeesPanel.this, "Invalid hours worked.");
                return;
            }

            String department = (String) departmentComboBox.getSelectedItem();
            double salaryPerHour = salaryPerHourMap.getOrDefault(department, 0.0);
            double salary = salaryPerHour * hoursWorked;

            employeeListModel.addElement(new Employee(name, department, hoursWorked, salary));
            clearInputFields();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee selectedEmployee = employeeList.getSelectedValue();
            if (selectedEmployee != null) {
                employeeListModel.removeElement(selectedEmployee);
            }
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        hoursField.setText("");
        departmentComboBox.setSelectedIndex(0);
    }

    public DefaultListModel<Employee> getEmployeeListModel() {
        return employeeListModel;
    }
}
