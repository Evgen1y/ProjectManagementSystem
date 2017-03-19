package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.console.table.ProjectsConsole;
import ua.goit.java.entity.Project;
import ua.goit.java.dao.ProjectsDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcProjectsDao implements ProjectsDao {

    private DataSource dataSource;
    private ProjectsConsole projectsConsole;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCompaniesDao.class);

    @Override
    public void addProject(Project project) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO projects VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, project.getProjectId());
            statement.setString(2, project.getProjectName());
            statement.setInt(3, project.getCompanyId());
            statement.setInt(4, project.getCustomerId());
            statement.setInt(5, project.getCost());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            while(resultSet.next()){
                project.setProjectId(resultSet.getInt(1));
                System.out.println(project.getProjectId());
            }
            addDevelopersToProject(project);
            LOGGER.info("In table Projects was added " + project);
        } catch(SQLException e){
            LOGGER.error("Something wrong with add project in projects");
        }
    }

    @Override
    public void deleteProject(int projectId) {
        deleteDevelopersFromProject(projectId);
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM projects WHERE project_id = ?")){

            statement.setInt(1, projectId);
            statement.execute();
            LOGGER.info("From table Projects was deleting company with id = " + projectId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete company in companies");
        }
    }

    @Override
    public void updateProject(Project project) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE projects SET project_name = ?, company_id = ?, customer_id = ?, cost = ? WHERE project_id = ?")){
            statement.setString(1, project.getProjectName());
            statement.setInt(2, project.getCompanyId());
            statement.setInt(3, project.getCustomerId());
            statement.setInt(4, project.getCost());
            statement.setInt(5, project.getCompanyId());
            statement.execute();
            updateDeveloperInProjects(project);
            LOGGER.info("In table Projects was updating company with id = " + project.getProjectId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating company in companies");
        }

    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
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
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT project_id, project_name, company_id, customer_id, cost FROM projects " +
                            "WHERE project_id = ?")){
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

        if(project.equals(new Project())){
            System.out.println("Project with this id doesn't exist.\nPlease, try again");
            projectsConsole.question(projectsConsole);
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

    private void addDevelopersToProject(Project project){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                .prepareStatement("INSERT INTO developer_project VALUE (?, ?)")){
            for(int id :project.getDevelopersId()){
                statement.setInt(1, id);
                statement.setInt(2, project.getProjectId());
                statement.execute();
                LOGGER.info("Add developer with id: " + id + " to project: " + project.getProjectName());
            }
        }catch (SQLException e){
            LOGGER.error("Something going wrong when add developer to project");
        }
    }

    private void updateDeveloperInProjects(Project project){
        deleteDevelopersFromProject(project.getProjectId());
        addDevelopersToProject(project);
    }

    private void deleteDevelopersFromProject(int projectId){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("DELETE FROM developer_project WHERE project_id = ?")) {
            statement.setInt(1, projectId);
            statement.execute();
            LOGGER.info("From table Developer_project was delete developers of project id: " + projectId);
        } catch (SQLException e) {
            LOGGER.error("Something wrong with deleting skill in developer_skill");
        }
    }

    public void setProjectsConsole(ProjectsConsole projectsConsole) {
        this.projectsConsole = projectsConsole;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
