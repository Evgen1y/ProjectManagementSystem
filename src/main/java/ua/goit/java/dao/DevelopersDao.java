package ua.goit.java.dao;

import ua.goit.java.entity.Developer;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface DevelopersDao {

    void save(Developer developer, List<String> skills);

    void delete(int developerId);

    void update(Developer developer, List<String> skills);

    List<Developer> getAll();

    Developer getById(int developerId);
}
