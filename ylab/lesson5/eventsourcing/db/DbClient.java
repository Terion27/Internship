package ylab.lesson5.eventsourcing.db;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class DbClient implements DbInterface {
    private static final String PERSON_ID = "personId";
    private final DataSource dataSource;

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void savePerson(Map<String, String> map) {
        try {
            long personId = Long.parseLong(map.get(PERSON_ID));
            if (!getPersonById(personId)) {
                insertPerson(map);
            } else {
                updatePerson(map);
            }
            map.clear();
        } catch (SQLException e) {
            System.out.println("savePerson - " + e.getMessage());
        }
    }

    @Override
    public void deletePerson(Map<String, String> map) {
        String sqlDelete = "delete from person where person_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlDelete)) {
            long personId = Long.parseLong(map.get(PERSON_ID));
            if (getPersonById(personId)) {
                preStat.setLong(1, Long.parseLong(map.get(PERSON_ID)));
                preStat.executeUpdate();
            } else {
                System.out.println("Нет записи для удаления!");
            }
            map.clear();
        } catch (SQLException e) {
            System.out.println("deletePerson - " + e.getMessage());
        }
    }

    private void insertPerson(Map<String, String> map) throws SQLException {
        String sqlInsert = "insert into person (person_id, first_name, last_name, middle_name) values (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlInsert)) {
            preStat.setLong(1, Long.parseLong(map.get(PERSON_ID)));
            preStat.setString(2, map.get("firstName"));
            preStat.setString(3, map.get("lastName"));
            preStat.setString(4, map.get("middleName"));
            preStat.addBatch();
            preStat.executeBatch();
        }
    }

    private void updatePerson(Map<String, String> map) throws SQLException {
        String sqlUpdate = "update person set first_name = ?, last_name = ?, middle_name = ? where person_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlUpdate)) {
            preStat.setString(1, map.get("firstName"));
            preStat.setString(2, map.get("lastName"));
            preStat.setString(3, map.get("middleName"));
            preStat.setLong(4, Long.parseLong(map.get(PERSON_ID)));
            preStat.executeUpdate();
        }
    }

    private boolean getPersonById(long personId) throws SQLException {
        String sqlSelect = "select person_id from person where person_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlSelect)) {
            preStat.setLong(1, personId);
            ResultSet resultSql = preStat.executeQuery();
            return resultSql.next();
        }
    }
}
