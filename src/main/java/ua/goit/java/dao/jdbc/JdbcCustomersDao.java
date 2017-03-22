package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.console.table.CustomersConsole;
import ua.goit.java.entity.Customer;
import ua.goit.java.dao.CustomersDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcCustomersDao implements CustomersDao {

    private DataSource dataSource;
    private CustomersConsole customersConsole;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCompaniesDao.class);

    @Override
    public void save(Customer customer) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO customers VALUES (?, ?)")){
            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getCustomerName());
            statement.execute();
            LOGGER.info("In table Customers was saved " + customer);
        } catch(SQLException e){
            LOGGER.error("Something wrong with saving customer in customers");
        }

    }

    @Override
    public void delete(int customerId) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM customers WHERE customer_id = ?")){
            statement.setInt(1, customerId);
            statement.execute();
            LOGGER.info("From table Customers was deleting customer with id = " + customerId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete customer in customers");
        }

    }

    @Override
    public void update(Customer customer) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE customers SET customer_name = ? WHERE customer_id = ?")){
            statement.setString(1, customer.getCustomerName());
            statement.setInt(2, customer.getCustomerId());
            statement.execute();
            LOGGER.info("In table Customers was updating customer with id = " + customer.getCustomerId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating customer in customers");
        }

    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection
                    .createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
            while(resultSet.next()) {
                customers.add(createCustomer(resultSet));
            }
            LOGGER.info("All customers was received");
        } catch(SQLException e){
            LOGGER.error("Something wrong with receiving of all customers");
        }
        return customers;

    }

    @Override
    public Customer getById(int customerId) {
        Customer customer = new Customer();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT customer_id, customer_name FROM customers " +
                            "WHERE customer_id = ?")){
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                customer.setCustomerName(resultSet.getString("customer_name"));
                customer.setCustomerId(resultSet.getInt("customer_id"));
                LOGGER.info("Customer with id = " + customerId + " is received");
            }
        } catch(SQLException e){
            LOGGER.error("Something wrong with getting customer from customers with id = " + customerId);
        }

        if(customer.equals(new Customer())){
            System.out.println("Customer with this id doesn't exist.\nPlease, try again");
            customersConsole.question(customersConsole);
        }

        return customer;
    }

    private Customer createCustomer(ResultSet resultSet) throws SQLException{
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getInt("customer_id"));
        customer.setCustomerName(resultSet.getString("customer_name"));
        return customer;
    }

    public void setCustomersConsole(CustomersConsole customersConsole) {
        this.customersConsole = customersConsole;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
