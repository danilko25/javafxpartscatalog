package com.example.javafxpartscatalog.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties() throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream("database.properties");
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }
}
