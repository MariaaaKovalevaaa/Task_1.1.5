package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userServiceImpl = new UserServiceImpl(); //Создали, и вызываем его методы

        userServiceImpl.createUsersTable(); //Создаем таблицу, применяя метод класса UserServiceImpl

        List <User> users2 = new ArrayList <User> (); // Создали лист юзеров

        users2.add (new User ("Ola", "Katina", (byte)32)); //Заполнили лист юзеров
        users2.add (new User("Kolya", "Ivanov", (byte)23)); //Заполнили лист юзеров
        users2.add (new User("Lena", "Sorina", (byte)81)); //Заполнили лист юзеров
        users2.add (new User("Petya", "Petrov", (byte)17)); //Заполнили лист юзеров

        for (User user : users2) { //Перебрать каждого юзера из нашего списка юзеров
            userServiceImpl.saveUser(user.getName(), user.getLastName(), (byte) user.getAge()); // Сохранить каждого юзера
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
        }

        List<User> tableOfUsers= userServiceImpl.getAllUsers(); //Получаем список всех юзеров, применяя метод класса userServiceImpl и сохраняя результат метода в список

        userServiceImpl.cleanUsersTable(); // чистим таблицу, т.е. удаляем из нее инфу

        userServiceImpl.dropUsersTable(); // Удаляем саму таблицу
    }
}
