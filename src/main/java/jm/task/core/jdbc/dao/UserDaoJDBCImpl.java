package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = Util.getConnection();

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
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
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
