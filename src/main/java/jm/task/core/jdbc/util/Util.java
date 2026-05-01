package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/database?useSSL=false&serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static Connection connection;
    private static final SessionFactory sessionFactory = buildSessionFactory();


    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    private static SessionFactory buildSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.USER, USER);
        properties.put(Environment.URL, URL);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.DIALECT, DIALECT);
        Configuration configuration = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


}
