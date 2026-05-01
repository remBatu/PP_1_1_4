package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                    " id BIGINT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(30)," +
                    " last_name VARCHAR(30)," +
                    " age TINYINT," +
                    " PRIMARY KEY (id)" +
                    ")").executeUpdate();
            tx.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession();) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tx.commit();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction tx = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            if (session != null) session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            tx.commit();
        }
    }
}
