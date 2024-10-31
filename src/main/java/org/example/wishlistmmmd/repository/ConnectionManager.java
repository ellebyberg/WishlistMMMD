package org.example.wishlistmmmd.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    //Ved singleton vil vi sikre, at der kun oprettes én instans af en klasse. Det vil vi gerne have fx i forbindelse
    //med databaseforbindelse, for at undgå konflikter og spare ressourcer.

    private Connection conn; //attribut til selve dataforbindelsen
    private static ConnectionManager instance; //statisk instans af klassen selv, for at sikre der kun findes én instans

    private String db_url = System.getenv("DB_URL");
    private String db_user = System.getenv("DB_USER");
    private String db_password = System.getenv("DB_PASS");


    private ConnectionManager() { //private konstruktør, så klassen ikke kan instantieres direkte udefra
        try {
            conn = DriverManager.getConnection(db_url, db_user, db_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionManager getInstance() {
        //denne metode laver en instans af klassen, hvis den ikke allerede eksisterer. Metoden er statisk dvs den tilhører klassen og kaldes uden new
        if(instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConn() { //getter til at få fat på connection som hører til instansen
        return conn;
    }


}
