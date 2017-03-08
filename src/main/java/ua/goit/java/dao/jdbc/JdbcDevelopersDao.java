package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.entity.ConnectionFactory;
import ua.goit.java.entity.Developer;
import ua.goit.java.dao.DevelopersDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcDevelopersDao implements DevelopersDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDevelopersDao.class);

    @Override
    public void addDeveloper(Developer developer) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("INSERT INTO companies VALUES (?, ?, ?, ?)");
            statement.setInt(1, developer.getDeveloperId());
            statement.setString(2, developer.getName());
            statement.setString(3, developer.getSurname());
            statement.setInt(4, developer.getSalary());
            statement.execute();
            LOGGER.info("In table Companies was added " + developer);
        } catch(SQLException e){
            LOGGER.error("Something wrong with add company in companies");
        }

    }

    @Override
    public void deleteDeveloper(int developerId) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("DELETE FROM developers WHERE developer_id = ?");
            statement.setInt(1, developerId);
            statement.execute();
            LOGGER.info("From table Developers was deleting developer with id = " + developerId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete developer in developers");
        }

    }

    @Override
    public void updateDeveloper(Developer developer) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("UPDATE developers SET name = ?, salary = ?, surname = ? WHERE developer_id = ?");
            statement.setString(1, developer.getName());
            statement.setInt(2, developer.getSalary());
            statement.setString(3, developer.getSurname());
            statement.setInt(4, developer.getDeveloperId());
            statement.execute();
            LOGGER.info("In table Developers was updating developer with id = " + developer.getDeveloperId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating developer in developers");
        }

    }

    @Override
    public List<Developer> getAllDevelopers() {
        List<Developer> developers = new ArrayList<>();
        try{
            Statement statement = new ConnectionFactory().getConnection().createStatement();
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
    public Developer getDeveloperById(int developerId) {
        Developer developer = new Developer();
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("SELECT developer_id, name, salary, surname FROM developers " +
                            "WHERE developer_id = ?");
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

}
