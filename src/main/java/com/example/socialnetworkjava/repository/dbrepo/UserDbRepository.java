package com.example.socialnetworkjava.repository.dbrepo;


import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.domain.Validator;
import com.example.socialnetworkjava.repository.Repository;

import java.sql.*;
import java.util.*;
import java.util.stream.StreamSupport;

public class UserDbRepository implements Repository<Long, User> {
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;

    public UserDbRepository(String url, String username, String password, Validator<User> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    public UserDbRepository() {

    }

    @Override
    public Optional<User> findOne(Long aLong) {
        User utilizator = new User();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users where id_user=" + aLong);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_user");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Integer age = resultSet.getInt("age");

                utilizator = new User(name,email, password, age);
                utilizator.setId(id);
            }
            return Optional.of(utilizator);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(utilizator);
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id_user");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Integer age = resultSet.getInt("age");

                User utilizator = new User(name,email, password, age);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * @param entity entity must be not null
     * @return an {@code Optional} - null if the entity was saved,
     * - the entity (id already exists)
     * @throws com.example.lab4db1.domain.MyException      if the entity is not valid
     * @throws IllegalArgumentException if the given entity is null.     *
     */

    @Override
    public Optional<User> save(User entity) {
        String sql = "insert into users (name, email, password, age) values (?, ?, ?, ?)";
        validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPassword());
            ps.setInt(4, entity.getAge());

            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Long aLong) {
        String sql = "delete from users " +
                "where id_user=" + aLong;
        Optional<User> user = findOne(aLong);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.empty();
    }

    @Override
    public int getSize() {
        return (int) StreamSupport.stream(findAll().spliterator(), false).count();
    }

    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        for(User user: findAll()){
            users.add(user);
        }
        return users;
    }
}
