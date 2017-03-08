package ua.goit.java.dao;

import ua.goit.java.entity.Project;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface ProjectsDao {

    void addProject (Project project);

    void deleteProject (int projectId);

    void updateProject (Project project);

    List<Project> getAllProjects();

    Project getProjectById (int projectId);
}
