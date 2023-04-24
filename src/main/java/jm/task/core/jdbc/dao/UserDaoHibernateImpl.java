package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() { // Метод создания таблицы

        Session session = Util.getSessionFactory().getCurrentSession();// Получили/начали сессию
        Transaction transaction = session.beginTransaction(); //Начали/открыли транзакцию в рамках этой сессии

        //Сформировали SQL-запрос
        String query = "CREATE TABLE IF NOT EXISTS users " +
                "(Id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(20), " +
                "lastName VARCHAR(20), age TINYINT(128))";


        session.createSQLQuery(query).executeUpdate(); // В рамках текущей сессии создали SQL-запрос,
        // передав его в аргумент, и здесь же сразу выполнили его

        transaction.commit(); // Закрыли транзакцию
        session.close(); // Закрыли сессию
    }

    @Override
    public void dropUsersTable() { //Удаление таблицы
        Session session = Util.getSessionFactory().getCurrentSession();// Получили/начали сессию
        Transaction transaction = session.beginTransaction(); //Начали/открыли транзакцию в рамках этой сессии

        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate(); // В рамках текущей сессии
        // создали SQL-запрос, передав его в аргумент, и здесь же сразу выполнили его.
        // DROP используется для удаления таблиц (и баз данных тоже)
        // пишем IF EXISTS - т.к. по условию, надо, чтобы не было ошибки, если таблицы не существует

        transaction.commit(); // Закрыли транзакцию
        session.close(); // Закрыли сессию
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {  //Сохранение/Вставка юзера в таблицу

        Session session = Util.getSessionFactory().getCurrentSession(); // Получили/начали сессию
        Transaction transaction = session.beginTransaction(); //Начали/открыли транзакцию в рамках этой сессии

        session.save(new User(name, lastName, age)); // Сохраняем экземпляр класса User

        transaction.commit(); // Закрыли транзакцию
        session.close(); // Закрыли сессию

    }

    @Override
    public void removeUserById(long id) { //Удаление юзера по Id
        Session session = Util.getSessionFactory().getCurrentSession(); // Получили/начали сессию
        Transaction transaction = session.beginTransaction(); //Начали/открыли транзакцию в рамках этой сессии

        User user = session.load(User.class, id); // Загрузили экземпляр класса User по id (load возвращает объект из БД по его id),
        // переданному в аргумент метода removeUserById, и указав Entity-класс и сохранили результат в User user

        session.delete(user); // Удалили этого загруженного по id юзера

        transaction.commit(); // Закрыли транзакцию
        session.close(); // Закрыли сессию
    }

    @Override
    public List<User> getAllUsers() {//Получить список юзеров

        Session session = Util.getSessionFactory().getCurrentSession(); // Получили/начали сессию
        Transaction transaction = session.beginTransaction(); //Начали/открыли транзакцию в рамках этой сессии

        String hql = "FROM User"; // Создали запрос "Показать всю таблицу", Entity-класс которой - User.
        // Это запрос на HQL-языке, а не на SQL. User в данном случае - POJO-класс, который связан с таблицей users в БД.

        Query query = session.createQuery(hql);// Создали сам запрос, передав в аргумент HQL-запрос

        List users = query.getResultList(); //Представили результат запроса в виде List
//
        transaction.commit(); // Закрыли транзакцию
        session.close(); // Закрыли сессию

        return users;

    }

    @Override
    public void cleanUsersTable() { //Удалить информацию в таблице
        Session session = Util.getSessionFactory().getCurrentSession(); // Получили/начали сессию
        Transaction transaction = session.beginTransaction(); //Начали/открыли транзакцию в рамках этой сессии

        session.createSQLQuery("DELETE FROM users").executeUpdate(); //Создали запрос "Удалить всю таблицу"
        // и сразу же выполнили его

        transaction.commit(); // Закрыли транзакцию
        session.close(); // Закрыли сессию
    }
}
