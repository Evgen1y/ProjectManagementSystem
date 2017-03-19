package ua.goit.java.console;

import org.springframework.transaction.PlatformTransactionManager;
import ua.goit.java.console.table.*;
import ua.goit.java.entity.Developer;

import java.util.Scanner;

/**
 * Created by bulov on 07.03.2017.
 */
public class Console {

    private PlatformTransactionManager txManager;
    private Scanner scanner = new Scanner(System.in);
    private CompaniesConsole companiesConsole;
    private DevelopersConsole developersConsole;
    private CustomersConsole customersConsole;
    private ProjectsConsole projectsConsole;
    private SkillsConsole skillsConsole;
    public Console() {

    }

    public void runConsole(){
        System.out.println("Please chose what are you want to do:\n");
        System.out.println("Press 1 - COMPANIES TABLE");
        System.out.println("Press 2 - DEVELOPERS TABLE");
        System.out.println("Press 3 - CUSTOMERS TABLE");
        System.out.println("Press 4 - PROJECTS TABLE");
        System.out.println("Press 5 - SKILLS TABLE");
        System.out.println("Press 0 - EXIT\n");
        System.out.print("Your choice > ");
        choseTable(scanner.nextInt());
    }

    private void choseTable(int choice){
        switch(choice){
            case 0: exit();
            case 1:
                companiesConsole.runConsole();
                break;
            case 2:
                developersConsole.runConsole();
                break;
            case 3:
                customersConsole.runConsole();
                break;
            case 4:
                projectsConsole.runConsole();
                break;
            case 5:
                skillsConsole.runConsole();
                break;
            default:
                System.out.println("\n!!! PLEASE DO CORRECT CHOICE !!!\n");
                runConsole();
        }
    }

    private void exit(){
        System.out.print("Are you sure?\nY or N\n\nYour choice > " );
        switch (scanner.next().toLowerCase().charAt(0)){
            case 'y':
                System.out.println("GOOD BYE");
                System.exit(0);
            case 'n':
                runConsole();
        }
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCompaniesConsole(CompaniesConsole companiesConsole) {
        this.companiesConsole = companiesConsole;
    }

    public void setDevelopersConsole(DevelopersConsole developersConsole) {
        this.developersConsole = developersConsole;
    }

    public void setCustomersConsole(CustomersConsole customersConsole) {
        this.customersConsole = customersConsole;
    }

    public void setProjectsConsole(ProjectsConsole projectsConsole) {
        this.projectsConsole = projectsConsole;
    }

    public void setSkillsConsole(SkillsConsole skillsConsole) {
        this.skillsConsole = skillsConsole;
    }
}
