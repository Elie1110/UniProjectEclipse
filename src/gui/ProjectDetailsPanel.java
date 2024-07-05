package gui;

import javax.swing.*;
import model.Project;
import java.awt.*;

public class ProjectDetailsPanel extends JPanel {
    private JLabel projectNameLabel;
    private JTextArea projectDetailsArea;

    public ProjectDetailsPanel(Project project) {
        setLayout(new BorderLayout());

        projectNameLabel = new JLabel("Project: " + project.getProjectName());
        projectDetailsArea = new JTextArea(project.getProjectDetails());
        projectDetailsArea.setEditable(false);

        add(projectNameLabel, BorderLayout.NORTH);
        add(new JScrollPane(projectDetailsArea), BorderLayout.CENTER);
    }

    public void setProject(Project project) {
        projectNameLabel.setText("Project: " + project.getProjectName());
        projectDetailsArea.setText(project.getProjectDetails());
    }
}
