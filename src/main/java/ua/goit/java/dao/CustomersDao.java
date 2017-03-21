package ua.goit.java.dao;

import ua.goit.java.entity.Customer;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface CustomersDao {

    void save(Customer customer);

    void delete(int customerId);

    void update(Customer customer);

    List<Customer> getAll();

    Customer getById(int customerId);
}
