package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String createUsersTable = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (`id` BIGINT NOT NULL AUTO_INCREMENT,`name` VARCHAR(100) NOT NULL,`lastName` VARCHAR(100) NULL,`age` INT(3) NOT NULL, PRIMARY KEY (`id`))";
            statement.executeUpdate(createUsersTable);
        } catch (SQLException e) {
            e.getStackTrace();
        }
        System.out.println("Таблица создана.");
    }

    @Override
    public void dropUsersTable() {
        String dropUsersTable = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropUsersTable);
        } catch (SQLException e) {
            e.getStackTrace();
        }
        System.out.println("Таблица удалена.");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
        System.out.println("Пользователь c именем " + name + " " + lastName + " был добавлен.");
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE Id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllUsers = "SELECT * FROM users";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        System.out.println("Список пользователей готов:");
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}