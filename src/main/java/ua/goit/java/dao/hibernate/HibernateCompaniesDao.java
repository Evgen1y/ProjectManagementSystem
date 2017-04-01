package ua.goit.java.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.CompaniesDao;
import ua.goit.java.entity.Company;

import java.util.List;

/**
 * Created by bulov on 22.03.2017.
 */
public class HibernateCompaniesDao implements CompaniesDao{

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Company company) {
        sessionFactory.getCurrentSession().save(company);
    }

    @Override
    @Transactional
    public void delete(int companyId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Company c where c.companyId = :id");
        query.setParameter("id", companyId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(Company company) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(company);
    }

    @Override
    @Transactional
    public List<Company> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Company c").list();
    }

    @Override
    @Transactional
    public Company getById(int companyId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select c from Company c where c.companyId = :id");
        query.setParameter("id", companyId);
        return (Company) query.uniqueResult();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
