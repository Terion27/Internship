package ylab.lesson4.persistentmap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {
    private String name = null;
    private static final String SELECT_BY_NAME_KEY = "select value from persistent_map where map_name = ? and key = ?";

    private final DataSource dataSource;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.name = name;
    }

    /*
        Задание дано с вероятностью большого количества реализаций.
        Более щадящий для БД способ реализовал в PersistentMapImplTwo
     */
    @Override
    public boolean containsKey(String key) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(SELECT_BY_NAME_KEY)) {
            preStat.setString(1, name);
            preStat.setString(2, key);
            ResultSet rs = preStat.executeQuery();
            return rs.next();
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> keyList = new ArrayList<>();
        String sqlQuery = "select key from persistent_map where map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlQuery)) {
            preStat.setString(1, name);
            ResultSet rs = preStat.executeQuery();
            while (rs.next()) {
                keyList.add(rs.getString("key"));
            }
        }
        return keyList;
    }

    @Override
    public String get(String key) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(SELECT_BY_NAME_KEY)) {
            preStat.setString(1, name);
            preStat.setString(2, key);
            ResultSet rs = preStat.executeQuery();
            if (rs.next()) {
                return rs.getString("value");
            }
        }
        return null;
    }

    /**
         Запрос на наличие записи можно не проверять, т.к. БД просто ничего не удалит.
         В методе будет только одина строчка:
                deleteByNameKey(name, key);
     */
    @Override
    public void remove(String key) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(SELECT_BY_NAME_KEY)) {
            preStat.setString(1, name);
            preStat.setString(2, key);
            ResultSet rs = preStat.executeQuery();
            if (rs.next()) {
                deleteByNameKey(name, key);
            }
        }
    }

    /**
        То же и тут. В методе будет только 2 записи:
            deleteByNameKey(name, key);
            insertMap(name, key, value);

        Применять данный метод не стал, не знаю, как вы к этому отнесетесь.

     */
    @Override
    public void put(String key, String value) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(SELECT_BY_NAME_KEY)) {
            preStat.setString(1, name);
            preStat.setString(2, key);
            ResultSet rs = preStat.executeQuery();
            if (rs.next()) {
                deleteByNameKey(name, key);
            }
            insertMap(name, key, value);
        }
    }

    @Override
    public void clear() throws SQLException {
        deleteAllByName(name);
    }

    public void deleteByNameKey(String name, String key) throws SQLException {
        String sqlDelete = "delete from persistent_map where map_name = ? and KEY = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlDelete)) {
            preStat.setString(1, name);
            preStat.setString(2, key);
            preStat.executeUpdate();
        }
    }

    public void deleteAllByName(String name) throws SQLException {
        String sqlDelete = "delete from persistent_map where map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlDelete)) {
            preStat.setString(1, name);
            preStat.executeUpdate();
        }
    }

    public void insertMap(String name, String key, String value) throws SQLException {
        String sqlInsert = "insert into persistent_map (map_name, KEY, value) values (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlInsert)) {
            preStat.setString(1, name);
            preStat.setString(2, key);
            preStat.setString(3, value);
            preStat.addBatch();
            preStat.executeBatch();
        }
    }
}
