package ua.goit.java.console.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.DevelopersDao;
import ua.goit.java.dao.SkillsDao;
import ua.goit.java.entity.Developer;

import java.util.*;

/**
 * Created by bulov on 07.03.2017.
 */
public class DevelopersConsole extends TableConsole {

    private PlatformTransactionManager txManager;
    private Scanner scanner = new Scanner(System.in);
    private DevelopersDao developersDao;
    private SkillsDao skillsDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopersConsole.class);


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
        try {
            int choice = scanner.nextInt();
            checkChoice(this, choice);
        } catch (InputMismatchException e) {
            System.out.print("\n!!! INPUT NOT CORRECT !!!\n");
            new DevelopersConsole().runConsole();
        }
    }

    @Override
    public void add() {
        Developer developer = new Developer();
        System.out.print("Insert developer name: ");
        developer.setName(scanner.next());
        System.out.print("Insert developer surname: ");
        developer.setSurname(scanner.next());
        System.out.print("Insert developer salary: ");
        developer.setSalary(scanner.nextInt());
        System.out.println("Insert developer skills (PLEASE USE / TO SEPARATE): ");
        System.out.println("You can chose from this skills:");
        skillsDao.getAll().forEach(System.out::println);
        System.out.print("Your choice > ");
        List<String> skills = (Arrays.asList(scanner.next().split("/")));
        developersDao.save(developer, skills);
    }

    @Override
    public void delete() {
        System.out.print("Insert developer id: ");
        developersDao.delete(scanner.nextInt());
    }

    @Override
    public void update() {
        System.out.print("Insert id of developer that you want update: ");
        Developer developer = developersDao.getById(scanner.nextInt());
        System.out.println("You chose: " + developer.toString());
        System.out.print("Insert new name for developer: ");
        developer.setName(scanner.next());
        System.out.print("Insert new developer surname: ");
        developer.setSurname(scanner.next());
        System.out.print("Insert new developer salary: ");
        developer.setSalary(scanner.nextInt());
        System.out.println("Insert developer skills (PLEASE USE / TO SEPARATE): ");
        System.out.println("You can chose from this skills:");
        skillsDao.getAll().forEach(System.out::println);
        System.out.print("Your choice > ");
        List<String> skills = (Arrays.asList(scanner.next().split("/")));
        developersDao.update(developer, skills);
    }

    @Override
    public void getAll() {
        List<Developer> customers;
        customers = developersDao.getAll();
        customers.forEach(System.out::println);
    }

    @Override
    public void getById() {
        System.out.println("Insert id of company: ");
        System.out.println(developersDao.getById(scanner.nextInt()));
    }

    public void setDevelopersDao(DevelopersDao developersDao) {
        this.developersDao = developersDao;
    }

    public void setSkillsDao(SkillsDao skillsDao) {
        this.skillsDao = skillsDao;
    }
}
