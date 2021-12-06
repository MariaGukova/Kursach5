package com.Server.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProvider {
    public static final String  url ="jdbc:postgresql://localhost:5432/";
    public static final String  NAME_DATABASE ="postgres";
    public static final String  user ="postgres";
    public static final String  password ="0636";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("can't provide connection", e);
        }
    }
}
