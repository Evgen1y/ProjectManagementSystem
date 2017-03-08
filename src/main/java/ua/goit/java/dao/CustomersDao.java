package ua.goit.java.dao;

import ua.goit.java.entity.Customer;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface CustomersDao {

    void addCustomer (Customer customer);

    void deleteCustomer (int customerId);

    void updateCustomer (Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById (int customerId);
}
