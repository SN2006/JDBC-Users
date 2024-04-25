package com.example.database;

import com.example.view.AppView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection connect() {

        Properties props = new Properties();
        Connection con = null;

        try {
            props.load(DBConnection.class.getResourceAsStream("/db/jdbc.properties"));
            con = DriverManager.getConnection(props.getProperty("dbDriver") +
                            props.getProperty("dbName"),
                    props.getProperty("username"), props.getProperty("password"));
        } catch (SQLException | IOException e) {
            new AppView().getOutput(e.getMessage());
        }
        return con;
    }
}
