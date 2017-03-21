package ua.goit.java.dao;

import ua.goit.java.entity.Skill;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface SkillsDao {

    void save(Skill skill);

    void delete(int skillId);

    void update(Skill skill);

    List<Skill> getAll();

    Skill getById(int skillId);
}
