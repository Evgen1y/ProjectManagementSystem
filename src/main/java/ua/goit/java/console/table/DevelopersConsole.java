package ua.goit.java.console.table;

import ua.goit.java.dao.DevelopersDao;
import ua.goit.java.dao.jdbc.JdbcDevelopersDao;
import ua.goit.java.entity.Developer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bulov on 07.03.2017.
 */
public class DevelopersConsole extends TableConsole{

    private Scanner scanner = new Scanner(System.in);
    private DevelopersDao developersDao = new JdbcDevelopersDao();

    @Override
    public void runConsole() {
        System.out.println("Please chose what you want to do:");
        System.out.println("Press 1 - ADD DEVELOPER");
        System.out.println("Press 2 - DELETE DEVELOPER");
        System.out.println("Press 3 - UPDATE DEVELOPER");
        System.out.println("Press 4 - GET ALL DEVELOPERS");
        System.out.println("Press 5 - GET DEVELOPER BY ID");
        System.out.println("Press 9 - RETURN TO START MENU");
        System.out.println("Press 0 - EXIT");
        System.out.print("Your choice > ");
        try{
            int choice = scanner.nextInt();
            checkChoice(this, choice);
        } catch (InputMismatchException e){
            System.out.print("\n!!! INPUT NOT CORRECT !!!\n");
            new DevelopersConsole().runConsole();
        }
    }

    @Override
    public void add(){
        Developer developer = new Developer();
        System.out.print("Insert developer name: ");
        developer.setName(scanner.next());
        System.out.print("\nInsert developer surname: ");
        developer.setSurname(scanner.next());
        System.out.println("\nInsert developer salary: ");
        developer.setSalary(scanner.nextInt());
        developersDao.addDeveloper(developer);
    }

    @Override
    public void delete(){
        System.out.println("Insert developer id: ");
        developersDao.deleteDeveloper(scanner.nextInt());
    }

    @Override
    public void update(){
        System.out.println("Insert id of developer that you want update: ");
        Developer developer = developersDao.getDeveloperById(scanner.nextInt());
        System.out.println("You chose: " + developer.toString());
        System.out.println("Insert new name for developer: ");
        developer.setName(scanner.next());
        System.out.print("\nInsert new developer surname: ");
        developer.setSurname(scanner.next());
        System.out.println("\nInsert new developer salary: ");
        developer.setSalary(scanner.nextInt());
        developersDao.updateDeveloper(developer);
    }

    @Override
    public void getAll(){
        List<Developer> customers;
        customers = developersDao.getAllDevelopers();
        customers.forEach(System.out::println);
    }

    @Override
    public void getById(){
        System.out.println("Insert id of company: ");
        System.out.println(developersDao.getDeveloperById(scanner.nextInt()));
    }
}
