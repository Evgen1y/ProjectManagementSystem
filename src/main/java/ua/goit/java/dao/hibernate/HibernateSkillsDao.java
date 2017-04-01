package ua.goit.java.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.SkillsDao;
import ua.goit.java.entity.Skill;

import java.util.List;

/**
 * Created by bulov on 22.03.2017.
 */
public class HibernateSkillsDao implements SkillsDao{

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Skill skill) {
        sessionFactory.getCurrentSession().save(skill);
    }

    @Override
    @Transactional
    public void delete(int skillId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Skill s where s.skillId = :id");
        query.setParameter("id", skillId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(Skill skill) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(skill);
    }

    @Override
    @Transactional
    public List<Skill> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Skill s").list();
    }

    @Override
    @Transactional
    public Skill getById(int skillId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s from Skill s where s.skillId = :id");
        query.setParameter("id", skillId);
        return (Skill) query.uniqueResult();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
