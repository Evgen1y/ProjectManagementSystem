package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.entity.ConnectionFactory;
import ua.goit.java.entity.Project;
import ua.goit.java.dao.ProjectsDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcProjectsDao implements ProjectsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCompaniesDao.class);

    @Override
    public void addProject(Project project) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("INSERT INTO projects VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, project.getProjectId());
            statement.setString(2, project.getProjectName());
            statement.setInt(3, project.getCompanyId());
            statement.setInt(4, project.getCustomerId());
            statement.setInt(5, project.getCost());
            statement.execute();
            LOGGER.info("In table Companies was added " + project);
        } catch(SQLException e){
            LOGGER.error("Something wrong with add company in companies");
        }

    }

    @Override
    public void deleteProject(int projectId) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("DELETE FROM projects WHERE project_id = ?");
            statement.setInt(1, projectId);
            statement.execute();
            LOGGER.info("From table Projects was deleting company with id = " + projectId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete company in companies");
        }

    }

    @Override
    public void updateProject(Project project) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("UPDATE projects SET project_name = ?, company_id = ?, customer_id = ?, cost = ? WHERE project_id = ?");
            statement.setString(1, project.getProjectName());
            statement.setInt(2, project.getCompanyId());
            statement.setInt(3, project.getCustomerId());
            statement.setInt(4, project.getCost());
            statement.setInt(5, project.getCompanyId());
            statement.execute();
            LOGGER.info("In table Projects was updating company with id = " + project.getProjectId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating company in companies");
        }

    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try{
            Statement statement = new ConnectionFactory().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM projects");
            while(resultSet.next()) {
                projects.add(createProject(resultSet));
            }
            LOGGER.info("All projects was received");
        } catch(SQLException e){
            LOGGER.error("Something wrong with receiving of all projects");
        }
        return projects;
    }

    @Override
    public Project getProjectById(int projectId) {
        Project project = new Project();
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("SELECT project_id, project_name, company_id, customer_id, cost FROM projects " +
                            "WHERE project_id = ?");
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                project.setProjectName(resultSet.getString("project_name"));
                project.setProjectId(resultSet.getInt("project_id"));
                project.setCompanyId(resultSet.getInt("company_id"));
                project.setCustomerId(resultSet.getInt("customer_id"));
                project.setCost(resultSet.getInt("cost"));
                LOGGER.info("Project with id = " + projectId + " is received");
            }
        } catch(SQLException e){
            LOGGER.error("Something wrong with getting project from projects with id = " + projectId);
        }
        return project;
    }

    private Project createProject(ResultSet resultSet) throws SQLException{
        Project project = new Project();
        project.setProjectId(resultSet.getInt("project_id"));
        project.setProjectName(resultSet.getString("project_name"));
        project.setCompanyId(resultSet.getInt("company_id"));
        project.setCustomerId(resultSet.getInt("customer_id"));
        project.setCost(resultSet.getInt("cost"));
        return project;
    }

}
