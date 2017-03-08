package ua.goit.java.dao;

import ua.goit.java.entity.Skill;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface SkillsDao {

    void addSkill (Skill skill);

    void deleteSkill (int skillId);

    void updateSkill (Skill skill);

    List<Skill> getAllSkills();

    Skill getSkillById (int skillId);
}
