package ua.goit.java.console.table;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.CompaniesDao;
import ua.goit.java.entity.Company;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bulov on 07.03.2017.
 */
public class CompaniesConsole extends TableConsole{

    private Scanner scanner = new Scanner(System.in);
    private CompaniesDao companiesDao;

    @Override
    public void runConsole() {
        System.out.println("Please chose what you want to do:");
        System.out.println("Press 1 - ADD COMPANY");
        System.out.println("Press 2 - DELETE COMPANY");
        System.out.println("Press 3 - UPDATE COMPANY");
        System.out.println("Press 4 - GET ALL COMPANIES");
        System.out.println("Press 5 - GET COMPANY BY ID");
        System.out.println("Press 9 - RETURN TO START MENU");
        System.out.println("Press 0 - EXIT\n");
        System.out.print("Your choice > ");
        try{
            int choice = scanner.nextInt();
            checkChoice(this, choice);
        } catch (InputMismatchException e){
            System.out.print("\n!!! INPUT NOT CORRECT !!!\n");
            new CompaniesConsole().runConsole();
        }
    }

    @Override
    public void add(){
        Company company = new Company();
        System.out.print("Please insert company name: ");
        company.setCompanyName(scanner.next());
        companiesDao.save(company);
    }

    @Override
    public void delete(){
        System.out.print("Please insert company id: ");
        companiesDao.delete(scanner.nextInt());
    }

    @Override
    public void update(){
        System.out.print("Please insert id of company that you want update: ");
        Company company = companiesDao.getById(scanner.nextInt());
        System.out.println("You chose: " + company.toString());
        System.out.print("Please insert new name for company: ");
        company.setCompanyName(scanner.next());
        companiesDao.update(company);
    }

    @Override
    public void getAll(){
        List<Company> companies;
        companies = companiesDao.getAll();
        companies.forEach(System.out::println);
    }

    @Override
    public void getById(){
        System.out.print("Please insert id of company: ");
        System.out.println(companiesDao.getById(scanner.nextInt()));
    }


    public void setCompaniesDao(CompaniesDao companiesDao) {
        this.companiesDao = companiesDao;
    }
}
