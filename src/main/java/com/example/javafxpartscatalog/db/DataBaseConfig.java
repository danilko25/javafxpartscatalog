package com.example.javafxpartscatalog.db;


import com.example.javafxpartscatalog.utils.PropertiesLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConfig {

    private static Properties conf;

    {
        try {
            conf = PropertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException("Properties file for db was not founded");
        }
    }

    private static String url = conf.getProperty("db.url");
    private static String username = conf.getProperty("db.username");
    private static String password = conf.getProperty("db.password");

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
