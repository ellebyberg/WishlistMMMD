package org.example.wishlistmmmd.repository;

import java.sql.Connection;

public class ConnectionManager {
    private static Connection connection;

    private ConnectionManager() {}

    public static Connection getConnection(String db_url, String db_user, String db_password) {

    }
}
