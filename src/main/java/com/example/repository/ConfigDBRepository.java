package com.example.repository;

import com.example.database.DBConnection;
import com.example.view.AppView;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfigDBRepository {

    public void createTable() {
        String SQL = """
                CREATE TABLE IF NOT EXISTS users(
                   id INTEGER NOT NULL AUTO_INCREMENT,
                   name VARCHAR(128) NOT NULL,
                   email VARCHAR(128) NOT NULL,
                   PRIMARY KEY (id)
                )""";
        try (PreparedStatement preparedStatement = DBConnection.connect().prepareStatement(SQL)){
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            new AppView().getOutput(e.getMessage());
        }
    }

}
