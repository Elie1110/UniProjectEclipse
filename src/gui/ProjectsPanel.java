package gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import model.Project;

public class ProjectsPanel extends JPanel {
    private MainApp mainApp;
    private DefaultListModel<Project> projectListModel;
    private JList<Project> projectList;
    private JTextField projectNameField;
    private JButton addButton, deleteButton, selectButton;

    public ProjectsPanel(MainApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        projectListModel = new DefaultListModel<>();
        projectList = new JList<>(projectListModel);

        // Input panel with vertical layout and padding
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        JPanel inputFieldsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputFieldsPanel.add(new JLabel("Project Name:"));
        inputFieldsPanel.add(projectNameField = new JTextField());
        inputPanel.add(inputFieldsPanel, BorderLayout.NORTH);

        // Button panel with vertical layout and padding
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        JPanel buttonFieldsPanel = new JPanel(new GridLayout(3, 50, 50, 20));
        buttonFieldsPanel.add(addButton = new JButton("Add Project"));
        buttonFieldsPanel.add(deleteButton = new JButton("Delete Project"));
        buttonFieldsPanel.add(selectButton = new JButton("Select Project"));
        buttonFieldsPanel.add(new JLabel("Step 1: Add Project"));
        buttonFieldsPanel.add(new JLabel("Step 2: Choose from the List"));
        buttonFieldsPanel.add(new JLabel("Step 3: Click Select Project"));
        buttonFieldsPanel.add(new JLabel(""));
        buttonPanel.add(buttonFieldsPanel, BorderLayout.NORTH);

        add(new JScrollPane(projectList), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.NORTH);

        // Adding button listeners
        addButton.addActionListener(e -> addProject());
        deleteButton.addActionListener(e -> deleteProject());
        selectButton.addActionListener(e -> selectProject());

        // Disable delete and select buttons initially
        updateButtonStates();

        // Add a listener to the project list model to monitor changes
        projectListModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                updateButtonStates();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                updateButtonStates();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                updateButtonStates();
            }
        });
    }

    private void addProject() {
        String name = projectNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the project name before adding a project.", "Hint", JOptionPane.INFORMATION_MESSAGE);
        } else {
            projectListModel.addElement(new Project(name));
            projectNameField.setText("");
        }
    }

    private void deleteProject() {
        int selectedIndex = projectList.getSelectedIndex();
        if (selectedIndex != -1) {
            projectListModel.remove(selectedIndex);
        }
    }

    private void selectProject() {
        Project selectedProject = projectList.getSelectedValue();
        if (selectedProject != null) {
            mainApp.setActiveProject(selectedProject);
        }
    }

    private void updateButtonStates() {
        boolean hasProjects = !projectListModel.isEmpty();
        deleteButton.setEnabled(hasProjects);
        selectButton.setEnabled(hasProjects);
    }
}
