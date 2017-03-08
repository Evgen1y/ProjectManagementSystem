package ua.goit.java.console;

import ua.goit.java.console.table.*;

import java.util.Scanner;

/**
 * Created by bulov on 07.03.2017.
 */
public class Console {
    private Scanner scanner = new Scanner(System.in);

    public Console() {
        this.runConsole();
    }

    public void runConsole(){
        System.out.println("Please chose what are you want to do:\n");
        System.out.println("Press 1 - COMPANIES TABLE");
        System.out.println("Press 2 - DEVELOPERS TABLE");
        System.out.println("Press 3 - CUSTOMERS TABLE");
        System.out.println("Press 4 - PROJECTS");
        System.out.println("Press 5 - SKILLS");
        System.out.println("Press 0 - EXIT\n");
        System.out.print("Your choice > ");
        choseTable(scanner.nextInt());
    }

    private void choseTable(int choice){
        switch(choice){
            case 0: exit();
            case 1:
                new CompaniesConsole().runConsole();
                break;
            case 2:
                new DevelopersConsole().runConsole();
                break;
            case 3:
                new CustomersConsole().runConsole();
                break;
            case 4:
                new ProjectsConsole().runConsole();
                break;
            case 5:
                new SkillsConsole().runConsole();
                break;
            default:
                System.out.println("\n!!! PLEASE DO CORRECT CHOICE !!!\n");
                new Console();
        }
    }

    private void exit(){
        System.out.println("Are you sure?\nY or N" );
        switch (scanner.next().toLowerCase().charAt(0)){
            case 'y':
                System.out.println("GOOD BYE");
                System.exit(0);
            case 'n':
                new Console();
        }
    }
}
