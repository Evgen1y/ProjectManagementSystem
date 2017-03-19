package ua.goit.java.console.table;

import ua.goit.java.console.Console;

import java.util.List;
import java.util.Scanner;

/**
 * Created by bulov on 08.03.2017.
 */
public abstract class TableConsole {

    private Console console;

    Scanner scanner = new Scanner(System.in);

    abstract void add();

    abstract void delete();

    abstract void update();

    abstract void getAll();

    abstract void getById();

    abstract void runConsole();

    void checkChoice(TableConsole t, int choice){
        switch (choice){
            case 1: add();
                question(t);
                break;
            case 2: delete();
                question(t);
                break;
            case 3: update();
                question(t);
                break;
            case 4: getAll();
                question(t);
                break;
            case 5: getById();
                question(t);
                break;
            case 9: console.runConsole();
                break;
            case 0: exit(t);
            default:
                System.out.println("\n!!! PLEASE DO CORRECT CHOICE !!!\n");
                t.runConsole();
        }
    }

    public void question(TableConsole t){
        System.out.println("S - back to START MENU\nT - back to TABLE\nE - EXIT");
        switch(scanner.next().toLowerCase().charAt(0)){
            case 's': console.runConsole();
                break;
            case 't': t.runConsole();
                break;
            case 'e': exit(t);
                break;
            default:
                System.out.println("\n!!! PLEASE DO CORRECT CHOICE !!!\n");
                question(t);
        }
    }

    private void exit(TableConsole t){
        System.out.println("Are you sure?\nY or N" );
        switch (scanner.next().toLowerCase().charAt(0)){
            case 'y':
                System.out.println("Good bye");
                System.exit(0);
            case 'n':
                t.runConsole();
                break;
            default:
                System.out.println("\n!!! chose Y or N !!!\n");
                exit(t);
        }
    }

    public void setConsole(Console console) {
        this.console = console;
    }
}
