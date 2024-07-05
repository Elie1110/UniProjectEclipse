package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Project;
import util.SaveData;

public class MainApp extends JFrame {
    private JTabbedPane tabbedPane;
    private MaterialsPanel materialsPanel;
    private EmployeesPanel employeesPanel;
    private AppliancesPanel appliancesPanel;
    private ExtraCostPanel extraCostPanel;
    private FinancialPanel financialPanel;
    private ProjectsPanel projectsPanel;

    private Project activeProject;

    public MainApp() {
        setTitle("Home Appliances Company Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        projectsPanel = new ProjectsPanel(this);

        add(tabbedPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeProject != null) {
                    SaveData.saveProjectData(materialsPanel.getMaterialListModel(), employeesPanel.getEmployeeListModel(), appliancesPanel.getApplianceListModel(), extraCostPanel.getExtraCostListModel());
                    JOptionPane.showMessageDialog(MainApp.this, "Data saved successfully.");
                } else {
                    JOptionPane.showMessageDialog(MainApp.this, "Please select a project to save.");
                }
            }
        });

        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeProject != null) {
                    SaveData.loadProjectData(materialsPanel.getMaterialListModel(), employeesPanel.getEmployeeListModel(), appliancesPanel.getApplianceListModel(), extraCostPanel.getExtraCostListModel());
                    JOptionPane.showMessageDialog(MainApp.this, "Data loaded successfully.");
                } else {
                    JOptionPane.showMessageDialog(MainApp.this, "Please select a project to load.");
                }
            }
        });

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        setActiveProject(null); // Initialize with no project selected
    }

    public void setActiveProject(Project project) {
        this.activeProject = project;
        tabbedPane.removeAll();

        if (project == null) {
            tabbedPane.addTab("Projects", projectsPanel);
        } else {
            materialsPanel = new MaterialsPanel();
            employeesPanel = new EmployeesPanel();
            appliancesPanel = new AppliancesPanel(materialsPanel.getMaterialListModel());
            extraCostPanel = new ExtraCostPanel();
            financialPanel = new FinancialPanel(materialsPanel, employeesPanel, appliancesPanel, extraCostPanel);

            tabbedPane.addTab("Projects", projectsPanel);
            tabbedPane.addTab("Materials", materialsPanel);
            tabbedPane.addTab("Employees", employeesPanel);
            tabbedPane.addTab("Appliances", appliancesPanel);
            tabbedPane.addTab("Extra Cost", extraCostPanel);
            tabbedPane.addTab("Financial", financialPanel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }
}
