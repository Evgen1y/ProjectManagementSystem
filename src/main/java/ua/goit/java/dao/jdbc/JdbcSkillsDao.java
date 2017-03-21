package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.console.table.SkillsConsole;
import ua.goit.java.entity.Skill;
import ua.goit.java.dao.SkillsDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcSkillsDao implements SkillsDao {

    private DataSource dataSource;
    private SkillsConsole skillsConsole;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSkillsDao.class);


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Skill skill) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO skills VALUES (?, ?)")){
            statement.setInt(1, skill.getSkillId());
            statement.setString(2, skill.getSkillName());
            statement.execute();
            LOGGER.info("In table Skills was saved " + skill);
        } catch(SQLException e){
            LOGGER.error("Something wrong with saving skill in skills");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(int skillId) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                .prepareStatement("DELETE FROM skills WHERE skill_id = ?")){
            statement.setInt(1, skillId);
            statement.execute();
            LOGGER.info("From table Skills was deleting skill with id = " + skillId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete skill in skills");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Skill skill) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE skills SET skill_name = ? WHERE skill_id = ?")){
            statement.setString(1, skill.getSkillName());
            statement.setInt(2, skill.getSkillId());
            statement.execute();
            LOGGER.info("In table Skills was updating skill with id = " + skill.getSkillId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating skill in skills");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
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
    @Transactional(propagation = Propagation.REQUIRED)
    public Skill getById(int skillId) {
        Skill skill = new Skill();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT skill_id, skill_name FROM skills " +
                            "WHERE skill_id = ?")){
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

        if(skill.equals(new Skill())){
            System.out.println("Skill with this id doesn't exist.\nPlease, try again");
            skillsConsole.question(skillsConsole);
        }

        return skill;
    }

    private Skill createSkill(ResultSet resultSet) throws SQLException{
        Skill skill = new Skill();
        skill.setSkillId(resultSet.getInt("skill_id"));
        skill.setSkillName(resultSet.getString("skill_name"));
        return skill;
    }

    public void setSkillsConsole(SkillsConsole skillsConsole) {
        this.skillsConsole = skillsConsole;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
