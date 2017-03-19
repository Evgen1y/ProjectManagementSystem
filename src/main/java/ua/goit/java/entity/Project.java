package ua.goit.java.entity;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class Project {

    private int projectId;
    private String projectName;
    private int companyId;
    private int customerId;
    private int cost;
    private List<Integer> developersId;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Integer> getDevelopersId() {
        return developersId;
    }

    public void setDevelopersId(List<Integer> developersId) {
        this.developersId = developersId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", companyId=" + companyId +
                ", customerId=" + customerId +
                ", cost=" + cost +
                ", developersId=" + developersId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (getProjectId() != project.getProjectId()) return false;
        if (getCompanyId() != project.getCompanyId()) return false;
        if (getCustomerId() != project.getCustomerId()) return false;
        if (getCost() != project.getCost()) return false;
        if (getProjectName() != null ? !getProjectName().equals(project.getProjectName()) : project.getProjectName() != null)
            return false;
        return getDevelopersId() != null ? getDevelopersId().equals(project.getDevelopersId()) : project.getDevelopersId() == null;
    }

    @Override
    public int hashCode() {
        int result = getProjectId();
        result = 31 * result + (getProjectName() != null ? getProjectName().hashCode() : 0);
        result = 31 * result + getCompanyId();
        result = 31 * result + getCustomerId();
        result = 31 * result + getCost();
        result = 31 * result + (getDevelopersId() != null ? getDevelopersId().hashCode() : 0);
        return result;
    }
}
