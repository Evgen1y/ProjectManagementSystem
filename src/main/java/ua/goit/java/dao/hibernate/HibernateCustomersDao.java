package ua.goit.java.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.CustomersDao;
import ua.goit.java.entity.Customer;

import java.util.List;

/**
 * Created by bulov on 22.03.2017.
 */
public class HibernateCustomersDao implements CustomersDao {

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Customer customer) {
        sessionFactory.getCurrentSession().save(customer);
    }

    @Override
    @Transactional
    public void delete(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Customer c where c.customerId = :id");
        query.setParameter("id", customerId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(customer);
    }

    @Override
    @Transactional
    public List<Customer> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Customer c").list();
    }

    @Override
    @Transactional
    public Customer getById(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select c from Customer c where c.customerId = :id");
        query.setParameter("id", customerId);
        return (Customer) query.uniqueResult();    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
