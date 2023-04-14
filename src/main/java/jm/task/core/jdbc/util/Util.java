package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static SessionFactory sessionFactory; //SessionFactory - фабрика по производству сессий.
    // Это средство подключения к БД так же, как и Connection
    private static final String DB_URL = "jdbc:mysql://localhost:3306/database";
    private static final String DB_USERNAME = "rootroot";
    private static final String DB_PASSWORD = "rootroot";

    // Подключение к БД ч/з Connection. (Устаревший способ)
    public Connection getConnection () {
        Connection connection = null; //Объявили переменную типа Connection
        try {
//            Class.forName(DB_DRIVER); //Зарегистрировали драйвер-SQL в DriverManager'e. (Без этой строчки тоже все работает)
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD); //DriverManager-класс упр-я JDBC, отслеживает все доступные драйверы и управляет установлением соединений между БД и соответствующим драйвером.
            System.out.println("Соединение установлено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Соединение не установлено");
        }
        return connection;
    }
    // Подключение к БД ч/з SessionFactory. (Современный способ)
    // Метод создания SessionFactory
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) { // т.е. если SessionFactory нет, то
            try {
                Properties properties= new Properties(); // Чтобы вручную конфигурировать создание SessionFactory,
                // мы пишем свойства. Properties устроен, как Map - содержит пары "ключ-значение".
                // Можно переместить их в отдельный файл. Либо можно написать отдельный класс с конфигурациями.

                //Вносим свойства. Интерфейс Environment - это окружение, в котором приложение запущено.
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver"); // Драйвер д/БД
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/database?useSSL=false"); // Ссыдка к БД
                properties.put(Environment.USER, "rootroot");
                properties.put(Environment.PASS, "rootroot");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect"); // Тип СУБД
                properties.put(Environment.SHOW_SQL, "true"); // Hibernate будет дублировать в консоль все запросы, которые выполняет
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread"); //класс текущей сессии???
                properties.put(Environment.HBM2DDL_AUTO, "create-drop"); //Hibernate поменяет структуру базы данных

                sessionFactory = new Configuration() //Создаем экземпляр Configuration
                        .addProperties(properties) //или setProperties() ? // Вносим в эту конфигурацию свойства
                        .addAnnotatedClass(User.class) // Указываем, какой Entity-класс использовать
                        .buildSessionFactory(); // Создаем SessionFactory

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}




//Метод создания SessionFactory - фабрики по производству сессий (без конфигураций)
//    public static SessionFactory getSessionFactory() {
//       try {
//           return new Configuration()
//                   .configure() //SessionFactory читает этот файл и узнает, как создавать сессии
//                   .addAnnotatedClass(User.class) //указывает тот класс, который имеет аннотации для работы с БД
//                   .buildSessionFactory();
//       }
//       catch (Throwable e) {
//           e.printStackTrace();
//       }
//       return sessionFactory;
//    }























//    private static final String DB_URL = "jdbc:mysql://localhost:3306/database";
//    private static final String DB_USERNAME = "rootroot";
//    private static final String DB_PASSWORD = "rootroot";
//
//    public Connection getConnection () {
//        Connection connection = null; //Объявили переменную типа Connection
//        try {
////            Class.forName(DB_DRIVER); //Зарегистрировали драйвер-SQL в DriverManager'e. (Без этой строчки тоже все работает)
//            connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD); //DriverManager-класс упр-я JDBC, отслеживает все доступные драйверы и управляет установлением соединений между БД и соответствующим драйвером.
//            System.out.println("Соединение установлено");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Соединение не установлено");
//        }
//        return connection;
//    }
