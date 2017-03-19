package ua.goit.java.console.table;

import org.springframework.transaction.PlatformTransactionManager;
import ua.goit.java.dao.CompaniesDao;
import ua.goit.java.dao.CustomersDao;
import ua.goit.java.dao.DevelopersDao;
import ua.goit.java.dao.ProjectsDao;
import ua.goit.java.dao.jdbc.JdbcCompaniesDao;
import ua.goit.java.dao.jdbc.JdbcCustomersDao;
import ua.goit.java.dao.jdbc.JdbcDevelopersDao;
import ua.goit.java.dao.jdbc.JdbcProjectsDao;
import ua.goit.java.entity.Company;
import ua.goit.java.entity.Developer;
import ua.goit.java.entity.Project;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bulov on 07.03.2017.
 */
public class ProjectsConsole extends TableConsole{

    private PlatformTransactionManager txManager;
    private Scanner scanner = new Scanner(System.in);
    private ProjectsDao projectsDao;
    private CompaniesDao companiesDao;
    private CustomersDao customersDao;
    private DevelopersDao developersDao;

    @Override
    public void runConsole() {

        System.out.println("\nPlease chose what you want to do:");
        System.out.println("Press 1 - ADD PROJECT");
        System.out.println("Press 2 - DELETE PROJECT");
        System.out.println("Press 3 - UPDATE PROJECT");
        System.out.println("Press 4 - GET ALL PROJECTS");
        System.out.println("Press 5 - GET PROJECT BY ID");
        System.out.println("Press 9 - RETURN TO START MENU");
        System.out.println("Press 0 - EXIT");
        System.out.print("Your choice > ");
        try{
            int choice = scanner.nextInt();
            checkChoice(this, choice);
        } catch (InputMismatchException e){
            System.out.print("\n!!! INPUT NOT CORRECT !!!\n");
            new ProjectsConsole().runConsole();
        }

    }

    @Override
    public void add(){
        Project project = new Project();
        System.out.print("Insert project name: ");
        project.setProjectName(scanner.next());
        System.out.print("Insert project cost: ");
        project.setCost(scanner.nextInt());
        System.out.println("Insert company id that will work on this project: ");
        System.out.println("You can chose from this company: ");
        companiesDao.getAllCompanies().forEach(System.out::println);
        System.out.print("Your choice > ");
        project.setCompanyId(scanner.nextInt());
        System.out.print("Insert customer id of this project: ");
        System.out.println("You can chose from this customers: ");
        customersDao.getAllCustomers().forEach(System.out::println);
        System.out.print("Your choice > ");
        project.setCustomerId(scanner.nextInt());
        System.out.println("Insert developer id that work in this project: ");
        System.out.println("You can chose from this developers: ");
        developersDao.getAllDevelopers().forEach(System.out::println);
        System.out.print("Your choice > ");
        List<Integer> developersId = new ArrayList<>();
        Arrays.stream(scanner.next().split("/")).mapToInt(Integer::parseInt).forEach(developersId::add);
        project.setDevelopersId(developersId);
        System.out.println(developersId);
        projectsDao.addProject(project);
    }

    @Override
    public void delete(){
        System.out.print("Insert project id: ");
        projectsDao.deleteProject(scanner.nextInt());
    }

    @Override
    public void update(){
        System.out.print("Insert id of project that you want update: ");
        Project project = projectsDao.getProjectById(scanner.nextInt());
        System.out.println("You chose: " + project.toString());
        System.out.print("Insert new name for project: ");
        project.setProjectName(scanner.next());
        System.out.println("Insert developer id that work in this project: ");
        System.out.println("You can chose from this developers: ");
        developersDao.getAllDevelopers().forEach(System.out::println);
        System.out.print("Your choice > ");
        List<Integer> developersId = new ArrayList<>();
        Arrays.stream(scanner.next().split("/")).mapToInt(Integer::parseInt).forEach(developersId::add);
        project.setDevelopersId(developersId);
        projectsDao.updateProject(project);
    }

    @Override
    public void getAll(){
        List<Project> projects;
        projects = projectsDao.getAllProjects();
        projects.forEach(System.out::println);
    }

    @Override
    public void getById(){
        System.out.print("Insert id of project: ");
        System.out.println(projectsDao.getProjectById(scanner.nextInt()));
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setProjectsDao(ProjectsDao projectsDao) {
        this.projectsDao = projectsDao;
    }

    public void setCompaniesDao(CompaniesDao companiesDao) {
        this.companiesDao = companiesDao;
    }

    public void setCustomersDao(CustomersDao customersDao) {
        this.customersDao = customersDao;
    }

    public void setDevelopersDao(DevelopersDao developersDao) {
        this.developersDao = developersDao;
    }
}
