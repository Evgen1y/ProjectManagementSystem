package ua.goit.java.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.DevelopersDao;
import ua.goit.java.dao.SkillsDao;
import ua.goit.java.entity.Developer;
import ua.goit.java.entity.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 22.03.2017.
 */
public class HibernateDevelopersDao implements DevelopersDao{

    private SessionFactory sessionFactory;
    private SkillsDao skillsDao;

    @Override
    @Transactional
    public void save(Developer developer, List<String> skills) {
        developer.setSkills(getDeveloperSkills(skills));
        sessionFactory.getCurrentSession().save(developer);
    }

    @Override
    @Transactional
    public void delete(int developerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Developer c where c.developerId = :id");
        query.setParameter("id", developerId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(Developer developer, List<String> skills) {
        Session session = sessionFactory.getCurrentSession();
        developer.setSkills(getDeveloperSkills(skills));
        session.saveOrUpdate(developer);
    }

    @Override
    @Transactional
    public List<Developer> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select d from Developer d").list();
    }

    @Override
    @Transactional
    public Developer getById(int developerId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select d from Developer d where d.developerId = :id");
        query.setParameter("id", developerId);
        return (Developer) query.uniqueResult();
    }

    public List<Skill> getDeveloperSkills(List<String> skills) {
        List<Skill> developerSkills = new ArrayList<>();
        for (Skill skill: skillsDao.getAll()) {
            if(skills.stream().anyMatch(s -> s.equals(skill.getSkillName()))){
                developerSkills.add(skill);
            }
        }
        return developerSkills;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSkillsDao(SkillsDao skillsDao) {
        this.skillsDao = skillsDao;
    }
}
