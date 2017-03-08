package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.entity.ConnectionFactory;
import ua.goit.java.entity.Skill;
import ua.goit.java.dao.SkillsDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcSkillsDao implements SkillsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSkillsDao.class);


    @Override
    public void addSkill(Skill skill) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("INSERT INTO skills VALUES (?, ?)");
            statement.setInt(1, skill.getSkillId());
            statement.setString(2, skill.getSkillName());
            statement.execute();
            LOGGER.info("In table Skills was added " + skill);
        } catch(SQLException e){
            LOGGER.error("Something wrong with add skill in skills");
        }
    }

    @Override
    public void deleteSkill(int skillId) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("DELETE FROM skills WHERE skill_id = ?");
            statement.setInt(1, skillId);
            statement.execute();
            LOGGER.info("From table Skills was deleting skill with id = " + skillId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete skill in skills");
        }
    }

    @Override
    public void updateSkill(Skill skill) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("UPDATE skills SET skill_name = ? WHERE skill_id = ?");
            statement.setString(1, skill.getSkillName());
            statement.setInt(2, skill.getSkillId());
            statement.execute();
            LOGGER.info("In table Skills was updating skill with id = " + skill.getSkillId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating skill in skills");
        }

    }

    @Override
    public List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        try{
            Statement statement = new ConnectionFactory().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM skills");
            while(resultSet.next()) {
                skills.add(createSkill(resultSet));
            }
            LOGGER.info("All skills was received");
        } catch(SQLException e){
            LOGGER.error("Something wrong with receiving of all skills");
        }
        return skills;
    }

    @Override
    public Skill getSkillById(int skillId) {
        Skill skill = new Skill();
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("SELECT skill_id, skill_name FROM skills " +
                            "WHERE skill_id = ?");
            statement.setInt(1, skillId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                skill.setSkillName(resultSet.getString("skill_name"));
                skill.setSkillId(resultSet.getInt("skill_id"));
                LOGGER.info("Skill with id = " + skillId + " is received");
            }
        } catch(SQLException e){
            LOGGER.error("Something wrong with getting skill from skills with id = " + skillId);
        }
        return skill;
    }

    private Skill createSkill(ResultSet resultSet) throws SQLException{
        Skill skill = new Skill();
        skill.setSkillId(resultSet.getInt("skill_id"));
        skill.setSkillName(resultSet.getString("skill_name"));
        return skill;
    }

}
