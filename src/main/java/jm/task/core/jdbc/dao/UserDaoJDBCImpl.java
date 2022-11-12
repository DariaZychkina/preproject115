package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection conn = getConn();
    private final String CREATE_DB = "CREATE TABLE IF NOT EXISTS Users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), Lastname VARCHAR(20), Age TINYINT)";
    private final String DROP_DB = "DROP TABLE Users";
    private final String INSERT_DB = "INSERT INTO Users(Name, Lastname, Age) VALUES(?,?,?)";
    private final String REMOVEUSER_DB = "DELETE FROM Users WHERE Id = ?";
    private final String GET_DB = "SELECT * FROM Users";
    private final String DELETE_DB = "DELETE FROM Users";


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        try (Statement createTab = conn.createStatement()) {
            conn.setAutoCommit(false);
            createTab.executeUpdate(CREATE_DB);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(false);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Statement dropTable = conn.createStatement()){
            conn.setAutoCommit(false);
            dropTable.executeUpdate(DROP_DB);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(false);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement savingUser = conn.prepareStatement(INSERT_DB)) {
            conn.setAutoCommit(false);
            savingUser.setString(1, name);
            savingUser.setString(2, lastName);
            savingUser.setByte(3, age);
            savingUser.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
            throw new SQLException();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement remUser = conn.prepareStatement(REMOVEUSER_DB)){
            conn.setAutoCommit(false);
            remUser.setLong(1, id);
            remUser.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(false);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new LinkedList<>();
        try (PreparedStatement getUsers = conn.prepareStatement(GET_DB)) {
            conn.setAutoCommit(false);
            ResultSet rs = getUsers.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("Id"));
                user.setName(rs.getString("Name"));
                user.setLastName(rs.getString("Lastname"));
                user.setAge(rs.getByte("Age"));
                users.add(user);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(false);
            return users;
        }
    }

    public void cleanUsersTable() throws SQLException{
        try (PreparedStatement  cleanTab = conn.prepareStatement(DELETE_DB );) {
            conn.setAutoCommit(false);
            cleanTab.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(false);
        }
    }

    public Connection getConn() {
        try {
            return  Util.getMySQLConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
