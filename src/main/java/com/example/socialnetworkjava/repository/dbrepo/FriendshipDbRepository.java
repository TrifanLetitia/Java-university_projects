package com.example.socialnetworkjava.repository.dbrepo;

import com.example.socialnetworkjava.domain.*;
import com.example.socialnetworkjava.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class FriendshipDbRepository implements Repository<Tuple<Long, Long>, Friendship> {
    private String url;
    private String username;
    private String password;

    public FriendshipDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public FriendshipDbRepository() {

    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> longLongTuple) {
        for(Friendship f : findAll()){
            if(f.getId() == longLongTuple){
                return Optional.of(f);
            }
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                    Long id1 = resultSet.getLong("id_u1");
                    Long id2 = resultSet.getLong("id_u2");
                    Date date = resultSet.getDate("date");
                    String status = resultSet.getString("status");
                    Tuple<Long, Long> id = new Tuple<>(id1, id2);
                    Friendship friendship = new Friendship();
                    friendship.setId(id);
                    friendship.setDate((java.sql.Date) date);
                    friendship.setStatus(status);
                    friendships.add(friendship);
                }
                return friendships;
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        /**
     * @param entity entity must be not null
     * @return an {@code Optional} - null if the entity was saved,
     * - the entity (id already exists)
     * @throws MyException              if the entity is not valid
     * @throws IllegalArgumentException if the given entity is null.     *
     */
    @Override
    public Optional<Friendship> save(Friendship entity) {
        String sql = "insert into friendships (id_u1, id_u2, date, status) values (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId().getLeft());
            ps.setLong(2, entity.getId().getRight());
            ps.setDate(3, entity.getDate());
            ps.setString(4, entity.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    /**
     * removes the entity with the specified id
     *
     * @param longLongTuple id must be not null
     * @return an {@code Optional}
     * - null if there is no entity with the given id,
     * - the removed entity, otherwise
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> longLongTuple) {
        String sql = "delete from friendships " +
                "where id_u1 in (" + longLongTuple.getLeft() + "," + longLongTuple.getRight()+ ") and id_u2 in (" + longLongTuple.getLeft() + "," + longLongTuple.getRight() + ")";
        Optional<Friendship> friendship = findOne(longLongTuple);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    /**
     * @param entity entity must not be null
     * @return an {@code Optional}
     * - null if the entity was updated
     * - otherwise (e.g. id does not exist) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws MyException              if the entity is not valid.
     */
    @Override
    public Optional<Friendship> update(Friendship entity) {
        String sql = "update friendships " +
                "set status=" + "(?)" +
                " where id_u1=" + entity.getId().getLeft() + " and id_u2=" + entity.getId().getRight();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getStatus());
            ps.execute();
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int getSize() {
        return (int) StreamSupport.stream(findAll().spliterator(), false).count();
    }
}
