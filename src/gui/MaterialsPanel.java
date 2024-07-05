package gui;

import javax.swing.*;

import model.Material;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaterialsPanel extends JPanel {
    private DefaultListModel<Material> materialListModel;
    private JList<Material> materialList;
    private JTextField nameField, costField;

    public MaterialsPanel() {
        setLayout(new BorderLayout());

        materialListModel = new DefaultListModel<>();
        materialList = new JList<>(materialListModel);
        add(new JScrollPane(materialList), BorderLayout.SOUTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 3, 50, 20));

        nameField = new JTextField();
        costField = new JTextField();

        inputPanel.add(new JLabel("Material Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Cost/Unit:"));
        inputPanel.add(costField);
        inputPanel.add(new JLabel("To add new materials, enter the name and cost in dollars, then click \"Add New Material\". The material will appear in the list below."));

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add New Material");
        JButton deleteButton = new JButton("Delete Material");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.NORTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String costStr = costField.getText().trim();
                
                // Check if both fields are empty
                if (name.isEmpty() && costStr.isEmpty()) {
                    JOptionPane.showMessageDialog(MaterialsPanel.this, "Please enter Material Name and Cost.");
                    return;
                }
                
                // Check if either field is empty
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(MaterialsPanel.this, "Please enter Material Name.");
                    return;
                }
                
                if (costStr.isEmpty()) {
                    JOptionPane.showMessageDialog(MaterialsPanel.this, "Please enter Material Cost.");
                    return;
                }
                
                // Parse cost
                double cost;
                try {
                    cost = Double.parseDouble(costStr);
                    if (cost < 0 || cost > 12) {
                        JOptionPane.showMessageDialog(MaterialsPanel.this, "Cost must be between 0 and 12.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MaterialsPanel.this, "Invalid cost format.");
                    return;
                }
                
                // Add material to the list
                materialListModel.addElement(new Material(name, cost));
                nameField.setText("");
                costField.setText("");
            }
        });
        

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Material selectedMaterial = materialList.getSelectedValue();
                if (selectedMaterial != null) {
                    materialListModel.removeElement(selectedMaterial);
                }
            }
        });
    }

    public DefaultListModel<Material> getMaterialListModel() {
        return materialListModel;
    }
}
