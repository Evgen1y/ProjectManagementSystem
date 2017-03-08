package ua.goit.java.dao;

import ua.goit.java.entity.Developer;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface DevelopersDao {

    void addDeveloper (Developer developer);

    void deleteDeveloper (int developerId);

    void updateDeveloper (Developer developer);

    List<Developer> getAllDevelopers();

    Developer getDeveloperById (int developerId);
}
