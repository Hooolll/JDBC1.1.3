package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS Users ("
                    + "id INT(64) NOT NULL AUTO_INCREMENT, "
                    + "name VARCHAR(250) NOT NULL, "
                    + "lastName VARCHAR(250) NOT NULL, "
                    + "age INT(5) NOT NULL, "
                    + "PRIMARY KEY(id))";
            statement.executeUpdate(createTable);
            System.out.println("Table create success");
        } catch (SQLException e) {
            System.out.println("Cannot create");
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DROP TABLE IF EXISTS Users";
            statement.execute(SQL);
            System.out.println("Table drop success");
        } catch (SQLException e) {
            System.out.println("Cannot drop table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()) {
            String saveUser = "INSERT INTO Users ("
                    + "name, lastName, age) "
                    + "VALUES (" + "'" + name + "', '" + lastName + "'," + age + ")";
            statement.executeUpdate(saveUser);
            System.out.println("User - " + name + " added");
        } catch (SQLException e) {
            System.out.println("Cannot save user");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String removeUser = "DELETE FROM Users WHERE ID = id";
            statement.executeUpdate(removeUser);
        } catch (SQLException e) {
            System.out.println("Cannot remove user by ID");
        }
    }

    public List<User> getAllUsers() {
        String getAllUsers = "SELECT * FROM Users";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Cannot get all users");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DELETE FROM Users";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("Cannot clean users table");
        }
    }
}
