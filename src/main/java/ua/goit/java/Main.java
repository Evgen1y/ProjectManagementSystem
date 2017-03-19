package ua.goit.java;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.goit.java.console.Console;
import ua.goit.java.console.table.CompaniesConsole;

/**
 * Created by bulov on 05.03.2017.
 */
public class Main {

    private Console console;
    private CompaniesConsole companiesConsole;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = context.getBean(Main.class);
        main.start();
    }

    private void start() {
        console.runConsole();
    }


    public void setConsole(Console console) {
        this.console = console;
    }

    public void setCompaniesConsole(CompaniesConsole companiesConsole) {
        this.companiesConsole = companiesConsole;
    }
}
