package ylab.lesson4.eventsourcing.db;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DbImpl implements DbInterface {
    private static final String PERSON_ID = "personId";

    @Override
    public void savePerson(Map<String, String> map, DataSource dataSource) {
        try {
            long personId = Long.parseLong(map.get(PERSON_ID));
            if (!getPersonById(personId, dataSource)) {
                insertPerson(map, dataSource);
            } else {
                updatePerson(map, dataSource);
            }
            map.clear();
        } catch (SQLException e) {
            System.out.println("savePerson - " + e.getMessage());
        }
    }

    @Override
    public void deletePerson(Map<String, String> map, DataSource dataSource) {
        String sqlDelete = "delete from person where person_id = ?";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlDelete)) {
            long personId = Long.parseLong(map.get(PERSON_ID));
            if (getPersonById(personId, dataSource)) {
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

    @Override
    public void insertPerson(Map<String, String> map, DataSource dataSource) throws SQLException {
        String sqlInsert = """
                    insert into person (person_id, first_name, last_name, middle_name)
                    values (?, ?, ?, ?)
                """;

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlInsert)) {
            preStat.setLong(1, Long.parseLong(map.get(PERSON_ID)));
            preStat.setString(2, map.get("firstName"));
            preStat.setString(3, map.get("lastName"));
            preStat.setString(4, map.get("middleName"));
            preStat.addBatch();
            preStat.executeBatch();
        }
    }

    @Override
    public void updatePerson(Map<String, String> map, DataSource dataSource) throws SQLException {
        String sqlUpdate = "update person set first_name = ?, last_name = ?, middle_name = ? where person_id = ?";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlUpdate)) {
            preStat.setString(1, map.get("firstName"));
            preStat.setString(2, map.get("lastName"));
            preStat.setString(3, map.get("middleName"));
            preStat.setLong(4, Long.parseLong(map.get(PERSON_ID)));
            preStat.executeUpdate();
        }
    }

    public boolean getPersonById(long personId, DataSource dataSource) throws SQLException {
        String sqlSelect = "select person_id from person where person_id = ?";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sqlSelect)) {
            preStat.setLong(1, personId);
            ResultSet resultSql = preStat.executeQuery();
            return resultSql.next();
        }
    }
}
