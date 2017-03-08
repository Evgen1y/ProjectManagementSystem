package ua.goit.java.console.table;

import ua.goit.java.dao.ProjectsDao;
import ua.goit.java.dao.jdbc.JdbcProjectsDao;
import ua.goit.java.entity.Project;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bulov on 07.03.2017.
 */
public class ProjectsConsole extends TableConsole{


    private Scanner scanner = new Scanner(System.in);
    private ProjectsDao projectsDao = new JdbcProjectsDao();

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
        projectsDao.addProject(project);
    }

    @Override
    public void delete(){
        System.out.println("Insert project id: ");
        projectsDao.deleteProject(scanner.nextInt());
    }

    @Override
    public void update(){
        System.out.println("Insert id of project that you want update: ");
        Project project = projectsDao.getProjectById(scanner.nextInt());
        System.out.println("You chose: " + project.toString());
        System.out.println("Insert new name for project: ");
        project.setProjectName(scanner.next());
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
        System.out.println("Insert id of project: ");
        System.out.println(projectsDao.getProjectById(scanner.nextInt()));
    }

}
