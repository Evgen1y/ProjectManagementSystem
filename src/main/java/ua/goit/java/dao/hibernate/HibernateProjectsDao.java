package ua.goit.java.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.DevelopersDao;
import ua.goit.java.dao.ProjectsDao;
import ua.goit.java.entity.Developer;
import ua.goit.java.entity.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 22.03.2017.
 */
public class HibernateProjectsDao implements ProjectsDao{

    private SessionFactory sessionFactory;
    private DevelopersDao developersDao;

    @Override
    @Transactional
    public void save(Project project, List<Integer> developersId) {
        project.setDevelopers(getDeveloperInProject(developersId));
        sessionFactory.getCurrentSession().save(project);
    }

    @Override
    @Transactional
    public void delete(int projectId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Project p where p.projectId = :id");
        query.setParameter("id", projectId);
        query.executeUpdate();

    }

    @Override
    @Transactional
    public void update(Project project, List<Integer> developersId) {
        Session session = sessionFactory.getCurrentSession();
        project.setDevelopers(getDeveloperInProject(developersId));
        session.saveOrUpdate(project);
    }

    @Override
    @Transactional
    public List<Project> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Project p").list();
    }

    @Override
    @Transactional
    public Project getById(int projectId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select p from Project p where p.projectId = :id");
        query.setParameter("id", projectId);
        return (Project) query.uniqueResult();
    }

    private List<Developer> getDeveloperInProject(List<Integer> developersId) {
        List<Developer> developers = new ArrayList<>();
        for (Developer developer: developersDao.getAll()) {
            if(developersId.stream().anyMatch(i -> i == developer.getDeveloperId())){
                developers.add(developer);
            }
        }
        return developers;
    }

    public void setDevelopersDao(DevelopersDao developersDao) {
        this.developersDao = developersDao;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
