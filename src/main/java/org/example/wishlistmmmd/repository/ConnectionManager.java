package org.example.wishlistmmmd.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
    //Ved singleton vil vi sikre, at der kun oprettes én instans af en klasse. Det vil vi gerne have fx i forbindelse
    //med databaseforbindelse, for at undgå konflikter og spare ressourcer.
    //Vi sikrer det bl.a. ved at konstruktøren er private og der tjekkes via metode
    //for om der allerede er én instans af klassen (Denne kaldes fra Spring Configurations-klassen DataConfig)

    private Connection conn; //attribut til selve dataforbindelsen
    private static ConnectionManager instance; //statisk instans af klassen selv, for at sikre der kun findes én instans



    private ConnectionManager(String db_url, String db_user, String db_password) { //private konstruktør, så klassen ikke kan instantieres direkte udefra
        try {
            conn = DriverManager.getConnection(db_url, db_user, db_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionManager getInstance(String db_url, String db_user, String db_password) {
        //denne metode laver en instans af klassen, hvis den ikke allerede eksisterer. Metoden er statisk dvs den tilhører klassen og kaldes uden new
        if(instance == null) {
            instance = new ConnectionManager(db_url, db_user, db_password);
        }
        return instance;
    }

    public Connection getConn() { //getter til at få fat på connection som hører til instansen
        return conn;
    }

    //metode som tillader at vi kan nulstille ConnectionManager i starten af en test, så en ny instans
    //oprettes med de test-specifikke databaseindstillinger
    public static void resetInstance() {
        instance = null;
    }


}
