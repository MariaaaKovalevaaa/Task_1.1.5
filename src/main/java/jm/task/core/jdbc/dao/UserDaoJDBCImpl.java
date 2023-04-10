package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {//extends Util, чтобы UserDaoJDBCImpl соединился с БД

    public UserDaoJDBCImpl() {  // Пустой конструктор (дефолтный)
    }

    //Создаем методы с использовнием SQL
    public void createUsersTable() { //запрос create

        String query = "CREATE TABLE IF NOT EXISTS users " +
                "(Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(20), " +
                "lastName VARCHAR(20), age TINYINT(128))";

        try (Connection connection = getConnection(); //Получили соединение с БД
             Statement statement = connection.createStatement()) {  //Создали объект класса Statement (заявление, высказывание)
             statement.executeUpdate(query); //Поместили в него SQL-запрос
             System.out.println("Таблица создана."); //Проверка
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() { // запрос drop (удаление)

        String query2 = "DROP TABLE IF EXISTS users"; // DROP используется для удаления таблиц (и баз данных тоже)
        //пишем IF EXISTS - т.к. по условию, надо, чтобы не было ошибки, если таблицы не существует

        try (Connection connection = getConnection();  //Получили соединение с БД
             Statement statement = connection.createStatement()) { //Создали объект класса Statement (заявление, высказывание)
             statement.executeUpdate(query2); //Поместили в него SQL-запрос
             System.out.println("Таблица удалена."); //Проверка
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) { // запрос insert

        String query3 = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = getConnection()) { //Получили соединение с БД
            PreparedStatement preparedStatement = connection.prepareStatement(query3); //Создали объект класса PreparedStatement,
            // поместив туда SQL-запрос
            preparedStatement.setString(1, name); //Задали значение для индекса №1 в запросе
            preparedStatement.setString(2, lastName); //Задали значение для индекса №2 в запросе
            preparedStatement.setByte(3, age); //Задали значение для индекса №3 в запросе

            preparedStatement.executeUpdate(); //Выполнили сам запрос

            System.out.println("User с именем – " + name + " добавлен в базу данных"); //Проверка
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) { // запрос delete DELETE используется для удаления строк из таблиц, не удаляет саму таблицу

        String query4 = "DELETE FROM users WHERE id = ?";

        try (Connection connection = getConnection()) { //Получили соединение с БД
            PreparedStatement preparedStatement = connection.prepareStatement(query4); //Создали объект класса PreparedStatement (заявление),
            // поместив туда SQL-запрос
            preparedStatement.setLong(1, id); //Передали в запрос значение для индекса №1
            preparedStatement.executeUpdate(); //Выполнили запрос

            System.out.println("Пользователь удален по Id"); //Проверка
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {// запрос select

        List<User> users = new ArrayList<User>(); //Создали список, потому что мы его будем возвращать

        String query5 = "SELECT id, name, lastName, age FROM users"; //создали запрос

        try (Connection connection = getConnection();  //Получили соединение с БД
             Statement statement = connection.createStatement()) { //Создали объект класса Statement (заявление, высказывание)

             ResultSet resultSet = statement.executeQuery(query5); //Выполнили запрос SQL-запрос, сохранив его результат
                // в переменной типа ResultSet (множество результатов запроса в БД)

             while (resultSet.next()) { //до тех пор пока читается набор результатов от запроса
                User user = new User (); //Создали экземпляр класса User

                user.setId(resultSet.getLong("id")); //Установили ему id - из набора результатов от запроса получили значение из колонки "id"
                user.setName(resultSet.getString("name"));//Установили ему name - из набора результатов от запроса получили значение из колонки "name"
                user.setLastName(resultSet.getString("lastName"));//Установили ему lastName - из набора результатов от запроса получили значение из колонки "lastName"
                user.setAge(resultSet.getByte("age"));//Установили ему age - из набора результатов от запроса получили значение из колонки "age"

                 // таким образом создали одного user, теперь добавим его в наш ArrayList. и так пока цикл не закончится
                users.add(user);

                System.out.println(users); // проверка
                System.out.println("Список получен");

                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() { //DELETE используется для удаления строк из таблиц, не удаляет саму таблицу

        String query6 = "DELETE FROM users";

        try (Connection connection = getConnection();  //Получили соединение с БД
             Statement statement = connection.createStatement()) { //Создали объект класса Statement (заявление, высказывание)

             statement.executeUpdate(query6);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
