package gui;

import javax.swing.*;

import model.ExtraCost;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtraCostPanel extends JPanel {
    private DefaultListModel<ExtraCost> extraCostListModel;
    private JList<ExtraCost> extraCostList;
    private JTextField nameField, costField;

    public ExtraCostPanel() {
        setLayout(new BorderLayout());

        extraCostListModel = new DefaultListModel<>();
        extraCostList = new JList<>(extraCostListModel);
        add(new JScrollPane(extraCostList), BorderLayout.SOUTH);

        createInputPanel();
        createButtonPanel();
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 3, 50, 20));

        nameField = new JTextField();
        costField = new JTextField();

        inputPanel.add(new JLabel("Extra Cost Description:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Cost:"));
        inputPanel.add(costField);
        inputPanel.add(new JLabel("To add additional costs, enter the description and amount, then click \"Add Extra Cost\". The added costs will display in the list below."));
        add(inputPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Extra Cost");
        JButton deleteButton = new JButton("Delete Extra Cost");

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
                JOptionPane.showMessageDialog(ExtraCostPanel.this, "Extra cost name cannot be empty.");
                return;
            }

            double cost;
            try {
                cost = Double.parseDouble(costField.getText());
                if (cost < 0) {
                    JOptionPane.showMessageDialog(ExtraCostPanel.this, "Cost must be positive.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ExtraCostPanel.this, "Invalid cost.");
                return;
            }

            extraCostListModel.addElement(new ExtraCost(name, cost));
            clearInputFields();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ExtraCost selectedExtraCost = extraCostList.getSelectedValue();
            if (selectedExtraCost != null) {
                extraCostListModel.removeElement(selectedExtraCost);
            }
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        costField.setText("");
    }

    public DefaultListModel<ExtraCost> getExtraCostListModel() {
        return extraCostListModel;
    }
}
