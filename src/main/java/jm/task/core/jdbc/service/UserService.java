package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void createUsersTable() throws SQLException; // запрос create

    void dropUsersTable(); // запрос delete

    void saveUser(String name, String lastName, byte age); //запрос update

    void removeUserById(long id); // запрос delete

    List<User> getAllUsers(); // запрос select

    void cleanUsersTable();
}
