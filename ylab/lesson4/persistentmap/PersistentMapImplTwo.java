package ylab.lesson4.persistentmap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImplTwo implements PersistentMap {
    private String name = null;
    private final Map<String, String> map = new HashMap<>();

    private final DataSource dataSource;

    public PersistentMapImplTwo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.name = name;
        this.map.clear();
        getMapByName(name);
    }

    /*
        В методах обработка производиться из локальной Мар.
        Что обеспечивает более щадящий режим работы базы
     */
    @Override
    public boolean containsKey(String key){
        return map.containsKey(key);
    }

    @Override
    public List<String> getKeys(){
        return new ArrayList<>(map.keySet());
    }

    @Override
    public String get(String key) throws SQLException {
        return map.get(key);
    }

    @Override
    public void remove(String key) throws SQLException {
        if (map.containsKey(key)) {
            map.remove(key);
            deleteByNameKey(name, key);
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (map.containsKey(key)) {
            map.remove(key);
            deleteByNameKey(name, key);
        }
        map.put(key, value);
        insertMap(name, key, value);
    }

    @Override
    public void clear() throws SQLException {
        map.clear();
        deleteAllByName(name);
    }

    private void getMapByName(String name) {
        String sqlQuery = "select * from persistent_map where map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlQuery)) {
            preStat.setString(1, name);
            ResultSet rs = preStat.executeQuery();
            while (rs.next()) {
                this.map.put(rs.getString("KEY"), rs.getString("value"));
            }
        } catch (
                SQLException e) {
            System.out.println("Ошибка БД getValueByName " + e.getMessage());
        }
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
