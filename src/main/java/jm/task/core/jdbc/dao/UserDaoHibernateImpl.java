package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;
    private final String CREATE_DB = "CREATE TABLE IF NOT EXISTS User (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), Lastname VARCHAR(20), Age TINYINT)";
    private final String DROP_DB = "DROP TABLE IF EXISTS User";
    private final String INSERT_DB = "INSERT INTO User(Name, Lastname, Age) VALUES(?,?,?)";
    private final String REMOVEUSER_DB = "DELETE FROM User WHERE Id = ?";
    private final String GET_DB = "SELECT * FROM User";
    private final String DELETE_DB = "DELETE FROM User";
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session sessionCreate = Util.getSessionFactory().openSession()) {
            transaction = sessionCreate.beginTransaction();

            Query query = sessionCreate.createSQLQuery(CREATE_DB).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session sessionDropUser = Util.getSessionFactory().openSession()) {
            transaction = sessionDropUser.beginTransaction();
            Query query = sessionDropUser.createSQLQuery(DROP_DB).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session sessionSaveUser = Util.getSessionFactory().openSession()) {
            transaction = sessionSaveUser.beginTransaction();
            Query query = sessionSaveUser.createSQLQuery(INSERT_DB).addEntity(User.class);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session sessionRemove = Util.getSessionFactory().openSession()) {
            transaction = sessionRemove.beginTransaction();
            Query query = sessionRemove.createSQLQuery(REMOVEUSER_DB).addEntity(User.class);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session sessionGetUsers = Util.getSessionFactory().openSession()) {
            return sessionGetUsers.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session sessionClean = Util.getSessionFactory().openSession()) {
            transaction = sessionClean.beginTransaction();
            Query query = sessionClean.createSQLQuery(DELETE_DB).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
