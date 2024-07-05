package gui;

import javax.swing.*;
import model.Appliance;
import model.Material;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;

public class AppliancesPanel extends JPanel {
    private DefaultListModel<Appliance> applianceListModel;
    private JList<Appliance> applianceList;
    private JTextField nameField, quantityField;
    private JList<Material> materialsList;

    public AppliancesPanel(DefaultListModel<Material> materialsListModel) {
        setLayout(new BorderLayout());

        applianceListModel = new DefaultListModel<>();
        applianceList = new JList<>(applianceListModel);
        add(new JScrollPane(applianceList), BorderLayout.SOUTH);

        createInputPanel(materialsListModel);
        createButtonPanel();
    }

    private void createInputPanel(DefaultListModel<Material> materialsListModel) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 3, 20,20));

        nameField = new JTextField();
        quantityField = new JTextField();
        materialsList = new JList<>(materialsListModel);
        materialsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        inputPanel.add(new JLabel("Appliance Name:"));
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(new JLabel("Materials:"));
        inputPanel.add(nameField);
        inputPanel.add(quantityField);
        inputPanel.add(new JScrollPane(materialsList));
        inputPanel.add(new JLabel("Step1: Create Appliance from Materials"));
        inputPanel.add(new JLabel("Step2: Input Materials Qty"));
        inputPanel.add(new JLabel("Step3: Click Print For Invoice"));

        add(inputPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Appliance");
        JButton deleteButton = new JButton("Delete Appliance");
        JButton printButton = new JButton("Print"); 

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton); 

        add(buttonPanel, BorderLayout.NORTH);

        addButton.addActionListener(new AddButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
        printButton.addActionListener(new PrintButtonListener()); // Listener for print button
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            int quantity;

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(AppliancesPanel.this, "Appliance name cannot be empty.");
                return;
            }

            try {
                quantity = Integer.parseInt(quantityField.getText());
                if (quantity < 0) {
                    JOptionPane.showMessageDialog(AppliancesPanel.this, "Quantity must be positive.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AppliancesPanel.this, "Invalid quantity.");
                return;
            }

            DefaultListModel<Material> selectedMaterials = new DefaultListModel<>();
            for (Material material : materialsList.getSelectedValuesList()) {
                selectedMaterials.addElement(material);
            }

            applianceListModel.addElement(new Appliance(name, quantity, selectedMaterials));
            clearInputFields();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Appliance selectedAppliance = applianceList.getSelectedValue();
            if (selectedAppliance != null) {
                applianceListModel.removeElement(selectedAppliance);
            }
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        quantityField.setText("");
        materialsList.clearSelection();
    }

    private class PrintButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new PrintableContent(applianceListModel));
    
            if (job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AppliancesPanel.this, "Printing error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    static class PrintableContent implements Printable {
        private DefaultListModel<Appliance> applianceListModel;
    
        public PrintableContent(DefaultListModel<Appliance> applianceListModel) {
            this.applianceListModel = applianceListModel;
        }
    
        @Override
        public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }
    
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
    
            // Set up font metrics for calculating text dimensions
            FontMetrics metrics = g2d.getFontMetrics();
    
            // Define column positions
            int xApplianceName = 50;
            int xMaterialName = 150;
            int xQuantity = 250;
            int xMaterialCost = 350;
            int xTotalCost = 450;
    
            // Print header
            g.drawString("Appliance Name", xApplianceName, 100);
            g.drawString("Material Name", xMaterialName, 100);
            g.drawString("Quantity", xQuantity, 100);
            g.drawString("Material Cost/unit", xMaterialCost, 100);
            g.drawString("Total Cost", xTotalCost, 100);
    
            // Print each appliance's details
            int y = 120; // Starting Y position for content
            for (int i = 0; i < applianceListModel.size(); i++) {
                Appliance appliance = applianceListModel.getElementAt(i);
                String materialDetails = appliance.getMaterialDetails();
    
                // Split material details for multiline printing
                String[] materialLines = materialDetails.split("\n");
                for (String line : materialLines) {
                    String[] parts = line.split(", ");
                    String applianceName = parts[0];
                    String materialName = parts[1];
                    String qty = parts[2];
                    String materialCost = parts[3];
                    String applianceTotalCost = parts[4];
    
                    // Print appliance details
                    g.drawString(applianceName, xApplianceName, y);
                    g.drawString(materialName, xMaterialName, y);
                    g.drawString(qty, xQuantity, y);
                    g.drawString(materialCost, xMaterialCost, y);
                    g.drawString(applianceTotalCost, xTotalCost, y);
    
                    y += metrics.getHeight(); // Move to the next row
                }
            }
    
            return PAGE_EXISTS;
        }
    }

    public DefaultListModel<Appliance> getApplianceListModel() {
        return applianceListModel;
    }
}
