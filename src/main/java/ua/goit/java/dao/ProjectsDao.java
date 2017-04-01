package ua.goit.java.dao;

import ua.goit.java.entity.Project;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface ProjectsDao {

    void save(Project project, List<Integer> developersId);

    void delete(int projectId);

    void update(Project project, List<Integer> developersId);

    List<Project> getAll();

    Project getById(int projectId);
}
