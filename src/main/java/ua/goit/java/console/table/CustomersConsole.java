package ua.goit.java.console.table;

import org.springframework.transaction.PlatformTransactionManager;
import ua.goit.java.console.Console;
import ua.goit.java.dao.CustomersDao;
import ua.goit.java.dao.jdbc.JdbcCustomersDao;
import ua.goit.java.entity.Customer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bulov on 07.03.2017.
 */
public class CustomersConsole extends TableConsole{

    private PlatformTransactionManager txManager;
    private Scanner scanner = new Scanner(System.in);
    private CustomersDao customersDao;

    @Override
    public void runConsole() {
        System.out.println("Please chose what you want to do:");
        System.out.println("Press 1 - ADD CUSTOMER");
        System.out.println("Press 2 - DELETE CUSTOMER");
        System.out.println("Press 3 - UPDATE CUSTOMER");
        System.out.println("Press 4 - GET ALL CUSTOMERS");
        System.out.println("Press 5 - GET CUSTOMER BY ID");
        System.out.println("Press 9 - RETURN TO START MENU");
        System.out.println("Press 0 - EXIT\n");
        System.out.print("Your choice > ");
        try{
            int choice = scanner.nextInt();
            checkChoice(this, choice);
        } catch (InputMismatchException e){
            System.out.print("\n!!! INPUT NOT CORRECT !!!\n");
            new CustomersConsole().runConsole();
        }
    }


    @Override
    public void add(){
        Customer customer = new Customer();
        System.out.print("Insert customer name: ");
        customer.setCustomerName(scanner.next());
        customersDao.addCustomer(customer);
    }

    @Override
    public void delete(){
        System.out.print("Insert customer id: ");
        customersDao.deleteCustomer(scanner.nextInt());
    }

    @Override
    public void update(){
        System.out.print("Insert id of customer that you want update: ");
        Customer customer = customersDao.getCustomerById(scanner.nextInt());
        System.out.println("You chose: " + customer.toString());
        System.out.print("Insert new name for customer: ");
        customer.setCustomerName(scanner.next());
        customersDao.updateCustomer(customer);
    }

    @Override
    public void getAll(){
        List<Customer> customers;
        customers = customersDao.getAllCustomers();
        customers.forEach(System.out::println);
    }

    @Override
    public void getById(){
        System.out.print("Insert id of company: ");
        System.out.println(customersDao.getCustomerById(scanner.nextInt()));
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCustomersDao(CustomersDao customersDao) {
        this.customersDao = customersDao;
    }
}
