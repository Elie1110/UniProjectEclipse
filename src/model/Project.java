package model;

import java.io.Serializable;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private String projectName;
    private String projectDetails;

    public Project(String projectName) {
        this.projectName = projectName;
        this.projectDetails = "";
    }

    public Project(String projectName, String projectDetails) {
        this.projectName = projectName;
        this.projectDetails = projectDetails;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    @Override
    public String toString() {
        return projectName;
    }
}
