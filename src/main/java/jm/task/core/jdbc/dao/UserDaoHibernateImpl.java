package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.management.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null; 
    //для создания и удаления  таблицы бд не нашла метода и можно только с sql запросом, иначе ошибка
    private final String CREATE_DB = "CREATE TABLE IF NOT EXISTS User (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), Lastname VARCHAR(20), Age TINYINT)";
    private final String DROP_DB = "drop TABLE IF EXISTS User";
    private final String DELETE_DB = "delete User";
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session sessionCreate = Util.getSessionFactory().openSession()) {
            transaction = sessionCreate.beginTransaction();
            sessionCreate.createSQLQuery(CREATE_DB).addEntity(User.class).executeUpdate();
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
            sessionDropUser.createSQLQuery(DROP_DB).addEntity(User.class).executeUpdate();
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
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            sessionSaveUser.saveOrUpdate(user);
            sessionSaveUser.flush();
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
            User user = new User();
            user.setId(id);
            sessionRemove.delete(user);
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session sessionClean = Util.getSessionFactory().openSession()) {
            transaction = sessionClean.beginTransaction();
            sessionClean.createQuery("DELETE from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
