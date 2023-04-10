package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class Util {
//    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/database";
    private static final String DB_USERNAME = "rootroot";
    private static final String DB_PASSWORD = "rootroot";

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
}
