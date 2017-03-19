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
    @Transactional(propagation = Propagation.REQUIRED)
    public void addDeveloper(Developer developer) {
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
            addSkillsToDeveloper(developer);
            LOGGER.info("In table Companies was added " + developer);
        } catch(SQLException e){
            LOGGER.error("Something wrong with add developer in developers");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteDeveloper(int developerId) {
        deleteSkillsFromDeveloper(developerId);
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM developers WHERE developer_id = ?")){
            statement.setInt(1, developerId);
            statement.execute();
            LOGGER.info("From table Developers was deleting developer with id = " + developerId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete developer in developers");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDeveloper(Developer developer) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE developers SET name = ?, salary = ?, surname = ? WHERE developer_id = ?")){
            statement.setString(1, developer.getName());
            statement.setInt(2, developer.getSalary());
            statement.setString(3, developer.getSurname());
            statement.setInt(4, developer.getDeveloperId());
            statement.execute();
            updateSkillsFromDeveloper(developer);
            LOGGER.info("In table Developers was updating developer with id = " + developer.getDeveloperId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating developer in developers");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Developer> getAllDevelopers() {
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
    @Transactional(propagation = Propagation.REQUIRED)
    public Developer getDeveloperById(int developerId) {
        Developer developer = new Developer();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT developer_id, name, salary, surname FROM developers " +
                            "WHERE developer_id = ?")){
            statement.setInt(1, developerId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                developer.setDeveloperId(resultSet.getInt("developer_id"));
                developer.setName(resultSet.getString("name"));
                developer.setSalary(resultSet.getInt("salary"));
                developer.setSurname(resultSet.getString("surname"));
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
        return developer;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void addSkillsToDeveloper(Developer developer) {
        List<String> skills = developer.getSkills();
        List<Skill> skillList = skillsDao.getAllSkills();
        int id = developer.getDeveloperId();

        for (Skill skill : skillList) {
            for (String skillName : skills) {
                if (skillName.equals(skill.getSkillName())) {
                    try (Connection connection = dataSource.getConnection();
                         PreparedStatement statement = connection
                                 .prepareStatement("INSERT INTO developer_skill VALUES (?, ?)")) {
                        statement.setInt(1, id);
                        statement.setInt(2, skill.getSkillId());
                        statement.execute();
                        LOGGER.info("In table Developer_skill was add developer id: " + id
                                + ", developer skill id: " + skill.getSkillId());
                    } catch (SQLException e) {
                        LOGGER.error("Something wrong with add skill in developer_skill");
                    }
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void updateSkillsFromDeveloper(Developer developer){
        deleteSkillsFromDeveloper(developer.getDeveloperId());
        addSkillsToDeveloper(developer);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void deleteSkillsFromDeveloper(int developerId){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM developer_skill WHERE developer_id = ?")){
            statement.setInt(1, developerId);
            statement.execute();
            LOGGER.error("From table Developer_skill was delete skills of developer id: " + developerId);
        }catch (SQLException e){
            LOGGER.error("Something wrong with deleting skill from developer_skill");
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
