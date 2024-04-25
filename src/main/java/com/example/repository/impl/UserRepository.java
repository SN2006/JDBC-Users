package com.example.repository.impl;

import com.example.database.DBConnection;
import com.example.entity.User;
import com.example.repository.AppRepository;
import com.example.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements AppRepository<User> {
    @Override
    public String create(User obj) {
        String SQL = "INSERT INTO users (name, email) VALUES(?,?)";
        try (PreparedStatement preparedStatement = DBConnection.connect().prepareStatement(SQL)){
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getEmail());

            preparedStatement.executeUpdate();

            return Constants.DATA_INSERT_MSG;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Statement statement = DBConnection.connect().createStatement()){
            List<User> users = new ArrayList<>();
            String SQL = "SELECT id, name, email FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
                users.add(user);
            }
            return Optional.of(users);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public String update(User obj) {
        if (isIdNotExist(obj.getId())) {
            return Constants.DATA_ABSENT_MSG;
        }
        String SQl = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = DBConnection.connect().prepareStatement(SQl)) {
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setLong(3, obj.getId());

            preparedStatement.executeUpdate();
            return Constants.DATA_UPDATE_MSG;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public String delete(Long id) {
        if (isIdNotExist(id)) {
            return Constants.DATA_ABSENT_MSG;
        }
        String SQL = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = DBConnection.connect().prepareStatement(SQL)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            return Constants.DATA_DELETE_MSG;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public String deleteAll() {
        String SQL = "TRUNCATE TABLE users";
        try (PreparedStatement preparedStatement = DBConnection.connect().prepareStatement(SQL)){
            preparedStatement.executeUpdate();

            return Constants.DATA_DELETE_MSG;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        String SQL = "SELECT id, name, email FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = DBConnection.connect().prepareStatement(SQL)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email")
            );
            return Optional.of(user);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    private boolean isIdNotExist(Long id) {
        String SQL = "SELECT COUNT(id) FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = DBConnection.connect().prepareStatement(SQL)){
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return !resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            return true;
        }
        return true;
    }
}
