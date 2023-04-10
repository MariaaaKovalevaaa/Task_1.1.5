package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void createUsersTable() throws SQLException;  // запрос create

    void dropUsersTable() throws SQLException; // запрос delete

    void saveUser(String name, String lastName, byte age) throws SQLException; //запрос update

    void removeUserById(long id); //запрос delete

    List<User> getAllUsers(); // запрос select

    void cleanUsersTable();
}
