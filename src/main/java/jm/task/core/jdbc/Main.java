package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl tableUsers = new UserServiceImpl();
        tableUsers.createUsersTable();
        tableUsers.saveUser("Elliot", "Alderson", (byte)35);
        System.out.println("User с именем " + tableUsers.getAllUsers().get(tableUsers.getAllUsers().size()-1).getName() + "добавлен в базу данных");
        tableUsers.saveUser("Charlie", "Bradbury", (byte)30);
        tableUsers.saveUser("Wayne", "M", (byte)16);
        tableUsers.saveUser("Jonas", "Kahnwald", (byte)17);
        tableUsers.getAllUsers().forEach(System.out::println);
        tableUsers.cleanUsersTable();
        tableUsers.dropUsersTable();



    }
}
