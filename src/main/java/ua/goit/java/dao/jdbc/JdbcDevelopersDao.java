package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.console.table.DevelopersConsole;
import ua.goit.java.dao.SkillsDao;
import ua.goit.java.entity.Developer;
import ua.goit.java.dao.DevelopersDao;
import ua.goit.java.entity.Skill;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */

public class JdbcDevelopersDao implements DevelopersDao {

    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDevelopersDao.class);
    private SkillsDao skillsDao;
    private DevelopersConsole developersConsole;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void save(Developer developer, List<String> skills) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO developers VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, developer.getDeveloperId());
            statement.setString(2, developer.getName());
            statement.setString(3, developer.getSurname());
            statement.setInt(4, developer.getSalary());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                developer.setDeveloperId(resultSet.getInt(1));
                System.out.println("We are getting id of developer: " + developer.getDeveloperId());
            }else{
                System.out.println("We can't get developer id!!!");
            }
            addSkillsToDeveloper(developer, skills);
            LOGGER.info("In table Companies was saved " + developer);
        } catch(SQLException e){
            LOGGER.error("Something wrong with saving developer in developers");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(int developerId) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM developers WHERE developer_id = ?")){
            statement.setInt(1, developerId);
            deleteSkillsFromDeveloper(developerId);
            deleteDeveloperFromProject(developerId);
            statement.execute();
            LOGGER.info("From table Developers was deleting developer with id = " + developerId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete developer in developers");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void update(Developer developer, List<String> skills) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE developers SET name = ?, salary = ?, surname = ? WHERE developer_id = ?")){
            statement.setString(1, developer.getName());
            statement.setInt(2, developer.getSalary());
            statement.setString(3, developer.getSurname());
            statement.setInt(4, developer.getDeveloperId());
            statement.execute();
            updateSkillsFromDeveloper(developer, skills);
            LOGGER.info("In table Developers was updating developer with id = " + developer.getDeveloperId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating developer in developers");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public List<Developer> getAll() {
        List<Developer> developers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection
                    .createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM developers");
            while(resultSet.next()) {
                developers.add(createDeveloper(resultSet));
            }
            LOGGER.info("All developers was received");
        } catch(SQLException e){
            LOGGER.error("Something wrong with receiving of all developers");
        }
        return developers;    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Developer getById(int developerId) {
        Developer developer = new Developer();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT developer_id, name, salary, surname FROM developers " +
                            "WHERE developer_id = ?")){
            statement.setInt(1, developerId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                developer = createDeveloper(resultSet);
                LOGGER.info("Developer with id = " + developerId + " is received");
            }
        } catch(SQLException e){
            LOGGER.error("Something wrong with getting developer from developers with id = " + developerId);
        }

        if(developer.equals(new Developer())){
            System.out.println("Developer with this id doesn't exist.\nPlease, try again");
            developersConsole.question(developersConsole);
        }

        return developer;
    }

    private Developer createDeveloper(ResultSet resultSet) throws SQLException{
        Developer developer = new Developer();
        developer.setDeveloperId(resultSet.getInt("developer_id"));
        developer.setName(resultSet.getString("name"));
        developer.setSalary(resultSet.getInt("salary"));
        developer.setSurname(resultSet.getString("surname"));
        findDeveloperSkills(developer);
        return developer;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    private void addSkillsToDeveloper(Developer developer,List<String> skills) throws SQLException {
        int id = developer.getDeveloperId();
        for (String skill : skills) {
            for (Skill skill1 : skillsDao.getAll()) {
                if (skill1.getSkillName().equals(skill)) {
                    developer.addSkill(skill1);
                    try (Connection connection = dataSource.getConnection();
                         PreparedStatement statement = connection
                                 .prepareStatement("INSERT INTO developer_skill VALUES (?, ?)")) {
                        statement.setInt(1, id);
                        statement.setInt(2, skill1.getSkillId());
                        statement.execute();
                        LOGGER.info("In table Developer_skill was add developer id: " + id
                                + ", developer skill id: " + skill1.getSkillId());
                    } catch (SQLException e) {
                        LOGGER.error("Something wrong with add skill in developer_skill");
                        throw e;
                    }
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    private void updateSkillsFromDeveloper(Developer developer, List<String> skills){
        try {
            deleteSkillsFromDeveloper(developer.getDeveloperId());
            addSkillsToDeveloper(developer,skills);
            LOGGER.info("Skills of developer are updating");
        } catch (SQLException e) {
            LOGGER.error("Something wrong with updating skills from developer");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    private void deleteSkillsFromDeveloper(int developerId) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM developer_skill WHERE developer_id = ?")){
            statement.setInt(1, developerId);
            statement.execute();
            LOGGER.error("From table Developer_skill was delete skills of developer id: " + developerId);
        }catch (SQLException e){
            LOGGER.error("Something wrong with deleting skill from developer");
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    private void deleteDeveloperFromProject(int developerId){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM developer_project WHERE developer_id = ?")){
            statement.setInt(1, developerId);
            statement.execute();
            LOGGER.info("From developer_project was deleting developer with id: " + developerId);
        }catch (SQLException e){
            LOGGER.error("Something wrong with deleting developer from project");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    private void findDeveloperSkills(Developer developer){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT s.skill_id, s.skill_name FROM skills s " +
                            "INNER JOIN developer_skill d ON s.skill_id = d.skill_id " +
                            "WHERE developer_id = ?")){
            statement.setInt(1, developer.getDeveloperId());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Skill skill = new Skill();
                skill.setSkillId(resultSet.getInt("skill_id"));
                skill.setSkillName(resultSet.getString("skill_name"));
                developer.addSkill(skill);
                LOGGER.info("Add skill: " + skill.getSkillName() + ", to developer, id: " + developer.getDeveloperId());
            }

        } catch (SQLException e){
            LOGGER.error("Something wrong with finding developer skills");
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSkillsDao(SkillsDao skillsDao) {
        this.skillsDao = skillsDao;
    }

    public void setDevelopersConsole(DevelopersConsole developersConsole) {
        this.developersConsole = developersConsole;
    }
}
